package com.myth.common.utils;

import java.util.HashMap;

public class ToolUtils {
    public static HashMap<String,String> arrayToMap(Object[] objects){
        HashMap<String,String> map = new HashMap<>();
        for(Object o:objects){
            String s = String.valueOf(o);
            map.put(s,s);
        }
        return map;
    }
}
