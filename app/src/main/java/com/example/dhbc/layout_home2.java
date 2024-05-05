package com.example.dhbc;

import static com.example.dhbc.layout_home1.nhacXB;
import static com.example.dhbc.layout_home1.volumn1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
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
    TextView level,slgRuby,battat,tvname;

    Button choilai, start;
    LinearLayout lin1;

    MediaPlayer mp,mp1;
    CSDL csdl;
    ThongTinNguoiChoi thongTinNguoiChoi;
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
        tvname=findViewById(R.id.tvname);

        mp1=new MediaPlayer();
        csdl=new CSDL(getApplicationContext());
        thongTinNguoiChoi=csdl.HienThongTinNhanVat();
        CauHoi ch=csdl.HienCSDL(getApplicationContext());
        if(ch.getId()==-1){
            level.setText("Xuan Bac");
            start.setVisibility(View.GONE);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Xác nhận");
//            builder.setMessage("Bạn đã chơi hết các level, bạn có muốn chơi lại không?");
//            builder.setCancelable(false);
//            // Nút xác nhận
//            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Xử lý khi người dùng nhấn nút xác nhận
//                    // Thêm code xử lý ở đây
//                    csdl.ChoiLai(layout_home2.this);
//                    recreate();
//                }
//            });
//
//            // Nút hủy
//            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Xử lý khi người dùng nhấn nút hủy
//                    dialog.dismiss(); // Đóng dialog
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
            showDialogChoiLai();
        }
        else {
            level.setText(String.valueOf(ch.getId()));
        }

//        int slgRuby1= csdl.HienRuby(layout_home2.this);
        int slgRuby1= thongTinNguoiChoi.getRuby();
        slgRuby.setText(String.valueOf(slgRuby1));
        tvname.setText(thongTinNguoiChoi.getName());

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
//                                showConfirmationDialog();
                                showDialogChoiLai();
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
                    mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                    mp1.setVolume(volumn1,volumn1);
                    mp1.prepare();

                    mp1.start();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mp1.isPlaying()) {
//                                mp1.stop();
//
//                            }
//                        }
//                    }, 1000);
                    startActivity(new Intent(layout_home2.this,MainActivity.class));
                    layout_home2.this.finish();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });



        Resources res = getResources();
        AnimationDrawable animationDrawable = (AnimationDrawable) res.getDrawable(R.drawable.listanh);
        bacnoi.setImageDrawable(animationDrawable);
        bacnoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(layout_home2.this,cuahangvatpham.class));
                layout_home2.this.finish();
            }
        });
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

    private void showDialogChoiLai() {
        Dialog dialog = new Dialog(layout_home2.this, android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_choilai);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        Button close=dialog.findViewById(R.id.tuchoi);
        Button ok=dialog.findViewById(R.id.chapnhan);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csdl.ChoiLai(layout_home2.this);
                recreate();
            }
        });
        dialog.show();

    }

    private void ShareLinkApp(){
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Thay đổi thành URI của liên kết bạn muốn chia sẻ
        Uri uri = Uri.parse("https://drive.google.com/drive/folders/1j1AV8odUsTbpCG3Zhma5Kqj-_QB8TULn?usp=sharing");

        // Đặt nội dung của Intent thành liên kết
        intent.putExtra(Intent.EXTRA_TEXT, uri.toString());

        // Thêm cờ cho phép ứng dụng đính kèm xử lý dữ liệu từ URI được cung cấp
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Đặt loại dữ liệu của Intent thành "text/plain"
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share to..."));
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