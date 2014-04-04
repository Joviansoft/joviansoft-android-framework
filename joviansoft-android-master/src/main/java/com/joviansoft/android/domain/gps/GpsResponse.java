package com.joviansoft.android.domain.gps;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.domain.Message;

import java.util.List;

/**
 * Created by bigbao on 14-3-11.
 */
public class GpsResponse extends JovianResponse {
    private int total;
    @JsonProperty("gpsDataList")
    private List<GpsBean> gpsDataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GpsBean> getGpsDataList() {
        return gpsDataList;
    }

    public void setGpsDataList(List<GpsBean> gpsDataList) {
        this.gpsDataList = gpsDataList;
    }

    public GpsResponse(int total, List<GpsBean> gpsDataList) {
        this.total = total;
        this.gpsDataList = gpsDataList;
    }

    public GpsResponse() {
    }
}
