package com.vtb.dhbc.views;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vtb.dhbc.CSDL;
import com.vtb.dhbc.ClassDL.ThongTinNguoiChoi;
import com.vtb.dhbc.LoginGG;
import com.vtb.dhbc.R;
import com.vtb.dhbc.SreachPhongOnl;

import java.io.IOException;
import java.util.HashMap;

public class layout_home2 extends AppCompatActivity {

    static boolean nhacXB ;
    static boolean nhacback;
    static float volumn1,volumn2;
    static SharedPreferences prefs;
    AppCompatButton choingay,choilai,bxh,thoat,shop,settings,share,login;
    public static int RC_DEFAULT_SIGN_IN = 100;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;


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
        login=findViewById(R.id.login);
        mp=new MediaPlayer();
        mp1=new MediaPlayer();

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
        volumn1=prefs.getFloat("volumnBack",1);
        volumn2=prefs.getFloat("volumnXB",1);
        AnhXa();
        csdl=new CSDL(getApplicationContext());
        csdl.TaoCSDL();
        csdl.insertNewData();
        csdl.TaoCSDL_gameshow_round2();
        csdl.insertNewAvt();
        csdl.insertNewKhung();
        if(csdl.KiemTraNhanVat(this)){
            ShowDialogDoiTen();
        }
        if(!csdl.KiemTraNhanVat(layout_home2.this)){
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

        mp1=new MediaPlayer();


        thoat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                layout_home2.this.finish();
            }
        });
        choingay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp.stop();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(layout_home2.this,ChonCheDoChoi.class));
                    }
                },1000);


            }
        });
        bxh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp.stop();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(layout_home2.this, LeaderboardActivity.class));
                    }
                },1000);
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
                        mp.stop();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(layout_home2.this,cuahangvatpham.class));
                    }
                },1000);

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp.stop();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ShareLinkApp();
                    }
                },1000);

            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp.stop();
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
                showDialogSettings();
            }
        });
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        // Khởi tạo đối tượng Firebase Auth để thực hiện việc xác thực người dùng
        auth = FirebaseAuth.getInstance();
        // Khởi tạo đối tượng FirebaseDatabase để thực hiện lưu trữ thông tin người dùng
        firebaseDatabase = FirebaseDatabase.getInstance();

        // Khởi tạo đối tượng GoogleSignInOptions để yêu cầu việc xác thực bằng tài khoản Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        // Kiểm tra trạng thái đăng nhập của người dùng
        FirebaseUser currentUser = auth.getCurrentUser();
        if (auth.getCurrentUser() != null) {
            login.setBackgroundResource(R.drawable.logout);
            // Người dùng đã đăng nhập
//            Toast.makeText(this, "UserId: " + currentUser.getUid(), Toast.LENGTH_SHORT).show();

        } else {
            // Người dùng chưa đăng nhập
            login.setBackgroundResource(R.drawable.login);
        }
        choilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp.stop();
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
                if (currentUser != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(layout_home2.this, SreachPhongOnl.class));
                        }
                    },1000);
                } else {
                    Toast.makeText(layout_home2.this, "bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nhacXB) {
                    try {
                        mp.stop();
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
                if (auth.getCurrentUser() != null) {
                    signOut();
                } else {
                    signIn1();
                }
            }
        });
    }
    private void ShowDialogDoiTen() {
        Dialog dialog = new Dialog(layout_home2.this,android.R.style.Theme_Dialog );

        dialog.setContentView(R.layout.dialog_datten);
        EditText tvname=dialog.findViewById(R.id.tvname);
        ImageButton btnXN=dialog.findViewById(R.id.btnname);

        TextView cham=dialog.findViewById(R.id.cham);
        Animation blinkk= AnimationUtils.loadAnimation(this,R.anim.blink2);
        cham.setAnimation(blinkk);
        cham.setVisibility(View.GONE);
        dialog.setCancelable(false);
        cham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        cham.setVisibility(View.GONE);
        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvname.getText().toString().trim().equals("")){
                    Toast.makeText(layout_home2.this, "Tên nhân vật không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else {
                    csdl.TaoNhanVat(tvname.getText().toString());
                    Toast.makeText(layout_home2.this, "Bạn đã đặt tên thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
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
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.show();
    }
    private SeekBar volumeSeekBar1,volumeSeekBar2;

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
//                ktraAmthanh();
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

        // Kiểm tra nếu audio đang phát
        if (mp != null && mp.isPlaying()) {
            // Tạm dừng audio
            mp.pause();
        }
        if (mp1 != null && mp1.isPlaying()) {
            // Tạm dừng audio
            mp1.pause();
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
        if(!csdl.KiemTraNhanVat(layout_home2.this)){
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

    }
    @Override
    protected void onPause() {
        super.onPause();

        // Kiểm tra nếu audio đang phát
        if (mp != null && mp.isPlaying()) {
            // Tạm dừng audio
            mp.pause();
        }
        if (mp1 != null && mp1.isPlaying()) {
            // Tạm dừng audio
            mp1.pause();
        }
    }
    private void signOut() {
        auth.signOut();
        gsc.signOut().addOnCompleteListener(this, task -> {
            Toast.makeText(layout_home2.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

        });
        // Xóa dữ liệu của csdl và tạo lại
        csdl.recreateDatabase();
        login.setBackgroundResource(R.drawable.login);
        updatedl();
    }
    private void signIn1() {

        Intent intent=gsc.getSignInIntent();
        startActivityForResult(intent, RC_DEFAULT_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_DEFAULT_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuth1(account.getIdToken());

                login.setBackgroundResource(R.drawable.logout);
//                updatedl();

            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void updatedl(){
        tt=csdl.HienThongTinNhanVat2();
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

    private void firebaseAuth1(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user =auth.getCurrentUser();
                            ThongTinNguoiChoi thongTinNguoiChoi = csdl.HienThongTinNhanVat2();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("name", thongTinNguoiChoi.getName());
                            map.put("ruby", thongTinNguoiChoi.getRuby());
                            map.put("level", thongTinNguoiChoi.getLevel());
                            map.put("avt_id", thongTinNguoiChoi.getAvt_id());
                            map.put("khung_id", thongTinNguoiChoi.getKhung_id());map.put("avt_id", thongTinNguoiChoi.getAvt_id());
                            map.put("damua_khung", thongTinNguoiChoi.getDamua_khung());
                            map.put("damua_avt", thongTinNguoiChoi.getDamua_avt());


                            // Kiểm tra xem thông tin người dùng đã tồn tại chưa
                            firebaseDatabase.getReference().child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        csdl.getPlayerInfoFromFirebase();
                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        FirebaseUser user = auth.getCurrentUser();
                                        if (user != null) {
                                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                            DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(user.getUid());

                                            // Lấy thông tin người chơi từ Firebase
                                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    // Kiểm tra xem dataSnapshot có tồn tại không
                                                    if (dataSnapshot.exists()) {
                                                        ThongTinNguoiChoi thongTinNguoiChoi = dataSnapshot.getValue(ThongTinNguoiChoi.class);
                                                        if (thongTinNguoiChoi != null) {

                                                            hightcore.setText("High score: "+ thongTinNguoiChoi.getLevel());
                                                            name.setText(thongTinNguoiChoi.getName());
                                                            ruby3.setText(String.valueOf(thongTinNguoiChoi.getRuby()));
                                                            String fileAvt = "avt"+String.valueOf(thongTinNguoiChoi.getAvt_id()); // Lấy tên tệp ảnh từ đối tượng baiHat
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
                                                    } else {
                                                        System.out.println("User does not exist.");
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                    System.out.println("The read failed: " + databaseError.getMessage());
                                                }
                                            });
                                        } else {
                                            System.out.println("User not logged in.");
                                        }
                                        Toast.makeText(layout_home2.this, "Old player", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(layout_home2.this, "New player", Toast.LENGTH_SHORT).show();
                                        // Lưu thông tin người dùng vào Realtime Database
                                        firebaseDatabase.getReference().child("users").child(user.getUid())
                                                .setValue(map);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.w("TAG", "loadPost:onCancelled", error.toException());
                                }
                            });

                        }
                    }
                });
        updatedl();
    }
}