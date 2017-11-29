package com.butao.ulifebiz.util;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description：GSON单例类，提供对象到JSON数据格式转换
 * </p>
 * <p>
 * Copyright：Copyright (c) 2013
 * </p>
 *
 * @author gujc
 */
public class JsonUtil {
    private static Gson gson = null;

    private JsonUtil() {
    }

    private static Gson gson() {
        if (gson == null) {
            synchronized (JsonUtil.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    /**
     * 目标对象到JSON数据格式转换
     *
     * @param src 目标对象
     */
    public static String toJson(Object src) {
        return gson().toJson(src);
    }

    /**
     * JSON格式数据到目标对象转换
     *
     * @param js    JSON格式数据
     * @param clazz 目标对象類型
     * @return 目标对象
     * @throws "JsonConvertException"
     */
    public static <T> T fromJson(String js, Class<T> clazz) {
        return gson().fromJson(js, clazz);
    }
    /**
     * Json 转成 Map<>
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getMapForJson(String jsonStr){
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Object value ;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Json 转成 List<Map<>>
     * @param jsonStr
     * @return
     */
    public static List<Map<String, Object>> getlistForJson(String jsonStr){
        List<Map<String, Object>> list = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            JSONObject jsonObj ;
            list = new ArrayList<Map<String,Object>>();
            for(int i = 0 ; i < jsonArray.length() ; i ++){
                jsonObj = (JSONObject)jsonArray.get(i);
                list.add(getMapForJson(jsonObj.toString()));
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    public static <T> boolean isEmpty(List<T> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}