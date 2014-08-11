package com.joviansoft.android.core.net;

import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianRequest;
import com.joviansoft.android.core.JovianResponse;

import java.util.Map;

/**
 * Created by bigbao on 14-3-11.
 */
public interface JoviansoftClient {
    <T extends JovianResponse> T post(String url, JovianRequest request, Class<T> clazz) throws JovianException;

    <T extends JovianResponse> T get(String url, JovianRequest request, Class<T> clazz) throws JovianException;

    <T extends JovianResponse> T post(String url, Map<String, String> params, Class<T> clazz) throws JovianException;

    <T extends JovianResponse> T get(String url, Map<String, String> params, Class<T> clazz) throws JovianException;
}
