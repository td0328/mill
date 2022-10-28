package com.myth.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    /**
     *
     * @param record
     * @return HashMap 用于将JSONObject转化为HashMap
     */
    public static HashMap<String, String> changeToMap(JSONObject record) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : record.entrySet()) {
            map.put(entry.getKey(),entry.getValue().toString());
        }
        return map;
    }
}
