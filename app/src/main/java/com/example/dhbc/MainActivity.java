package com.example.dhbc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RecyclerView listcauhoi,dapan;
    ImageView help,shop,layout_2,back;
    TextView level,slgRuby;
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
        CSDL csdl = new CSDL(getApplicationContext());
        CauHoi ch = csdl.HienCSDL(getApplicationContext());
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
            }
        });
        ArrayList<String> arr2 = new ArrayList<>();

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

        ArrayList<String> arr = new ArrayList<String>();
            for (int i = 0; i < dapAn.length(); i++) {
                // Convert each character to a string and add it to the ArrayList
                if(dapAn.charAt(i)==' ') {
                    arr.add(String.valueOf(1));
                }
                else {
                    arr.add(String.valueOf(" "));
                }
            }
            level.setText("Level "+String.valueOf(ch.getId()));
            listcauhoi = findViewById(R.id.listcauhoi);
            dapan = findViewById(R.id.dapan);
            CauHoiAdapter adapter = new CauHoiAdapter(this, arr);
            DapAnAdapter adap = new DapAnAdapter(this, arr2);

            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getApplicationContext());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(getApplicationContext());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            listcauhoi.setLayoutManager(layoutManager);
            dapan.setLayoutManager(layoutManager2);
            listcauhoi.setAdapter(adapter);
            dapan.setAdapter(adap);


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
}
class CenterLayoutManager extends LinearLayoutManager {

    public CenterLayoutManager(Context context) {
        super(context);
    }

    public CenterLayoutManager(MainActivity context, int horizontal, boolean b) {
        super(context,horizontal,b);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        int totalHeight = getHeight();

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = getDecoratedMeasuredWidth(child);
            int height = getDecoratedMeasuredHeight(child);

            int left = (getWidth() - width) / 2;
            int top = (totalHeight - height) / 2;

            layoutDecorated(child, left, top, left + width, top + height);
        }
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return super.generateLayoutParams(lp);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }
}
