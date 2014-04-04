package com.joviansoft.android.request;

import com.joviansoft.android.core.JovianRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bigbao on 14-3-14.
 */
public class GetGpsRequest extends JovianRequest {
    private String userId;
    private String deviceId;
    private Map<String, String> params = new HashMap<String, String>();
    @Override
    public Map<String, String> getParams() {
        params.put("userid", userId);
        params.put("deviceid", deviceId);
        return params;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
