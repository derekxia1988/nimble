/*
 * Copyright (c) 2021-2022.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.common.util;

import com.alibaba.fastjson.JSON;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xiadong
 * @since 2021/11/23
 **/
public class ReflectUtils {
    /**
     * 找到所有声明的方法，即使这个类被动态代理修改也可以找到原来的方法
     *
     * @param clazz class
     * @return methods
     */
    public static Method[] findAllDeclaredMethods(Class<?> clazz) {
        return ReflectionUtils.getAllDeclaredMethods(clazz);
    }

    public static Set<Class<?>> getTypesWithAnnotation(Class<? extends Annotation> annotation, String packageName) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder().forPackages(packageName)
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
                        .setScanners(Scanners.TypesAnnotated)
        );
        return reflections.getTypesAnnotatedWith(annotation);
    }

    public static <T> Set<Class<? extends T>> getSubTypes(Class<T> interfaceClz, String packageName) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages(packageName)
                        .filterInputsBy(
                                new FilterBuilder().includePackage(packageName)
                        ).setScanners(Scanners.SubTypes)

        );
        return reflections.getSubTypesOf(interfaceClz);
    }

    public static Object invokeStaticMethod(
            Method method,
            boolean allowParamsNotMatch,
            Object[] params,
            String[] convertParams
    ) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
        Object[] actualParams = getMethodParams(method, allowParamsNotMatch, params, convertParams);
        method.setAccessible(true);
        if (actualParams.length == 0) {
            return method.invoke(null);
        }
        return method.invoke(null, actualParams);
    }

    public static Object invokeMethod(
            Method method,
            Object target,
            boolean allowParamsNotMatch,
            Object[] params,
            String[] convertParams
    ) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
        Object[] actualParams = getMethodParams(method, allowParamsNotMatch, params, convertParams);
        method.setAccessible(true);
        if (actualParams.length == 0) {
            return method.invoke(target);
        }
        return method.invoke(target, actualParams);
    }

    public static Object[] getMethodParams(
            Method method,
            boolean allowParamsNotMatch,
            Object[] params,
            String[] convertParams
    ) throws IllegalArgumentException {
        var parameterTypes = method.getParameterTypes();
        if (!allowParamsNotMatch && parameterTypes.length != params.length + convertParams.length) {
            throw new IllegalArgumentException(
                    String.format(
                            "参数长度不一致, %s需要%d, 但提供了%d个参数",
                            method.getName(),
                            parameterTypes.length,
                            params.length + convertParams.length
                    )
            );
        }

        if (parameterTypes.length == 0) {
            return new Object[0];
        }

        Object[] parsedParams = new Object[parameterTypes.length];
        for (int i = 0; i < params.length; i++) {
            if (allowParamsNotMatch && i >= parameterTypes.length) {
                return parsedParams;
            }
            parsedParams[i] = params[i];
        }
        for (int i = params.length; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            var typeParam = TypeParam.get(parameterType);
            if (allowParamsNotMatch && i - params.length >= convertParams.length) {
                parsedParams[i] = typeParam.defaultValue;
            } else {
                parsedParams[i] = typeParam.parser == null ?
                        typeParam.parser2.apply(convertParams[i - params.length], parameterType)
                        : typeParam.parser.apply(convertParams[i - params.length]);
            }
        }

        return parsedParams;
    }

    private enum TypeParam {
        /**
         *
         */
        DEFAULT(
                Set.of(),
                null,
                JSON::parseObject,
                null
        ),
        BOOLEAN(
                Set.of(boolean.class, Boolean.class),
                Boolean::parseBoolean,
                false
        ),
        INT(
                Set.of(int.class, Integer.class),
                Integer::parseInt,
                0
        ),
        BYTE(
                Set.of(boolean.class, Boolean.class),
                Byte::parseByte,
                0),
        SHORT(
                Set.of(short.class, Short.class),
                Short::parseShort,
                0
        ),
        LONG(
                Set.of(long.class, Long.class),
                Long::parseLong,
                0
        ),
        FLOAT(
                Set.of(float.class, Float.class),
                Float::parseFloat,
                0
        ),
        DOUBLE(
                Set.of(double.class, Double.class),
                Double::parseDouble,
                0
        ),
        STRING(
                Set.of(String.class),
                String::valueOf,
                ""
        ),
        LIST(
                Set.of(List.class),
                s -> JSON.parseArray(arrayStringPreprocess(s)),
                List.of()
        ),
        ;
        private final Set<Class<?>> classes;
        private final Function<String, Object> parser;
        private final BiFunction<String, Class<?>, Object> parser2;
        private final Object defaultValue;

        TypeParam(Set<Class<?>> classes, Function<String, Object> parser, Object defaultValue) {
            this.classes = classes;
            this.parser = parser;
            this.defaultValue = defaultValue;
            this.parser2 = null;
        }

        TypeParam(Set<Class<?>> classes, Function<String, Object> parser, BiFunction<String, Class<?>, Object> parser2, Object defaultValue) {
            this.classes = classes;
            this.parser = parser;
            this.parser2 = parser2;
            this.defaultValue = defaultValue;
        }

        private boolean match(Class<?> clazz) {
            return classes.contains(clazz);
        }

        public static TypeParam get(Class<?> clazz) {
            return Arrays.stream(values())
                    .filter(typeParam -> typeParam.match(clazz))
                    .findFirst()
                    .orElse(DEFAULT);
        }
    }

    public static String arrayStringPreprocess(String s) {
        if (!s.endsWith("]")) {
            return "[" + s + "]";
        }
        return s;
    }

}
