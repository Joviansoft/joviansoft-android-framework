package com.joviansoft.android.service;

import com.joviansoft.android.core.net.HttpMethod;
import com.joviansoft.android.core.net.JovianRequestListener;
import com.joviansoft.android.core.JovianParameter;
import com.joviansoft.android.domain.user.LoginResponse;
import com.joviansoft.android.domain.user.LogoutResponse;
import com.joviansoft.android.domain.user.UserInfo;
import com.joviansoft.android.core.serialize.Converter;
import com.joviansoft.android.core.serialize.JacsonJsonConverter;

/**
 * Created by bigbao on 14-3-25.
 */
public class AccountService extends BaseService {
    private static final String USER_SERVICE_ROOT = API_ROOT_URL+ "user/";

    public void login(String username, String password, JovianRequestListener<LoginResponse> listener) {
       // LoginRequest request = new LoginRequest();
        JovianParameter params = new JovianParameter();
        params.addParam("username", username);
        params.addParam("password", password);
        String url = USER_SERVICE_ROOT;

        request(url, params, LoginResponse.class, listener, HttpMethod.POST);
    }

    public void logOut(String sessionId, JovianRequestListener<LogoutResponse> listener) {
        JovianParameter params = new JovianParameter();
        params.addParam("sessionId", sessionId);
        String url = USER_SERVICE_ROOT+"/logout";
        request(url, params, LoginResponse.class, listener, HttpMethod.POST);
    }

    public void getUserInfo(String userId, JovianRequestListener<UserInfo> listener){
        JovianParameter params = new JovianParameter();
        params.addParam("userid", userId);
        String url = USER_SERVICE_ROOT;
        request(url, params, UserInfo.class, listener, HttpMethod.GET);
    }

    public void searchUserByXm(int pageSize, int start, String xm){

    }

    public void searchUserByJql(String jql, JovianRequestListener listener){

    }

}
