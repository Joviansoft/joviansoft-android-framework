package com.joviansoft.android.request;

import com.joviansoft.android.core.JovianRequest;

import java.util.Map;

/**
 * Created by bigbao on 14-3-25.
 */
public class LoginRequest extends JovianRequest {
    private String username;
    private String password;
    @Override
    public Map<String, String> getParams() {
        return null;
    }
    public LoginRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }
}
