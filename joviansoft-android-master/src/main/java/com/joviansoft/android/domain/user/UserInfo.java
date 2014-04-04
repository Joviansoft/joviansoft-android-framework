package com.joviansoft.android.domain.user;

import com.joviansoft.android.core.JovianResponse;

/**
 * Created by bigbao on 14-3-26.
 */
public class UserInfo extends JovianResponse {
    private String userId;
    private String username;
    private String gender;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
