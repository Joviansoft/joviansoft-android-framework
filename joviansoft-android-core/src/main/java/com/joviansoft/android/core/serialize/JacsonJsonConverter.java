package com.joviansoft.android.core.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianResponse;

/**
 * Created by bigbao on 14-3-12.
 */
public class JacsonJsonConverter implements Converter {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 用jackson json的json处理，将json字符串转成json对象。
     *
     * @param rsp
     * @param clazz
     * @param <T>
     * @return
     */
    @Override
    public <T extends JovianResponse> T convertResponse2Object(String rsp, Class<T> clazz) throws JovianException {

        //objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        try {
            //    rsp = "{\"total\":2,\"gpsDataList\":[{\"x\":120.11,\"y\":30.12}]}";
            T object = objectMapper.readValue(rsp, clazz);
            return object;

            //  return objectMapper.readValue(rsp, clazz);
        } catch (Exception e) {
            throw new JovianException(1, clazz + "对象反序列化失败，检查是否有默认的构造函数");
        }

    }

    @Override
    public String convertObject2Json(Object object) throws JovianException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String strJson = objectMapper.writeValueAsString(object);
            return strJson;
        } catch (JsonProcessingException e) {
            throw new JovianException(1, object.getClass() + "对象序列化失败");
        }
    }
}
