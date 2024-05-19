package com.example.dhbc.views;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.dhbc.R;

import java.io.IOException;

public class ChonCheDoChoi extends AppCompatActivity {
    static boolean nhacXB ;
    static float volumn1;
    static SharedPreferences prefs;
    AppCompatButton h1,h2,h3,h4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Đặt cờ cho cửa sổ
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Ẩn thanh công cụ (navigation bar)
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_chon_che_do_choi);
        prefs= getSharedPreferences("game", MODE_PRIVATE);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnXB",1);
        AnhXa();
        MediaPlayer mp1=new MediaPlayer();
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nhacXB){
                    try {
                        mp1.stop();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                        mp1.setVolume(volumn1,volumn1);
                        mp1.prepare();

                        mp1.start();

//                    layout_home2.this.finish();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                startActivity(new Intent(ChonCheDoChoi.this, GameShowRound1.class));
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nhacXB){
                    try {
                        mp1.stop();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                        mp1.setVolume(volumn1,volumn1);
                        mp1.prepare();

                        mp1.start();

//                    layout_home2.this.finish();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                startActivity(new Intent(ChonCheDoChoi.this, MainActivity.class));
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nhacXB){
                    try {
                        mp1.stop();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                        mp1.setVolume(volumn1,volumn1);
                        mp1.prepare();

                        mp1.start();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                ChonCheDoChoi.this.finish();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mp1.stop();
                    mp1.reset();
                    mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                    mp1.setVolume(volumn1,volumn1);
                    mp1.prepare();

                    mp1.start();

//                    ChonCheDoChoi.this.finish();
//                    layout_home2.this.finish();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void AnhXa() {
        h1=findViewById(R.id.chedogameshow);
        h2=findViewById(R.id.chedocoban);
        h3=findViewById(R.id.btnback);
        h4=findViewById(R.id.btninfo);
    }

}