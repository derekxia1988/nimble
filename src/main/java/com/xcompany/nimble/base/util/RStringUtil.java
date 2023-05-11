package com.xcompany.nimble.base.util;

public class RStringUtil {
    public static String format(String t, Object... args){
        for(Object arg : args){
            t.replaceFirst("\\{\\}", String.valueOf(arg));
        }

        return t;
    }
}
