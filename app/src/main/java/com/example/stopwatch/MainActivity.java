package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.stopwatch.views.Clock;

import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    private Canvas clockCanvas;
    private Bitmap bitmap;
    private Paint PaintWhite;
    private float width;
    private float height;

    private Button playBt;
    private Button pauseBt;
    private Button resetBt;
    private Clock clock;

    private Handler h;
    private Runnable r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        setListeners();
    }


    private void setListeners() {
        playBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBt.setClickable(false);
                r.run();
            }
        });
        pauseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBt.setClickable(true);
                h.removeCallbacks(r);
            }
        });
        resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClockDetails.ang=0;
                ClockDetails.angMin=0;
                playBt.setClickable(true);
                h.removeCallbacks(r);
            }
        });
    }

    private void setup() {
        h=new Handler();
        playBt=findViewById(R.id.Bt_play);
        pauseBt=findViewById(R.id.Bt_pause);
        resetBt=findViewById(R.id.Bt_Reset);
        clock=(Clock)findViewById(R.id.IV_clock);
        r=new Runnable() {
            @Override
            public void run() {
                ClockDetails cd=new ClockDetails();
                ClockDetails.ang+=6;
                ClockDetails.angMin+=0.1;
//                Log.i("Main", "Angle : "+ClockDetails.ang);
//                Log.i("Main", "Angle : "+ClockDetails.ang);
                h.postDelayed(r,100);
            }
        };
    }
}