package com.example.myserviceapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Context context;
    TextView textView;
    ProgressBar progressBar;
    Button clearbtn;
    MyIntentService service;
    // private Handler handler;


    public static final String MESSAGE_KEY = "message_key";

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        textView = findViewById(R.id.textview);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        clearbtn = findViewById(R.id.clearBtn);
        //handler = new Handler();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String songname = intent.getStringExtra(MESSAGE_KEY);
                Log(songname);

            }
        };
        service = new MyIntentService();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart: called " + DownloadHandler.SERVICE_MESSAGE);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver, new IntentFilter(DownloadHandler.SERVICE_MESSAGE));

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG", "onStop: called " + broadcastReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(broadcastReceiver);
    }

    public void run(View view) {
        // ResultReceiver resultReceiver = new MyResultReceiverDownload(null);
        progressBar.setVisibility(View.VISIBLE);
        for (String song : Playlist.songs) {
            Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
            intent.putExtra(MESSAGE_KEY, song);
            //intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, resultReceiver);
         //   startService(intent);
            service.onHandleIntent(intent);

        }

    }

    public void clear(View view) {
        Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
        stopService(intent);
        Log.e("TAG", "clear: stop");
        textView.setText(" ");
    }

    public void Log(String name) {
        textView.setText(name);
    }


   /*
   // updating ui/main thread from service using RESULT RECEIVER
   public class MyResultReceiverDownload extends ResultReceiver {
        public MyResultReceiverDownload(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            String song_name = resultData.getString(MESSAGE_KEY);
            if (resultCode == MainActivity.RESULT_OK && resultData != null) {

               //update ui with the help of runOnUiThread()method
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log(song_name);
                    }
                });

             //update ui with the help of handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log(song_name + " Downloaded");
                    }
                });
                Log.e("TAG", "onReceiveResult: thread " + Thread.currentThread().getName());

            }

        }
    }
*/

}