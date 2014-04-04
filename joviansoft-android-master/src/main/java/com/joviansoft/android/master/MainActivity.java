package com.joviansoft.android.master;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joviansoft.android.client.DefaultJoviansoftClient;


public class MainActivity extends ActionBarActivity {

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
        HandlerThread thread = new HandlerThread("Test");
        thread.start();
        final Handler handler = new Handler(thread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.i("MESSAGE", (String) msg.obj);
                return false;
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
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
