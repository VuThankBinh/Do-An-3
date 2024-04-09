package com.example.dhbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class layout_home1 extends AppCompatActivity {

    ImageView as1,as2,bacnoi,play1;
    RelativeLayout lin3;
    MediaPlayer mp;
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

        setContentView(R.layout.activity_layout_home1);
        bacnoi=findViewById(R.id.xb);
        as1=findViewById(R.id.asleft);
        as2=findViewById(R.id.asright);
        lin3=findViewById(R.id.line3);
        play1=findViewById(R.id.startgame);
        LinearLayout lin = findViewById(R.id.xoay);
        ImageView img=findViewById(R.id.name);
        Animation animationBlink = AnimationUtils.loadAnimation(this, R.anim.bink);
        Animation xoayxoay= AnimationUtils.loadAnimation(this, R.anim.laclubtn);
        Animation blink= AnimationUtils.loadAnimation(this, R.anim.blink);
        AnimationSet animSet = new AnimationSet(true);

        animSet.addAnimation(xoayxoay);
        animSet.addAnimation(blink);
        lin.setAnimation(animSet);
        img.setAnimation(animationBlink);



        Resources res = getResources();
        AnimationDrawable animationDrawable = (AnimationDrawable) res.getDrawable(R.drawable.listanh);
        bacnoi.setImageDrawable(animationDrawable);
        mp = new MediaPlayer();
        // Bắt đầu animation
        animationDrawable.start();
        CSDL csdl=new CSDL(getApplicationContext());
//        csdl.db = new DataBase(getApplicationContext(), "DHBC.sql", null, 1);
        csdl.TaoCSDL(getApplicationContext());

        Animation laclu1 = AnimationUtils.loadAnimation(this, R.anim.laclu);

        Animation laclu2 = AnimationUtils.loadAnimation(this, R.anim.laclubtn);
        Animation laclu3 = AnimationUtils.loadAnimation(this, R.anim.laclu3);
        as1.startAnimation(laclu1);
        as2.startAnimation(laclu3);
        mp = new MediaPlayer();
        try {
            mp.setDataSource(getResources().openRawResourceFd(R.raw.chaomung0));
            mp.prepare();
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // Hiển thị nút sau khi audio kết thúc
                    play1.setVisibility(View.VISIBLE);
                    lin3.setVisibility(View.VISIBLE);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mp.reset();
                    mp.setDataSource(getResources().openRawResourceFd(R.raw.huonglen1));
                    mp.prepare();

                    mp.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mp.isPlaying()) {
                                mp.stop();
                                startActivity(new Intent(layout_home1.this, layout_home2.class));
                            }
                        }
                    }, 1000);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Kiểm tra xem audio có được tạm dừng không và nếu có thì tiếp tục phát
        if (mp != null && !mp.isPlaying()) {
            mp.start();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Kiểm tra nếu audio đang phát
        if (mp != null && mp.isPlaying()) {
            // Tạm dừng audio
            mp.pause();
        }
    }
}