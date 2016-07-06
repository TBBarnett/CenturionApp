package com.example.tayle.centurion;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerText;
    TextView downNumber;
    TextView togoNumber;

    private Handler handler;
    private int timeCounter = 1;
    private int down = 0;
    private int togo = 100;
    private int buzzer = 60;
    private int end = 6000;

    private int flashes = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView) findViewById(R.id.timerText);
        downNumber = (TextView) findViewById(R.id.downNumber);
        togoNumber = (TextView) findViewById(R.id.togoNumber);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                timeCounter += 1;

                int seconds = timeCounter % 60;
                int minutes = timeCounter / 60;
                String stringTime = String.format("%02d:%02d", minutes, seconds);
                timerText.setText(stringTime);

                if(timeCounter < end) {
                    if(timeCounter == buzzer){
                        flash();
                        soundBuzzer();
                        down += 1;
                        togo -= 1;
                        downNumber.setText(Integer.toString(down));
                        togoNumber.setText(Integer.toString(togo));
                        buzzer += 60;
                    }
                    handler.postDelayed(this, 1000);
                }

            }
        };
        handler.postDelayed(runnable, 1000);
    }

    protected void flash(){

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(timerText.getVisibility() == View.VISIBLE){
                    timerText.setVisibility(View.INVISIBLE);
                }else{
                    timerText.setVisibility(View.VISIBLE);
                }

                if(flashes == 1) {
                   handler.postDelayed(this, 500);
                    flashes += 1;
                }
            }

        };
        handler.postDelayed(runnable, 500);
        flashes = 1;

    }

    protected void soundBuzzer(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
