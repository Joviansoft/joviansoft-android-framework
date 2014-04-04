package com.joviansoft.android.client;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianRequest;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.core.JovianParameter;

/**
 * Created by bigbao on 14-3-25.
 */
public class JovianAsyncRequest<T extends JovianResponse> {
    private int timeout = 30; // 超时控制
    private Handler handler = null;
    private JovianRequestListener<T> listener;
    private HandlerThread handlerThread;
    private String appKey = "";
    private String appSecret = "";
    private String sessionId = "";

    public JovianAsyncRequest(String appSecret, String appKey) {
        this.appSecret = appSecret;
        this.appKey = appKey;
    }

    public void asyncRequest(String url, JovianRequest request, JovianRequestListener listener, HttpMethod method) {
        this.listener = listener;
    }

    public void asyncRequest(String url, JovianParameter params, Class<T> clazz, JovianRequestListener listener, HttpMethod method) {
        this.listener = listener;
         execute(url, method, params,clazz);
    }

    private void execute(final String url, final HttpMethod method, final JovianParameter params, final Class<T> clazz) {
        handlerThread = new HandlerThread("Handler Thread for getdata");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(), callback);

        handler.post(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                JovianResponse object = null;
                JoviansoftClient client = new DefaultJoviansoftClient(appKey, appSecret);
                try {
                    switch (method) {
                        case POST:
                            object = client.post(url, params.getParams(), clazz);
                            break;
                        case GET:
                            object =  client.get(url, params.getParams(), clazz);
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
                        listener.onComplete((T) msg.obj);
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
