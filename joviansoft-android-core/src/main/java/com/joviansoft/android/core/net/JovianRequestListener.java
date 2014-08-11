package com.joviansoft.android.core.net;

import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.JovianResponse;

/**
 * 异步请求数据的监听，模板参数指定为需要返回的数据类型,此监听由用户发起请求时创建，
 * 在onComplte接口中实现收到请求数据的处理。
 * 在onApiException中，实现异常处理。
 * Created by bigbao on 14-3-25.
 */
public interface JovianRequestListener<T extends JovianResponse> {
    void onComplete(T object);

    void onApiExceptions(JovianException exception);
}
