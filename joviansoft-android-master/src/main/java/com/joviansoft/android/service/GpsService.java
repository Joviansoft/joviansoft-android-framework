package com.joviansoft.android.service;

import com.joviansoft.android.core.net.HttpMethod;
import com.joviansoft.android.core.net.JovianRequestListener;
import com.joviansoft.android.core.CodeOnlyResponse;
import com.joviansoft.android.core.JovianParameter;
import com.joviansoft.android.domain.gps.GpsResponse;

/**
 * Created by bigbao on 14-3-26.
 */
public class GpsService extends BaseService {
    private static final String GPS_SERVICE_ROOT = API_ROOT_URL+"gps/";

    public void getGps(String gpsid, String startTime, String endTime){

    }
    public void uploadGps(String userId, String  jsonBeanList, JovianRequestListener listener){
        String url = GPS_SERVICE_ROOT;
        JovianParameter params = new JovianParameter();
        params.addParam("userid",userId);
        params.addParam("gpsdata", jsonBeanList);
      //  params.addParam("gpsdata", converter.convertObject2Json(gpsBeanList));
        request(url, params, GpsResponse.class, listener, HttpMethod.POST);
    }
    public void getGps(String userId, JovianRequestListener listener){
        String url = GPS_SERVICE_ROOT;
        JovianParameter params = new JovianParameter();
        params.addParam("userid", userId);
        request(url,params,GpsResponse.class, listener,HttpMethod.GET);
    }
    public void addGps(String data, JovianRequestListener listener){
        String url = GPS_SERVICE_ROOT + "add";
        JovianParameter params = new JovianParameter();
        params.addParam("data", data);
        request(url, params, CodeOnlyResponse.class, listener, HttpMethod.GET);
    }
}
