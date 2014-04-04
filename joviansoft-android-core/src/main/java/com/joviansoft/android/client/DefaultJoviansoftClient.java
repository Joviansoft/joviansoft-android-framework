package com.joviansoft.android.client;

import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianRequest;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.core.ErrorResponse;
import com.joviansoft.android.utils.Converter;
import com.joviansoft.android.utils.JacsonJsonConverter;
import com.joviansoft.android.utils.SignUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bigbao on 13-3-10.
 * 该处理类内部完成数据的请求，完成后用向handler发送消息，
 * handler中以回调的形式，将数据请求的结果返回给UI主线程
 */
public class DefaultJoviansoftClient implements JoviansoftClient {
    private String appKey;
    private String appSecret;
    private String sessionId;


    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public DefaultJoviansoftClient(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public DefaultJoviansoftClient() {

    }


    /**
     * http请求使用Spring 的RestTemplate客户端访问库,由RestTemplate完成对HttpClient的封装，
     * 当然也可以根据需要,自己重新定义对HttpClient的封装，或者采用android推荐的HttpUrlConnection的方式
     * 用HttpUrlConnection的方式，可能会更高效。
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 自定义错误错误处理，对于服务端返回的400， 404 等错误进行处理
     */
    private class CustomErrorHandler implements ResponseErrorHandler {

        private HttpStatus statusCode;

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            this.statusCode = response.getStatusCode();
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {

        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }
    }

    private Map<String, String> buildAllParams(JovianRequest request) throws JovianException {
        //获得请求的业务级参数
        Map<String, String> params = request.getParams();
        //系统级参数参数处理
        Map<String, String> systemParams = buildSystemParams();
        //将业务级参数以及系统及参数组合，形成完整的参数对
        params.putAll(systemParams);
        //对参数进行签名
        String sign = SignUtil.sign(params, appSecret);

        params.put("sign", sign);

        return params;
    }

    /**
     * 系统级参数对处理
     * @return 系统级参数对
     */
    private Map<String, String> buildSystemParams(){
        Map<String,String> sysParams = new HashMap<String, String>();
        sysParams.put("appKey", this.appKey);
        sysParams.put("sessionId", this.sessionId);
        sysParams.put("timestamp", ""+System.currentTimeMillis());
        return sysParams;
    }
    @Override
    public <T extends JovianResponse> T post(String url, JovianRequest request, Class<T> clazz) throws JovianException {
        return execute(url, buildAllParams(request), clazz, HttpMethod.POST);
    }

    @Override
    public <T extends JovianResponse> T get(String url, JovianRequest request, Class<T> clazz) throws JovianException {
        return execute(url, buildAllParams(request), clazz, HttpMethod.GET);
    }

    @Override
    public <T extends JovianResponse> T post(String url, Map<String, String> params, Class<T> clazz) throws JovianException {
        return execute(url, params, clazz, HttpMethod.POST);
    }

    @Override
    public <T extends JovianResponse> T get(String url, Map<String, String> params, Class<T> clazz) throws JovianException {
        return execute(url, params, clazz, HttpMethod.GET);
    }

    private <T extends JovianResponse> T execute(String url, Map<String, String> params, Class<T> clazz, HttpMethod method) throws JovianException {
        //自定义错误处理
        CustomErrorHandler errorHandler = new CustomErrorHandler();
        restTemplate.setErrorHandler(errorHandler);
        //添加消息转换器
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new FormHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        String rsp = "";
        try {
            switch (method) {
                case POST:
                    rsp = restTemplate.postForObject(url, generateFormValues(params), String.class);
                    break;
                case GET:
                    if (params.size() > 0) {
                        url += "?" + generateGetUrl(params);
                    }
                    rsp = restTemplate.getForObject(url, String.class);
                    break;
                default:
                    break;
            }

        } catch (RestClientException e) {
            throw new JovianException(1, e.getMessage());
        }
        T response = null;
        errorHandler.getStatusCode();
        Converter converter = new JacsonJsonConverter();
        switch (errorHandler.getStatusCode()) {
            case OK:
                // convert rsp to Object;
                response = converter.convertResponse2Object(rsp, clazz);
                break;
            case BAD_REQUEST:
                ErrorResponse errorResponse = converter.convertResponse2Object(rsp, ErrorResponse.class);
                throw new JovianException(errorResponse.getErrorCode(),
                        errorResponse.getErrorMsg(), errorResponse.getSubMsg());
            case NOT_FOUND:
                throw new JovianException(2, url + " 访问的资源不存在");
            default:
                throw new JovianException(2, "服务不可用");

        }
        return response;

    }

    /**
     * 将请求的参数转换成url请求参数形式，并将参数值进行urlEncode
     *
     * @param params
     * @return
     */
    private String generateGetUrl(Map<String, String> params) {
        StringBuffer sbParams = new StringBuffer();
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(value == null){
                value="";
            }
            if (i > 0) {
                sbParams.append('&');
            }
            try {
                sbParams.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println("请求参数组合失败，于URLEncode失败引起");
            }
            i++;
        }
        return sbParams.toString();
    }

    private MultiValueMap generateFormValues(Map<String, String> params) {
        MultiValueMap multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.setAll(params);
        return multiValueMap;
    }
}