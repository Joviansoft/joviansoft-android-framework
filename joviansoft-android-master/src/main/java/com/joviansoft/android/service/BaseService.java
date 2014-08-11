package com.joviansoft.android.service;

import com.joviansoft.android.core.net.HttpMethod;
import com.joviansoft.android.core.net.JovianAsyncRequest;
import com.joviansoft.android.core.net.JovianRequestListener;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.core.JovianParameter;
import com.joviansoft.android.core.serialize.Converter;
import com.joviansoft.android.core.serialize.JacsonJsonConverter;

/**
 * Created by bigbao on 14-3-26.
 */
public class BaseService {
    private  JovianAsyncRequest asyncRequest;
    public static final String API_ROOT_URL = "http://192.168.1.179:8080/";
    public static String appKey = "100001";
    public static String appSecret = "111111";
    public static String sessionId = "";
    private int timeout = 30; // 超时控制
    private String serviceUrl = "";
    protected static Converter converter = new JacsonJsonConverter();

    public <T extends JovianResponse> void request(String url, JovianParameter params, Class<T> clazz, JovianRequestListener listener, HttpMethod method) {
        asyncRequest = new JovianAsyncRequest(appKey,appSecret);
        asyncRequest.asyncRequest(url, params, clazz, listener, method);
    }

}
