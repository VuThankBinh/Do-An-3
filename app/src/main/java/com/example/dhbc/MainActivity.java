package com.example.dhbc;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import java.text.Normalizer;
import java.util.regex.Pattern;
public class MainActivity extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
    RecyclerView listcauhoi,dapan;
    ImageView help,shop,layout_2,back;
    TextView level,slgRuby;
    MediaPlayer mp, mp1;
    Button nextdemo;
    CSDL csdl;
    CauHoi ch;
    List<Integer> vi_tri_dau_cach;
    List<Integer> trogiup;
    ArrayList<String> cautraloi;
    ArrayList<Integer> vitrioDapAn;
    ArrayList<String> arr2,arr;
    int index=0;
    CauHoiAdapter adapter;
    DapAnAdapter adap;
    TableLayout tb;

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
        tb=findViewById(R.id.deme);
//        verifyStoragePemission(MainActivity.this);
        mp = new MediaPlayer();
        mp1 = new MediaPlayer();
        loadTrang();
        nextdemo=findViewById(R.id.demonext);
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        nextdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trogiupp="";
                for (int i: trogiup) {
                    trogiupp+= i+", ";
                }
                Toast.makeText(MainActivity.this, trogiupp, Toast.LENGTH_SHORT).show();

            }
        });

        if(layout_home2.nhacback==true){
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
        LinearLayout ngthan=dialog.findViewById(R.id.nhongthan);


        ngthan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Bitmap b=takescreenshotOfRootView(tb);
//                layout_2.setImageBitmap(b);

                File savedFile = saveBitmapToFile(b);
                if (savedFile != null) {
                    Toast.makeText(MainActivity.this, "Đã chụp màn hình để chia sẻ", Toast.LENGTH_SHORT).show();
                    try {
//                        File file =new File(getApplicationContext().getExternalCacheDir(),File.separator+"fix.png");
//                        FileOutputStream fileOutputStream=new FileOutputStream(file);
//                        b.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
//                        fileOutputStream.flush();
//                        fileOutputStream.close();
                        savedFile.setReadable(true,false);
                        final Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri= FileProvider.getUriForFile(getApplicationContext(),getApplication().getPackageName()+".provider",savedFile);
                        intent.putExtra(Intent.EXTRA_STREAM,uri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setType("image/*");

                        startActivity(Intent.createChooser(intent,"Share to..."));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Failed to save screenshot", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
    private void showDialogChucMung() {
        Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        dialog.setContentView(R.layout.dialog_chucmung_dapan);
        ImageView close=dialog.findViewById(R.id.closehelp);
        TextView tv=dialog.findViewById(R.id.dapan);
        tv.setText("Đáp án: "+ dapAn.toUpperCase());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                csdl.Update(MainActivity.this,ch.getId());
                csdl.UpdateRuby(MainActivity.this,3);
                loadTrang();
            }
        });
        dialog.show();

    }
    String dapAn,dapAn2;
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
        trogiup=new ArrayList<>();
        vitrioDapAn=new ArrayList<>();
        ch= csdl.HienCSDL(getApplicationContext());
        int slgRuby1= csdl.HienRuby(MainActivity.this);
        slgRuby.setText(String.valueOf(slgRuby1));
        if(ch.getId()==-1){

            tb.setVisibility(View.GONE);
            level.setText("Thanh Binh");
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
                    csdl.ChoiLai(MainActivity.this);
                    recreate();
                }
            });

            // Nút hủy
