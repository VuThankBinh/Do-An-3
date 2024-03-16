package com.example.dhbc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
    RecyclerView listcauhoi,dapan;
    ImageView help,shop,layout_2,back;
    TextView level,slgRuby;
    MediaPlayer mp, mp1;
    Button nextdemo;
    CSDL csdl;
    CauHoi ch;
    List<Integer> vi_tri_dau_cach;
    ArrayList<String> cautraloi;
    ArrayList<Integer> vitrioDapAn;
    ArrayList<String> arr2,arr;
    int index=0;
    CauHoiAdapter adapter;
    DapAnAdapter adap;
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
        setContentView(R.layout.activity_main);
        help = findViewById(R.id.help);
        shop = findViewById(R.id.napvip);
        layout_2 = findViewById(R.id.layout_2);
        level=findViewById(R.id.level);
        slgRuby=findViewById(R.id.ruby);
        back=findViewById(R.id.back1);
        csdl= new CSDL(getApplicationContext());
        loadTrang();
        nextdemo=findViewById(R.id.demonext);
        nextdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s="";
                for (int i=0;i<vitrioDapAn.size();i++){
                    s+=vitrioDapAn.get(i)+", ";
                }
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        mp = new MediaPlayer();
        mp1 = new MediaPlayer();
        if(layout_home2.nhacback==false){
            try {
                mp.setDataSource(getResources().openRawResourceFd(R.raw.nhac1));
                mp.prepare();
                mp.start();

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.start();
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogHelp();
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogShop();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
                Intent intent = new Intent(MainActivity.this, layout_home2.class);
                startActivity(intent);
            }
        });


    }

    private void showDialogHelp() {
        Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialog.setContentView(R.layout.dialog_help);
        ImageView close=dialog.findViewById(R.id.closehelp);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
        dialog.show();

    }
    private void showDialogShop() {
        Dialog dialog = new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar );

        dialog.setContentView(R.layout.dialog_shop);
        ImageView close=dialog.findViewById(R.id.closebuy);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
//        Window window = dialog.getWindow();
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setGravity(Gravity.CENTER);
        dialog.show();

    }
    FlexboxLayoutManager layoutManager,layoutManager2;
    public void loadTrang(){
        vi_tri_dau_cach=new ArrayList<>();
        vitrioDapAn=new ArrayList<>();
        ch= csdl.HienCSDL(getApplicationContext());
        int slgRuby1= csdl.HienRuby(MainActivity.this);
        slgRuby.setText(String.valueOf(slgRuby1));
        level.setText("Level "+String.valueOf(ch.getId()));
        String fileName = ch.getHinhAnh().toString(); // Lấy tên tệp ảnh từ đối tượng baiHat
        int resId = getResources().getIdentifier(fileName, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
        if (resId != 0) {
            layout_2.setImageResource(resId); // Thiết lập hình ảnh cho ImageView
        } else {
            // Xử lý trường hợp không tìm thấy tệp ảnh
        }
        String dapAn = ch.getDapAn();
        String dapAn2 = ch.getHinhAnh();
        arr2= new ArrayList<>();

        for (int i = 0; i < dapAn2.length(); i++) {
            // Convert each character to a string and add it to the ArrayList

            arr2.add(String.valueOf(dapAn2.charAt(i)));

        }

        Random random = new Random();
        for (int i = 0; i < random.nextInt(2) + 6; i++) {
            // Generate a random character between 'a' and 'z'
            char randomChar = (char) (random.nextInt(26) + 'a');
            arr2.add(String.valueOf(randomChar));
        }
        Collections.shuffle(arr2);

        arr = new ArrayList<String>();
        cautraloi=new ArrayList<>();
        for (int i = 0; i < dapAn.length(); i++) {
            // Convert each character to a string and add it to the ArrayList
            if(dapAn.charAt(i)==' ') {
                arr.add(String.valueOf(""));
                vi_tri_dau_cach.add(i);
                cautraloi.add("");

                vitrioDapAn.add(-2);
            }
            else {
                arr.add(String.valueOf("1"));
                cautraloi.add("1");
                vitrioDapAn.add(-1);
            }

        }

        level.setText("Level "+String.valueOf(ch.getId()));
        listcauhoi = findViewById(R.id.listcauhoi);
        dapan = findViewById(R.id.dapan);
        adapter = new CauHoiAdapter(this, arr,this);
        adap = new DapAnAdapter(this, arr2,this);

        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        layoutManager2 = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        listcauhoi.setLayoutManager(layoutManager);
        dapan.setLayoutManager(layoutManager2);
        listcauhoi.setAdapter(adapter);
        dapan.setAdapter(adap);

    }
    //click đáp án
    @Override
    public void onItemClick(int position) {


//        Toast.makeText(this, "vị trí: "+position, Toast.LENGTH_SHORT).show();
        String s=arr2.get(position).toString().toUpperCase();

        if(s.trim().length()>0 &&s!=""&&s!=null&&index<arr.size()){
            arr2.set(position," ");
            boolean foundNegativeIndex = false; // Biến đánh dấu để chỉ thực hiện cập nhật arr một lần
            for (int j = 0; j < vitrioDapAn.size(); j++) {
                if (!foundNegativeIndex && vitrioDapAn.get(j) < 0) {
                    // Nếu chưa tìm thấy phần tử âm và vitrioDapAn[j] nhỏ hơn 0, cập nhật arr và đánh dấu đã tìm thấy
                    if (vitrioDapAn.get(j) == -1) {
                        vitrioDapAn.set(j, position);
                        foundNegativeIndex = true;
                        cautraloi.set(j, s);

                    } else {
                        cautraloi.set(j, "");
                        cautraloi.set(j + 1, s);


                    }

                }
            }




            //lỗi ở khu vực này
            // lỗi ở index


            adap.notifyDataSetChanged();
//            adap.notifyDataSetChanged();
//            listcauhoi.setLayoutManager(layoutManager);
            listcauhoi.setAdapter( new CauHoiAdapter(this,cautraloi,this));
            dapan.setAdapter( new DapAnAdapter(this,arr2,this));
        }
    }

    //click câu hỏi
    @Override
    public void onItemCauHoiClick(int position) {
//        Toast.makeText(this, "vị trí: "+position, Toast.LENGTH_SHORT).show();
        String s=cautraloi.get(position).toString().toUpperCase();
        if(s.length()>0 &&s!=""&&s!=null){
            cautraloi.set(position,"");

            for(int i=0;i<vi_tri_dau_cach.size();i++){
                if(position==vi_tri_dau_cach.get(i)){

//                    cautraloi.set(index+1,s);
//                    index+=2;
                }
                else {

                    cautraloi.set(position," ");
                    arr2.set(vitrioDapAn.get(position),s);

                    vitrioDapAn.set(position,-1);
                }
            }

//            vitrioDapAn.add(position);
            listcauhoi.setAdapter( new CauHoiAdapter(this,cautraloi,this));
//            listcauhoi.setLayoutManager(layoutManager);
            dapan.setAdapter( new DapAnAdapter(this,arr2,this));
        }
    }
}
//lỗi position: xóa câu trả lời bị lỗi
// chỗ đã xóa khi bấm lại không thêm vào đc