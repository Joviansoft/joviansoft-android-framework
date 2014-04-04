package com.joviansoft.android.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.joviansoft.android.client.DefaultJoviansoftClient;
import com.joviansoft.android.client.JovianRequestListener;
import com.joviansoft.android.client.JoviansoftClient;
import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianRequest;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.core.JovianParameter;
import com.joviansoft.android.client.HttpMethod;

/**
 * Created by bigbao on 14-3-25.
 */
public class JovianService {
    private static final String API_ROOT_URL = "http://192.168.1.100:8080/";
    private int timeout = 30; // 超时控制
    private Handler handler = null;
    private JovianRequestListener<JovianResponse> listener;
    private HandlerThread handlerThread;
    private String serviceUrl = "";
    private String appKey = "100001";
    private String appSecret = "111111";
    private String sessionId = "";

    public void asyncRequest(String url, JovianRequest request, JovianRequestListener listener, HttpMethod method) {
        this.listener = listener;
        serviceUrl = API_ROOT_URL + url;
    }

    public void asyncRequest(String url, JovianParameter params, JovianRequestListener listener, HttpMethod method) {
        this.listener = listener;
        serviceUrl = API_ROOT_URL + url;
        execute(method, params);
    }

    private void execute(final HttpMethod method, final JovianParameter params) {
        handlerThread = new HandlerThread("Handler Thread for getdata");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(), callback);

        handler.post(new Runnable() {
            @Override
            public void run() {
                JovianResponse object = null;
                Message message = new Message();
                JoviansoftClient client = new DefaultJoviansoftClient(appKey, appSecret);
                try {
                    switch (method) {
                        case POST:
                            object = client.post(serviceUrl, params.getParams(), JovianResponse.class);
                            break;
                        case GET:
                            object = client.get(serviceUrl, params.getParams(), JovianResponse.class);
                            break;
                        default:
                            break;
                    }
                    message.what = 0;
                    message.obj = object;

                } catch (JovianException e) {
                    message.what = 1;
                    message.obj = e;
                }
                handler.sendMessage(message);
            }
        });

    }

    /**
     * 通过handler的Callback，主线程的监听返回数据或者返回异常信息
     */
    private Handler.Callback callback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (null != listener) {
                switch (msg.what) {
                    case 0:
                        listener.onComplete((JovianResponse) msg.obj);
                        break;
                    case 1:
                        listener.onApiExceptions((JovianException) msg.obj);
                        break;
                    default:
                        break;
                }
            }
            return false;
        }
    };
}
