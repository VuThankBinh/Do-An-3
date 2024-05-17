package com.example.dhbc.views;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.dhbc.CSDL;
import com.example.dhbc.ClassDL.CauHoi;
import com.example.dhbc.ClassDL.ThongTinNguoiChoi;
import com.example.dhbc.R;

import java.io.IOException;

public class layout_home2 extends AppCompatActivity {

    static boolean nhacXB ;
    static boolean nhacback;
    static float volumn1,volumn2;
    static SharedPreferences prefs;
    AppCompatButton choingay,choilai,bxh,thoat,shop,settings,share;


    MediaPlayer mp,mp1;
    CSDL csdl;
    ImageView avt;
    TextView hightcore,name,ruby3;
    ThongTinNguoiChoi tt;
    private void AnhXa(){
        name=findViewById(R.id.tvnameNG);
        hightcore=findViewById(R.id.hightCore);
        avt=findViewById(R.id.avt1);
        ruby3=findViewById(R.id.ruby3);
        choingay=findViewById(R.id.btnChoiNgay);
        choilai=findViewById(R.id.btnChoiLai);
        bxh=findViewById(R.id.btnBXH);
        thoat=findViewById(R.id.btnThoat);
        shop=findViewById(R.id.shop);
        settings=findViewById(R.id.setting);
        share=findViewById(R.id.share);

    }
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
        prefs= getSharedPreferences("game", MODE_PRIVATE);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnXB",1);
        AnhXa();
        csdl=new CSDL(getApplicationContext());
        csdl.TaoCSDL(getApplicationContext());
        csdl.insertNewData();

        csdl.insertNewAvt();
        csdl.insertNewKhung();
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnXB",1);
        tt=csdl.HienThongTinNhanVat();
        hightcore.setText("High score: "+ tt.getLevel());
        name.setText(tt.getName());
        ruby3.setText(String.valueOf(csdl.HienThongTinNhanVat().getRuby()));
        String fileAvt = "avt"+String.valueOf(tt.getAvt_id()); // Lấy tên tệp ảnh từ đối tượng baiHat
        int resId = getResources().getIdentifier(fileAvt, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
        String fileKhung = "khung"+String.valueOf(tt.getKhung_id()); // Lấy tên tệp ảnh từ đối tượng baiHat
        int resId2 = getResources().getIdentifier(fileKhung, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh

        if (resId != 0) {
            avt.setImageResource(resId); // Thiết lập hình ảnh cho ImageView
        } else {
            // Xử lý trường hợp không tìm thấy tệp ảnh
        }
        if (resId2 != 0) {
            avt.setBackgroundResource(resId2); // Thiết lập hình ảnh cho ImageView
        } else {
            // Xử lý trường hợp không tìm thấy tệp ảnh
        }
        mp1=new MediaPlayer();

        choilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nhacXB){
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

                                }
                            }
                        }, 1000);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                showDialogChoiLai();

            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                layout_home2.this.finish();
            }
        });
        choingay.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(layout_home2.this,ChonCheDoChoi.class));

            }
        });
        mp = new MediaPlayer();
        // Bắt đầu animation
//        animationDrawable.start();

        if(nhacXB){
            try {
                mp.setDataSource(getResources().openRawResourceFd(R.raw.rule0));
                mp.setVolume(volumn1,volumn1);
                mp.prepare();
                mp.start();

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
//                        animationDrawable.stop();
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB){
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
                startActivity(new Intent(layout_home2.this,cuahangvatpham.class));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp1.stop();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                        mp1.setVolume(volumn1, volumn1);
                        mp1.prepare();

                        mp1.start();

//                        startActivity(new Intent(layout_home2.this, ChonCheDoChoi.class));
//                    layout_home2.this.finish();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                ShareLinkApp();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp1.stop();
                        mp1.reset();
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.win));
                        mp1.setVolume(volumn1, volumn1);
                        mp1.prepare();

                        mp1.start();

