package com.example.myserviceapplication;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);
        downloadSongs(songName);

    }
    public void downloadSongs(String songName) {
        Log.e("TAG", "downloadSongs: intent  starting");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "downloadSongs: download completed intent service " + songName);
    }


}