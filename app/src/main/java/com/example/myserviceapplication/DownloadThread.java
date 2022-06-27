package com.example.myserviceapplication;

import android.os.Looper;
import android.util.Log;

public class DownloadThread extends Thread{
    DownloadHandler mHandler;

    @Override
    public void run() {


        Looper.prepare();//  create the message Queue
        mHandler=new DownloadHandler();
        Looper.loop();



    }

}
