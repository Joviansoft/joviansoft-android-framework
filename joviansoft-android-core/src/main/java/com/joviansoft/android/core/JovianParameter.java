package com.joviansoft.android.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigbao on 14-3-25.
 */
public class JovianParameter {
    private Map<String,String> params = new HashMap<String,String>();

    public Map<String, String> getParams() {
        return params;
    }
    public void addParam(String key, String value){
        params.put(key, value);
    }
    public void removeParam(String key){
        params.remove(key);
    }
}
