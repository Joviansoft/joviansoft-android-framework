package com.joviansoft.android.utils;

import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianResponse;

/**
 * Created by bigbao on 14-3-11.
 */
public interface Converter {
    <T extends JovianResponse> T convertResponse2Object(String rsp, Class<T> tClass) throws JovianException;
    String convertObject2Json(Object object) throws JovianException;
}
