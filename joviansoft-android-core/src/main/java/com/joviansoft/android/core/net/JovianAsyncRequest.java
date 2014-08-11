package com.joviansoft.android.core.net;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianRequest;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.core.JovianParameter;

/**
 * Created by bigbao on 14-3-25.
 * 异步请求处理类
 *
 * @author bigbao
 * @version 1.0.1
 */
public class JovianAsyncRequest<T extends JovianResponse> {
    private int timeout = 5; // 超时控制
    private int retryTime = 3; //超时重试次数
    private Handler handler = null;
    private JovianRequestListener<T> listener;
    private String appKey;
    private String appSecret;
    private String sessionId;

    public JovianAsyncRequest(String appSecret, String appKey) {
        this.appSecret = appSecret;
        this.appKey = appKey;
    }

    public void asyncRequest(String url, JovianParameter params, Class<T> clazz, JovianRequestListener listener, HttpMethod method) {
        this.listener = listener;
        execute(url, method, params, clazz);
    }

    public T syncRequest(String url, JovianParameter params, Class<T> clazz, JovianRequestListener listener, HttpMethod method) {
        JoviansoftClient client = new DefaultJoviansoftClient(appKey, appSecret);
        try {
            switch (method) {
                case POST:
                    return client.post(url, params.getParams(), clazz);

                case GET:
                    return client.get(url, params.getParams(), clazz);

            }

        } catch (JovianException e) {
            return null;
        }
        return null;
    }

    private void execute(final String url, final HttpMethod method, final JovianParameter params, final Class<T> clazz) {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //主线程接收到消息，通知监听者
                super.handleMessage(msg);
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
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                JovianResponse object = null;
                JoviansoftClient client = new DefaultJoviansoftClient(appKey, appSecret);
                try {
                    switch (method) {
                        case POST:
                            object = client.post(url, params.getParams(), clazz);
                            break;
                        case GET:
                            object = client.get(url, params.getParams(), clazz);
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
        thread.start();
    }
}
