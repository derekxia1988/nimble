/*
 * Copyright (c) 2021.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.numeric;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.xcompany.nimble.base.exception.StartServerException;
import com.xcompany.nimble.common.util.ReflectUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiadong
 * @since 2021/09/07
 **/
@Component
@Log4j2
class NumericsLoader {

    private static final String CONST_NUMERIC_KEY = "Const";
    private List<NumericPreprocessor> preprocessors = Collections.emptyList();

    private final ObjectMapper mapper = JsonMapper.builder()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    private final NumericProperties numericProperties;

    NumericsLoader(NumericProperties numericProperties) {
        this.numericProperties = numericProperties;
    }

    @PostConstruct
    public void start() {
        log.info("[NUMERIC]load all numerics on start.");
        var numerics = loadAllNumerics();
        Numerics.writeLock();
        try {
            Numerics.init(numerics);
            loadConstNumeric(getAllNumericFiles().get(CONST_NUMERIC_KEY));
            log.info("[NUMERIC]init numerics complete, begin preprocess numeric.");
            preprocessors.forEach(numericPreprocessor -> {
                numericPreprocessor.process();
                log.info("[NUMERIC]preprocess {}.", numericPreprocessor.getClass().getSimpleName());
            });
        } finally {
            Numerics.releaseWriteLock();
        }
        log.info("[NUMERIC]load all numerics complete.");
    }

    private Map<Class<? extends NumericTable>, Map<Integer, ? extends NumericTable>> loadAllNumerics() {
        return loadNumerics(getAllNumericFiles());
    }

    public String reload() {
        log.info("[NUMERIC]start reload numerics");
        var numerics = loadAllNumerics();
        Numerics.writeLock();
        try {
            Numerics.init(numerics);
            loadConstNumeric(getAllNumericFiles().get(CONST_NUMERIC_KEY));
            log.info("[NUMERIC]reload numerics complete, begin reload numeric.");
            preprocessors.forEach(numericPreprocessor -> {
                numericPreprocessor.reload();
                log.info("[NUMERIC]reload {}.", numericPreprocessor.getClass().getSimpleName());
            });
        } finally {
            Numerics.releaseWriteLock();
        }
        log.info("[NUMERIC]reload all numerics complete.");
        return "success";
    }

    @NonNull
    private Map<String, File> getAllNumericFiles() {
        File root = new File(numericProperties.getPath());
        if (!root.exists()) {
            throw new StartServerException(
                    "load numerics error, path %s not found.".formatted(numericProperties.getPath()));
        }

        var files = root.listFiles(file -> !file.isDirectory() && file.getName().endsWith(".json"));
        if (files == null || files.length == 0) {
            throw new StartServerException(
                    "load numerics error, path %s is empty.".formatted(numericProperties.getPath()));
        }

        return Arrays.stream(files).collect(
                Collectors.toMap(
                        file -> file.getName().endsWith("Info.json") ?
                                file.getName().substring(0, file.getName().length() - 9)
                                : file.getName().substring(0, file.getName().length() - 5),
                        Function.identity()
                )
        );
    }

    private Map<Class<? extends NumericTable>, Map<Integer, ? extends NumericTable>> loadNumerics(Map<String, File> fileMap) {
        Map<Class<? extends NumericTable>, Map<Integer, ? extends NumericTable>> data
                = new HashMap<>(fileMap.size() * 2);

        for (var clazz : ReflectUtils.getSubTypes(NumericTable.class, numericProperties.getPackagePath())) {
            var file = fileMap.get(clazz.getSimpleName().replace("Numeric", ""));
            if (file == null) {
                throw new StartServerException("[NUMERIC]load numeric file for class: %s not found.".formatted(clazz.getSimpleName()));
            }
            try (MappingIterator<NumericTable> it = mapper.readerFor(clazz).readValues(file)) {
                Map<Integer, ? extends NumericTable> numericTables = it.readAll().stream()
                        .collect(Collectors.toUnmodifiableMap(NumericTable::getId, Function.identity()));
                data.put(clazz, numericTables);
                log.info("[NUMERIC]finish load numeric: {}", clazz.getSimpleName());
            } catch (Exception e) {
                throw new StartServerException("load numeric file %s error.".formatted(file.getName()), e);
            }
        }

        return data;
    }

    // TODO: ConstNumeric被注释掉了
    private void loadConstNumeric(File file) {
//        try {
//            var contents = Files.readString(file.toPath());
//
//            fillConstNumeric(contents);
//
//            log.info("finish load numeric: {}", ConstNumeric.class.getSimpleName());
//
//        } catch (Exception e) {
//            throw new StartServerException("load numeric file %s error.".formatted(file.getName()), e);
//        }

    }

//    private static void fillConstNumeric(String contents) {
//        var jsonObj = JSONObject.parseObject(contents);
//
//        Arrays.stream(ConstNumeric.class.getDeclaredFields())
//                .filter(field -> Modifier.isStatic(field.getModifiers()))
//                .forEach(field -> {
//                    var v = jsonObj.getObject(field.getName(), field.getType());
//                    try {
//                        field.set(null, v);
//                    } catch (IllegalAccessException e) {
//                        throw new StartServerException("load numeric file Const.json error.", e);
//                    }
//                });
//    }

    @Autowired
    public void setPreprocessors(List<NumericPreprocessor> preprocessors) {
        this.preprocessors = preprocessors;
    }
}