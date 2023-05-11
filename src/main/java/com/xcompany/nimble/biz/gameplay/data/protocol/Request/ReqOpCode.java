package com.xcompany.nimble.biz.gameplay.data.protocol.Request;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ReqOpCode {
    LOGIN(10001, ReqLoginData.class),
    STAGE_SUCCESS(20001, ReqMainPVEUnlockData.class),
    LORD_LEVEL_UP(30000, ReqLordLvUpData.class),
    HERO_LEVEL_UP(30001, ReqHeroLvUpData.class)
    ;

    private final int opCode;

    private final Class<? extends ReqData> clz;

    private final static Map<Integer, ReqOpCode> enumMap = Maps.newHashMap();

    static {
        for(ReqOpCode reqOpCode : ReqOpCode.values()){
            enumMap.put(reqOpCode.opCode, reqOpCode);
        }
    }

    ReqOpCode(int opCode, Class<? extends ReqData> clz){
        this.opCode = opCode;
        this.clz = clz;
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends ReqData> getClz() {
        return clz;
    }

    public static Class<? extends ReqData> getClzByOpCode(int opCode){
        return enumMap.get(opCode).getClz();
    }
}
