package com.example.myserviceapplication;

import static com.example.myserviceapplication.Playlist.songs;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDownloadService extends Service {
  public DownloadThread downloadThread; // using thread and handler
//DownloadHandler mHandler = new DownloadHandler();


    public MyDownloadService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
       downloadThread = new DownloadThread();
        downloadThread.start();

        if (downloadThread == null) {
            Log.e("TAG", "onCreate: downloadThread is null " +downloadThread);
        }
        else
        {
            Log.e("TAG", "onCreate: downloadThread is not null " +downloadThread);

        }


   /*     MyAsyncTask asyncTask = new MyAsyncTask();
        asyncTask.execute();*/


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "onStart command" + intent.getStringExtra(MainActivity.MESSAGE_KEY));
        String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);

        //through handler*********

        Message msg = Message.obtain();
        msg.obj = songName;
        msg.arg1 = startId;

        downloadThread.mHandler.sendMessage(msg);
        downloadThread.mHandler.setMyDownloadService(MyDownloadService.this);
        downloadThread.mHandler.setContext(getApplicationContext());

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            downloadThread.mHandler.setResultReceiver(intent.getParcelableExtra(Intent.EXTRA_RESULT_RECEIVER));
        }*/

        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //*********

      /*  Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "run: " + Thread.currentThread().getName());
                downloadSongs(songName);
            }
        });
        thread.start();*/

        return START_REDELIVER_INTENT;
    }

   /* public void downloadSongs(String songname) {
        Log.e("TAG", "downloadSongs: Starting download ");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "Download Completed " + songname);
    }
*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: 'called ");
    }


   /* static class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            for (String song : songs) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               publishProgress(song);
            }
            return "all songs are downloading";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.e("TAG", "onProgressUpdate: " + values[0]+" "+values.length );
        }

        @Override
        protected void onPreExecute() {
            Log.e("TAG", "onPreExecute: called");
        }
    }*/
}