//            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    // Xử lý khi người dùng nhấn nút hủy
//                    dialog.dismiss(); // Đóng dialog
//                }
//            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            level.setText("Level "+String.valueOf(ch.getId()));

            String fileName = ch.getHinhAnh().toString(); // Lấy tên tệp ảnh từ đối tượng baiHat
            int resId = getResources().getIdentifier(fileName, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
            if (resId != 0) {
                layout_2.setImageResource(resId); // Thiết lập hình ảnh cho ImageView
            } else {
                // Xử lý trường hợp không tìm thấy tệp ảnh
            }
            dapAn = ch.getDapAn();
            dapAn2 = ch.getHinhAnh();
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
                    trogiup.add(2);
                    cautraloi.add("");
                    vitrioDapAn.add(-2);
                }
                else {
                    arr.add(String.valueOf("1"));
                    cautraloi.add("1");
                    vitrioDapAn.add(-1);
                    trogiup.add(0);
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
            mp.reset();
            try {
                mp1.setDataSource(getResources().openRawResourceFd(R.raw.daylagi0));
                mp1.prepare();
                mp1.start();

                mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        mp1.reset();
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }
    //click đáp án
    @Override
    public void onItemClick(int position) {

        int dem=0;
        for(int i=0;i<cautraloi.size();i++){
            if(cautraloi.get(i).toUpperCase()!="1" && !cautraloi.get(i).trim().isEmpty()){
                dem++;
            }
        }
//        Toast.makeText(this, "vị trí: "+dem+vi_tri_dau_cach.size(), Toast.LENGTH_SHORT).show();
        System.out.println(dem+vi_tri_dau_cach.size());
        String s=arr2.get(position).toString().toUpperCase();

        if(s.trim().length()>0 &&s!=""&&s!=null && dem+vi_tri_dau_cach.size()<arr.size()){
            arr2.set(position," ");
            boolean foundNegativeIndex = false;
            int vitri=-1;// Biến đánh dấu để chỉ thực hiện cập nhật arr một lần
            for (int j = 0; j < vitrioDapAn.size(); j++) {
                if (!foundNegativeIndex && vitrioDapAn.get(j) < 0) {
                    // Nếu chưa tìm thấy phần tử âm và vitrioDapAn[j] nhỏ hơn 0, cập nhật arr và đánh dấu đã tìm thấy
                    if (vitrioDapAn.get(j) == -1 ) {


                            vitrioDapAn.set(j, position);
                            foundNegativeIndex = true;
                            cautraloi.set(j, s);
                            index++;


                    } else if(vitrioDapAn.get(j) == -2 && vitrioDapAn.get(j+1)==-1) {

                        cautraloi.set(j, " ");
                        cautraloi.set(j + 1, s);

                        vitrioDapAn.set(j+1,position);
                        index+=1;
                        break;

                    }

                }


            }
            if(vitrioDapAn.get(vitrioDapAn.size()-1)>=0){
//                    Toast.makeText(this, "nam ngu", Toast.LENGTH_SHORT).show();
                String dapan1 = dapAn.toUpperCase();
                StringBuilder result = new StringBuilder();
                for (String item : cautraloi) {
                    result.append(item);
                }
                String dapan2 = result.toString();

                String dapan1KhongDau = removeDiacritics(dapan1);
                String dapan2KhongDau = removeDiacritics(dapan2);

                System.out.println(dapan1KhongDau);
                System.out.println(dapan2KhongDau);

                if(dapan1KhongDau.equals(dapan2KhongDau)){
                    try {
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.chinhxac1));
                        mp1.prepare();
                        mp1.start();

                        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {

                                showDialogChucMung();
                                mp.reset();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//                    Toast.makeText(this, "mày giỏi đúng r đó con chóa", Toast.LENGTH_SHORT).show();

                }
                else {
                    try {
                        mp1.setDataSource(getResources().openRawResourceFd(R.raw.chuachinhxac0));
                        mp1.prepare();
                        mp1.start();

                        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.reset();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(this, "lew lew gà", Toast.LENGTH_SHORT).show();
                }

            }



            adap.notifyDataSetChanged();
//            adap.notifyDataSetChanged();
//            listcauhoi.setLayoutManager(layoutManager);
            listcauhoi.setAdapter( new CauHoiAdapter(this,cautraloi,this));
            dapan.setAdapter( new DapAnAdapter(this,arr2,this));
        }
    }
    public static String removeDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(str).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }

    //click câu hỏi
    @Override
    public void onItemCauHoiClick(int position) {
//        Toast.makeText(this, "vị trí: "+vitrioDapAn.get(position), Toast.LENGTH_SHORT).show();
        String s=cautraloi.get(position).toString().toUpperCase();
            if(s.trim().length()>0 &&s!=""&&s!=null && s!="1"){
            cautraloi.set(position,"");

                    cautraloi.set(position,"1");
                    arr2.set(vitrioDapAn.get(position),s);

                    vitrioDapAn.set(position,-1);



//            vitrioDapAn.add(position);
            listcauhoi.setAdapter( new CauHoiAdapter(this,cautraloi,this));
//            listcauhoi.setLayoutManager(layoutManager);
            dapan.setAdapter( new DapAnAdapter(this,arr2,this));
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
    public static  Bitmap takescreenshot(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b=Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }
    private static Bitmap takescreenshotOfRootView(View v){
        return takescreenshot(v.getRootView());
    }
    public File saveBitmapToFile(Bitmap bitmap) {
        String savedImagePath = null;
        String imageFileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "/YourAppName");

        // Create directory if it doesn't exist
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        // Create the image file
        File imageFile = new File(storageDir, imageFileName);
        savedImagePath = imageFile.getAbsolutePath();

        try {
            // Save the image to storage
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Update MediaStore to display the image in the Gallery
            MediaStore.Images.Media.insertImage(getContentResolver(), imageFile.getAbsolutePath(), imageFile.getName(), imageFile.getName());
        } catch (IOException e) {
            e.printStackTrace();
            savedImagePath = null;
        }
        return imageFile;
    }


}