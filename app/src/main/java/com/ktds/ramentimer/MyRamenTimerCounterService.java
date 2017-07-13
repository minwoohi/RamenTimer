package com.ktds.ramentimer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class MyRamenTimerCounterService extends Service {

    private Handler handler;
    private TextToSpeech tts;
    private int i;

    private boolean isBinding;

    private IMyRamenInterface.Stub binder = new IMyRamenInterface.Stub() {
        @Override
        public int getCount() throws RemoteException {
            return i;
        }
    };

    public MyRamenTimerCounterService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isBinding = true;
        handler = new Handler();
        i = 0;
        Thread counter = new Thread(new MyRamenTimerCounterService.CountThread());
        counter.start();
    }
    @Override
    public IBinder onBind(Intent intent) {
        isBinding = true;
        Log.d("COUNT", "BIND");
        i = intent.getIntExtra("TIME", 0);
        return  binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("COUNT", "UNBIND");
        isBinding = false;
        while (i !=-1){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        i=0;

        return true;
    }
    class CountThread implements Runnable{

        @Override
        public void run() {
            for ( ; i>=0; i--){
                if(!isBinding){
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            Toast.makeText(MyRamenTimerCounterService.this, "타이머가 종료되었습니다.", Toast.LENGTH_SHORT ).show();

                            tts.setLanguage(Locale.ENGLISH);//말하는 언어
                            tts.setSpeechRate(1.0f);//말하는 속도

                            tts.speak("The end", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                }
            });
            i = -1;
        }
    }
}
