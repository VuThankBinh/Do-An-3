package com.example.dhbc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class layout_home2 extends AppCompatActivity {

    ImageView back,bacnoi;
    TextView level,slgRuby,battat;

    Button choilai, start;
    LinearLayout lin1;

    MediaPlayer mp,mp1;
    CSDL csdl;
    AnimationDrawable animationDrawable;
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
        setContentView(R.layout.activity_layout_home2);
        back=findViewById(R.id.back1);
        start=findViewById(R.id.choitiep);
        bacnoi=findViewById(R.id.bacnoi);
        level=findViewById(R.id.level);
        slgRuby=findViewById(R.id.ruby);
        lin1=findViewById(R.id.line1);
        battat=findViewById(R.id.battat);
        choilai=findViewById(R.id.choilai);
        mp1=new MediaPlayer();
        csdl=new CSDL(getApplicationContext());
        boolean nhacXB = layout_home1.prefs.getBoolean("isXB", false);
        float volumn1=layout_home1.prefs.getFloat("volumnXB",1);
        CauHoi ch=csdl.HienCSDL(getApplicationContext());
        if(ch.getId()==-1){
            level.setText("Xuan Bac");
            start.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn đã chơi hết các level, bạn có muốn chơi lại không?");
            builder.setCancelable(false);
            // Nút xác nhận
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng nhấn nút xác nhận
                    // Thêm code xử lý ở đây
                    csdl.ChoiLai(layout_home2.this);
                    recreate();
                }
            });

            // Nút hủy
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Xử lý khi người dùng nhấn nút hủy
                    dialog.dismiss(); // Đóng dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            level.setText(String.valueOf(ch.getId()));
        }

        int slgRuby1= csdl.HienRuby(layout_home2.this);
        slgRuby.setText(String.valueOf(slgRuby1));

        choilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mp1.setDataSource(getResources().openRawResourceFd(R.raw.huonglen1));
                    mp1.setVolume(volumn1,volumn1);
                    mp1.prepare();

                    mp1.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mp1.isPlaying()) {
                                mp1.stop();
                                showConfirmationDialog();
                            }
                        }
                    }, 1000);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
                try {
                    mp.stop();
                    mp.reset();
                    mp1.setDataSource(getResources().openRawResourceFd(R.raw.huonglen1));
                    mp1.setVolume(volumn1,volumn1);
                    mp1.prepare();

                    mp1.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mp1.isPlaying()) {
                                mp1.stop();
                                startActivity(new Intent(layout_home2.this,MainActivity.class));
                                layout_home2.this.finish();
                            }
                        }
                    }, 1000);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });



        Resources res = getResources();
        AnimationDrawable animationDrawable = (AnimationDrawable) res.getDrawable(R.drawable.listanh);
        bacnoi.setImageDrawable(animationDrawable);
        mp = new MediaPlayer();
        // Bắt đầu animation
        animationDrawable.start();

        if(nhacXB){
            try {
                mp.setDataSource(getResources().openRawResourceFd(R.raw.rule0));
                mp.setVolume(volumn1,volumn1);
                mp.prepare();
                mp.start();

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

    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc chắn muốn thực hiện hành động này không?");

        // Nút xác nhận
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút xác nhận
                // Thêm code xử lý ở đây
                csdl.ChoiLai(layout_home2.this);
                recreate();
            }
        });

        // Nút hủy
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút hủy
                dialog.dismiss(); // Đóng dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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