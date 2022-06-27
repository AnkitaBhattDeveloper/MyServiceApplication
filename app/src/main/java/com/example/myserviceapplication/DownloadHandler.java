package com.example.myserviceapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class DownloadHandler extends Handler {
    MyDownloadService myDownloadService;
    public  static final String SERVICE_MESSAGE = "ServiceMessage";
    //ResultReceiver resultReceiver;
    public DownloadHandler() {}
    Context context;

    @Override
    public void handleMessage(@NonNull Message msg) {
        Log.e("TAG", "handleMessage: " + msg.obj.toString());
        downloadSongs(msg.obj.toString());


        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.MESSAGE_KEY,msg.obj.toString());
       // resultReceiver.send(MainActivity.RESULT_OK,bundle);
        sendMessageToUI(msg.obj.toString());

       // myDownloadService.stopSelf();
        boolean result = myDownloadService.stopSelfResult(msg.arg1);
        Log.e("TAG", "handleMessage: stop self result = "+result+"  "+msg.arg1);

    }

    public void sendMessageToUI(String s)
    {
        Intent intent = new Intent(SERVICE_MESSAGE);
        intent.putExtra(MainActivity.MESSAGE_KEY,s);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

   public void downloadSongs(String songName) {
        Log.e("TAG", "downloadSongs: starting");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("TAG", "downloadSongs: download completed " + songName);
    }

    public void setMyDownloadService(MyDownloadService myDownloadService) {
        this.myDownloadService = myDownloadService;
    }
      public void setContext(Context context)
      {
          this.context = context;
      }
/*    public void setResultReceiver(ResultReceiver resultReceiver) {
        this.resultReceiver = resultReceiver;
    }*/

}
