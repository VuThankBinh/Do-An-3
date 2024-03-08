package com.example.dhbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

public class layout_home2 extends AppCompatActivity {

    ImageView back, start,bacnoi;
    TextView level,slgRuby,battat;
    CheckBox nhacnen;
    Button choilai;
    LinearLayout lin1;
    static boolean nhacback=true;
    MediaPlayer mp;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home2);
        back=findViewById(R.id.back1);
        start=findViewById(R.id.startgame);
        bacnoi=findViewById(R.id.bacnoi);
        level=findViewById(R.id.level);
        slgRuby=findViewById(R.id.ruby);
        nhacnen=findViewById(R.id.nhacnen);
        lin1=findViewById(R.id.line1);
        battat=findViewById(R.id.battat);
        choilai=findViewById(R.id.choilai);

        CSDL csdl=new CSDL(getApplicationContext());
        CauHoi ch=csdl.HienCSDL(getApplicationContext());
        level.setText("Level "+String.valueOf(ch.getId()));
        int slgRuby1= csdl.HienRuby(layout_home2.this);
        slgRuby.setText(String.valueOf(slgRuby1));
        nhacnen.setChecked(true);
        choilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csdl.ChoiLai(layout_home2.this);
//                view.invalidate();
                recreate();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                layout_home2.this.finish();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(layout_home2.this,MainActivity.class));
            }
        });
        nhacnen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                   nhacback= !nhacback;
                   battat.setText("Nhạc nền đã bật");

                } else {
                    nhacback= !nhacback;

                    battat.setText("Nhạc nền đã tắt");
                }
                lin1.setVisibility(View.VISIBLE);
            }
        });


        Resources res = getResources();
        AnimationDrawable animationDrawable = (AnimationDrawable) res.getDrawable(R.drawable.listanh);
        bacnoi.setImageDrawable(animationDrawable);
        mp = new MediaPlayer();
        // Bắt đầu animation
        animationDrawable.start();
        try {
            mp.setDataSource(getResources().openRawResourceFd(R.raw.rule0));
            mp.prepare();
            mp.start();
//            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    // Hiển thị nút sau khi audio kết thúc
//                    play1.setVisibility(View.VISIBLE);
//                    lin3.setVisibility(View.VISIBLE);
//                }
//            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animationDrawable.stop();
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Giải phóng MediaPlayer khi activity bị destroy
        if (mp != null) {
            mp.release();
            mp = null;
        }
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