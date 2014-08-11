package com.joviansoft.android.master;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.joviansoft.android.core.net.JovianRequestListener;
import com.joviansoft.android.core.JovianException;
import com.joviansoft.android.core.net.DefaultJoviansoftClient;
import com.joviansoft.android.core.net.JoviansoftClient;
import com.joviansoft.android.core.JovianResponse;
import com.joviansoft.android.domain.gps.GpsBean;
import com.joviansoft.android.request.GetGpsRequest;
import com.joviansoft.android.domain.gps.GpsResponse;
import com.joviansoft.android.service.GpsService;

//import org.androidannotations.annotations.AfterViews;
//import org.androidannotations.annotations.EActivity;
//import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@EActivity(R.layout.master_main)
public class HelloAndroidActivity extends Activity {


    private static final String TAG = "HelloAndroidActivity";
    //	@ViewById
	TextView helloTextView;


//	@AfterViews
	void afterViews() {

		Date now = new Date();
		String helloMessage = String.format("fsdfdfadfadsfafd", now.toString());
		helloTextView.setText(helloMessage);
        System.out.println("主线程id"+Thread.currentThread().getId());
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程id"+Thread.currentThread().getId());
            }
        });
       // testGet();
      //  testPost();
      //  testHandlerThread();
      //  testThreadCallBack();
      //  testGpsService();

	}


    @Override
    protected void onResume() {
        super.onResume();
        testGpsServicePost();
    }

    private void testGpsService(){
        GpsService service = new GpsService();
        Log.i(TAG, "main thread id " + Thread.currentThread().getId());

        service.getGps("123", new JovianRequestListener<GpsResponse>() {
            @Override
            public void onComplete(GpsResponse gpsResponse) {
                Log.i("GPS-------------", gpsResponse.getTotal() + "");
                Log.i("GPS-------------", gpsResponse.getGpsDataList().get(0).getX() + "");
                Toast.makeText(HelloAndroidActivity.this, "xxxx", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onApiExceptions(JovianException exception) {
                Toast.makeText(HelloAndroidActivity.this, exception.getErrorMsg() +"错误", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void testGpsServicePost(){
        GpsService gpsUpload  = new GpsService();
        List<GpsBean> gpsBeanList = new ArrayList<GpsBean>();
        gpsUpload.addGps("aas", new JovianRequestListener() {
            @Override
            public void onComplete(JovianResponse object) {
                if(object == null)
                    Toast.makeText(HelloAndroidActivity.this, "返回空对象", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(HelloAndroidActivity.this, "不是空对象", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onApiExceptions(JovianException exception) {
                Toast.makeText(HelloAndroidActivity.this, exception.getErrorMsg(), Toast.LENGTH_LONG).show();
            }
        });
//        gpsUpload.uploadGps("111", gpsBeanList, new JovianRequestListener<GpsResponse>() {
//            @Override
//            public void onComplete(GpsResponse gpsResponse) {
//                Log.i("GPS-------------", gpsResponse.getTotal() + "");
//                Log.i("GPS-------------", gpsResponse.getGpsDataList().get(0).getX() + "");
//                Converter converter = new JacsonJsonConverter();
//                Toast.makeText(HelloAndroidActivity.this, converter.convertObject2Json(gpsResponse), 10000).show();
//            }
//
//            @Override
//            public void onApiExceptions(JovianException exception) {
//                Toast.makeText(HelloAndroidActivity.this, exception.getErrorMsg(), 10000).show();
//                //Log.i("GPS--------ERROR", "错误： "+exception.getErrorMsg());
//            }
//        });
    }
    private  void testPost(){
        Thread thread = new Thread(new Runnable() {
            private int i=0;
            @Override
            public void run() {
                JoviansoftClient client = new DefaultJoviansoftClient("100001", "111111");
                GetGpsRequest request = new GetGpsRequest();
                request.setUserId("123");
                //  request.setDeviceId("222");
                try {
                    GpsResponse gpsResponse = client.post("http://192.168.1.100:8080/gps", request, GpsResponse.class);
                    Log.i("XXXXXXXXXXXXXXXXXXXXXX", gpsResponse.getTotal() + "");
                } catch (JovianException e) {
                    Log.e("XXXX",  e.toString());
                   // Toast.makeText(HelloAndroidActivity.this, "101010", 1000).show();
                    // e.printStackTrace();
                    // Log.i("XXXXXXXXXXXXXXX", e.getMessage());
                }
            }
        } );
        thread.start();
    }
    private void testGet() {
        Thread thread = new Thread(new Runnable() {
            private int i=0;
            @Override
            public void run() {

                JoviansoftClient client = new DefaultJoviansoftClient("100001", "111111");
                GetGpsRequest request = new GetGpsRequest();
                request.setUserId("123");
              //  request.setDeviceId("222");
                try {
                    GpsResponse gpsResponse = client.get("http://192.168.1.100:8080/gps", request, GpsResponse.class);
                    Log.i("XXXXXXXXXXXXXXXXXXXXXX", gpsResponse.getTotal() + "");
                } catch (JovianException e) {
                    Log.e("XXXX",  e.toString());
                 //   Toast.makeText(HelloAndroidActivity.this, "101010", 1000);
                   // e.printStackTrace();
                   // Log.i("XXXXXXXXXXXXXXX", e.getMessage());
                }
            }
        });
        thread.start();
    }

    private void testHandlerThread(){
        HandlerThread thread = new HandlerThread("Test");
        thread.start();
        final Handler handler = new Handler(thread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(android.os.Message msg) {
                Log.i("MESSAGE", (String) msg.obj);
                Toast.makeText(HelloAndroidActivity.this, (String)msg.obj, Toast.LENGTH_LONG).show();
                helloTextView.setText("12345");

                return true;
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                android.os.Message msg = new android.os.Message();
                msg.what = 0;

                msg.obj = "123";
                handler.sendMessage(msg);
            }
        };
        handler.post(runnable);
        // thread.start();
    }
    private void testThreadCallBack(){

        GetDataTask getDataTask = new GetDataTask(new IListener() {
            @Override
            public void onMessage(String str) {
                System.out.println("OnMessage Id:" +Thread.currentThread().getId());
            }
        });
        getDataTask.execute();
    }
    public  interface IListener{
        void onMessage(String str);
    }
    public class GetDataTask{
        IListener listener;
        private Thread thread;

        public GetDataTask(IListener listener) {
            this.listener = listener;
        }
        public void execute(){
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                   if(listener!=null){
                       listener.onMessage("测试线程回调");
                   }
                }
            });
            thread.start();
        }
    }

}
