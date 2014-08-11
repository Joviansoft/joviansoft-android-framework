package com.joviansoft.android.master;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joviansoft.android.core.net.DefaultJoviansoftClient;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.master_main);
        testHandlerThread();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void testHandlerThread(){
        Log.i("main thread id", "" + Thread.currentThread().getId());
        HandlerThread thread = new HandlerThread("Test");
        thread.start();
        this.getMainLooper();
        final Handler handler = new Handler(getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                Log.i("callback thread id", "" + Thread.currentThread().getId());
                return false;
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Log.i("request thread id", "" + Thread.currentThread().getId());

                Message msg = new Message();
                msg.what = 0;

                msg.obj = "123";
                handler.sendMessage(msg);
            }
        };
        handler.post(runnable);
       // thread.start();
    }
}
