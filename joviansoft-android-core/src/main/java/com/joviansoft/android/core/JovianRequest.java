package com.joviansoft.android.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigbao on 14-3-11.
 */
public abstract class JovianRequest implements Serializable{
    private static final long serialVersionUID = 6341951835943868954L;

    /**
     * 构建参数对
     * @return
     */
    public abstract Map<String,String> getParams();
    private Map<String, String> params = null;

    public JovianRequest(){
        params = new HashMap<String, String>();
    }
    public void addParam(String paramName, String value){
        params.put(paramName, value);
    }
    public void removeParam(String paramName, String value){
        params.remove(paramName);
    }
}
