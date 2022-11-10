package com.task.stopwatch1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopWatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        // 앱의 화면이 처음 구성될 때 savedInstanceState는 null
        // 화면이 다시 생성되고 파괴되기 전에 onSaveInstanceState가 호출되면 생성된 Bundle 객체를 액티비티에 전달
        // 해당 메소드로 화면이 회전했을 때도 데이터를 유지할 수 있음
        runTimmer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
        //메소드는 Bundle 객체를 인자로 받음, Bundle 객체는 여러 타입의 데이터를 하나의 객체로 만듦
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }
    protected void onResume() {
        super.onResume();
        if(wasRunning) {
            running = true;
        }
    }

    public void onClickStart(View view) {
        running = true;
    }
    public void onClickStop(View view) {
        running = false;
    }
    public void onClickReset(View view) {
        seconds = 0;
        running = false;
    }

    private void runTimmer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minute = (seconds%3600) / 60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minute, secs);
                timeView.setText(time);
                if(running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000); // 1000 Millisecond마다 this 반복
            }
        });
    }

}