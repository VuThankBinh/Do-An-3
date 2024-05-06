package com.example.dhbc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

public class layout_home1 extends AppCompatActivity {

    ImageView as1,as2,bacnoi,play1,chua,acong;
    RelativeLayout lin3;
    static SharedPreferences prefs;
    MediaPlayer mp,mp1;
     static boolean nhacXB ;
    static float volumn1;
    CSDL csdl;
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
        prefs= getSharedPreferences("game", MODE_PRIVATE);
        bacnoi=findViewById(R.id.xb);
        as1=findViewById(R.id.asleft);
        as2=findViewById(R.id.asright);
        chua=findViewById(R.id.chua);
        Animation chay1=AnimationUtils.loadAnimation(this,R.anim.chuchay1);

        chua.setAnimation(chay1);
        acong=findViewById(R.id.acong);
        Animation chay2=AnimationUtils.loadAnimation(this,R.anim.chuchay1);
        acong.setAnimation(chay2);
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
        csdl=new CSDL(getApplicationContext());
//        csdl.db = new DataBase(getApplicationContext(), "DHBC.sql", null, 1);
        csdl.TaoCSDL(getApplicationContext());
        csdl.insertNewData();

        csdl.insertNewAvt();
        csdl.insertNewKhung();
        Animation laclu1 = AnimationUtils.loadAnimation(this, R.anim.laclu);

        Animation laclu2 = AnimationUtils.loadAnimation(this, R.anim.laclubtn);
        Animation laclu3 = AnimationUtils.loadAnimation(this, R.anim.laclu3);

        as1.startAnimation(laclu1);
        as2.startAnimation(laclu3);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnXB",1);

        if(nhacXB){
            try {
                mp.setDataSource(getResources().openRawResourceFd(R.raw.chaomung0));
                mp.setVolume(volumn1,volumn1);
                mp.prepare();
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        mp1= new MediaPlayer();
//        showDialogDatTen();

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(csdl.KiemTraNhanVat(layout_home1.this)){
                    showDialogDatTen();

                }
                else {
                    try {
                        mp.stop();
                        mp.reset();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.huonglen1));
                        mp1.setVolume(volumn1,volumn1);
                        mp1.prepare();

                        mp1.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mp1.isPlaying()) {
                                    mp1.stop();
                                    startActivity(new Intent(layout_home1.this, layout_home2.class));
                                }
                            }
                        }, 1000);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

    }
    private void showDialogDatTen(){
        Dialog dialog = new Dialog(layout_home1.this,android.R.style.Theme_Dialog );

        dialog.setContentView(R.layout.dialog_datten);
        EditText tvname=dialog.findViewById(R.id.tvname);
        ImageButton btnXN=dialog.findViewById(R.id.btnname);

        TextView cham=dialog.findViewById(R.id.cham);
        TextView chuy=dialog.findViewById(R.id.chuy);
        chuy.setVisibility(View.GONE);
        Animation blinkk=AnimationUtils.loadAnimation(this,R.anim.blink2);
        cham.setAnimation(blinkk);
        dialog.setCancelable(false);
        cham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        cham.setVisibility(View.GONE);
        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvname.getText().toString().trim().equals("")){
                    Toast.makeText(layout_home1.this, "Bạn phải nhập tên nhân vật", Toast.LENGTH_SHORT).show();
                }
                else {
                    csdl.TaoNhanVat(tvname.getText().toString());
                    dialog.dismiss();
                    try {
                        mp.stop();
                        mp.reset();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.huonglen1));
                        mp1.setVolume(volumn1,volumn1);
                        mp1.prepare();

                        mp1.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mp1.isPlaying()) {
                                    mp1.stop();
                                    startActivity(new Intent(layout_home1.this, layout_home2.class));
                                }
                            }
                        }, 1000);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.show();
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