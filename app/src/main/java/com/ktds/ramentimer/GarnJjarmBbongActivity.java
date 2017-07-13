package com.ktds.ramentimer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GarnJjarmBbongActivity extends AppCompatActivity {


    private boolean isBinding;

    private TextView tv_min;
    private TextView tv_counter;
    private Button btn_play;
    private Button btn_stop;

    private Handler handler;

    private IMyRamenInterface binder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMyRamenInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garn_jjarm_bbong);
        setTitle("간짬뽕");

        handler = new Handler();

        tv_min = (TextView) findViewById(R.id.tv_min);
        tv_counter = (TextView) findViewById(R.id.tv_counter);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBinding){
                    Intent intent = new Intent(GarnJjarmBbongActivity.this, MyRamenTimerCounterService.class);
                    intent.putExtra("TIME", 240);//180초 끝나면 카운팅 끝을 하고 싶을때
                    bindService(intent, connection, BIND_AUTO_CREATE);//바인더라는 객체를 공유함.
                    Thread thread = new Thread(new GarnJjarmBbongActivity.GetCountThread());
                    thread.start();
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBinding) {
                    isBinding = false;
                    unbindService(connection);
                }
            }
        });
    }
    //쓰레드를 띄운다.
    class GetCountThread implements Runnable {

        int count = 0;
        int count1 = 0;

        @Override
        public void run() {
            //무한 반복을 시킨다. 서비스가 언제 끝날지 모르기 떄문이다.
            isBinding = true;

            //바인더와 쓰레드는 별개로 돌아가서 바인딩을 참이라고 해주고 해야함.
            while (isBinding) {
                if (binder == null) {
                    continue;
                }
                try {
                    count = binder.getCount();
                    Log.d("COUNT", count + "");
                    if (count == -1) {
                        break;//i값이 -1일 때 쓰레드를 종료해라.
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_min.setText(count/60+"");
                        tv_counter.setText(count%60 + "");
                    }
                });
                //슬립을 안해주면 쓰레드는 계속 돌아간다.
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            if (isBinding) {
                //루프가 끝나면 서비스와 액티비티 간에 연결되있는 걸 끊어버린다.
                GarnJjarmBbongActivity.this.unbindService(connection);
            }
            isBinding = false;
        }
    }
}