//                        startActivity(new Intent(layout_home2.this, ChonCheDoChoi.class));
//                    layout_home2.this.finish();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                showDialogSettings();
            }
        });
    }
    private SeekBar volumeSeekBar1,volumeSeekBar2;
    private void ktraAmthanh() {
        if (nhacback) {
            try {
                mp.reset();
                mp.setDataSource(getResources().openRawResourceFd(R.raw.nhacback));
                mp.setVolume(volumn1,volumn1);
                mp.prepare();
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mp.start();
                    }
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void showDialogSettings() {
        Dialog dialog = new Dialog(layout_home2.this, android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_settings);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cham=dialog.findViewById(R.id.cham);
        TextView volumn1a=dialog.findViewById(R.id.volumn1);
        TextView volumn2a=dialog.findViewById(R.id.volumn2);
        ImageView nhacback12=dialog.findViewById(R.id.nhacback);
        ImageView nhacXB12=dialog.findViewById(R.id.nhacxb);
        ImageView sharelink=dialog.findViewById(R.id.sharelink);
        sharelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareLinkApp();
            }
        });
        Animation blinkk= AnimationUtils.loadAnimation(this,R.anim.blink2);
        cham.setAnimation(blinkk);
        cham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        volumeSeekBar1 = dialog.findViewById(R.id.seek1);
        volumeSeekBar2 = dialog.findViewById(R.id.seek2);
        volumeSeekBar1.setMax(100);
        int progress1 = (int) (100 - Math.pow(10, (1 - volumn1) * Math.log10(100)));
        volumn1a.setText(String.valueOf(progress1));
        volumeSeekBar1.setProgress(progress1); // Thiết lập mức âm lượng mặc định
        volumeSeekBar2.setMax(100);
        int progress2 = (int) (100 - Math.pow(10, (1 - volumn2) * Math.log10(100)));
        volumn2a.setText(String.valueOf(progress2));
        volumeSeekBar2.setProgress(progress2);
        // nhạc nền
        volumeSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress!=0){
                    nhacback12.setImageResource(R.drawable.notnhac);
                    nhacback=true;
                }
                else {
                    nhacback12.setImageResource(R.drawable.notnhac_mute);
                    nhacback=false;
                }
                volumn1 = (float) (1 - (Math.log(100 - progress) / Math.log(100)));
                mp.setVolume(volumn1, volumn1); // Thiết lập âm lượng của MediaPlayer
                volumn1a.setText(String.valueOf(progress));
                SharedPreferences.Editor editor = prefs.edit();
                editor.putFloat("volumnBack", volumn1);
                editor.putBoolean("isMute",nhacback);
                editor.apply();
                ktraAmthanh();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        //tiếng xuân bắc
        volumeSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress!=0){
                    nhacXB12.setImageResource(R.drawable.voicexb);
                    nhacXB=true;
                }
                else {
                    nhacXB12.setImageResource(R.drawable.voicexb_mute);
                    nhacXB=false;
                }
                volumn2 = (float) (1 - (Math.log(100 - progress) / Math.log(100)));
                mp1.setVolume(volumn2, volumn2);
                volumn2a.setText(String.valueOf(progress));
                SharedPreferences.Editor editor = prefs.edit();
                editor.putFloat("volumnXB", volumn2);
                editor.putBoolean("isXB",nhacXB);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        dialog.show();
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
        if (mp1 != null && !mp1.isPlaying()) {
            mp1.start();
        }
        tt=csdl.HienThongTinNhanVat();
        hightcore.setText("High score: "+ tt.getLevel());
        name.setText(tt.getName());
        ruby3.setText(String.valueOf(csdl.HienThongTinNhanVat().getRuby()));
        String fileAvt = "avt"+String.valueOf(tt.getAvt_id()); // Lấy tên tệp ảnh từ đối tượng baiHat
        int resId = getResources().getIdentifier(fileAvt, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
        String fileKhung = "khung"+String.valueOf(tt.getKhung_id()); // Lấy tên tệp ảnh từ đối tượng baiHat
        int resId2 = getResources().getIdentifier(fileKhung, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh

        if (resId != 0) {
            avt.setImageResource(resId); // Thiết lập hình ảnh cho ImageView
        } else {
            // Xử lý trường hợp không tìm thấy tệp ảnh
        }
        if (resId2 != 0) {
            avt.setBackgroundResource(resId2); // Thiết lập hình ảnh cho ImageView
        } else {
            // Xử lý trường hợp không tìm thấy tệp ảnh
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