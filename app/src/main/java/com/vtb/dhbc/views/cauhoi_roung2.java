package com.vtb.dhbc.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vtb.dhbc.Adapter.CauHoiAdapter;
import com.vtb.dhbc.Adapter.DapAnAdapter;
import com.vtb.dhbc.CSDL;
import com.vtb.dhbc.ClassDL.CauHoi;
import com.vtb.dhbc.Interface.ItemClick_cauhoi;
import com.vtb.dhbc.Interface.ItemClick_dapan;
import com.vtb.dhbc.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class cauhoi_roung2 extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
    static CauHoi cauHoi;
    static  int id=0;
    List<Integer> vi_tri_dau_cach;
    List<Integer> trogiup;
    ArrayList<String> cautraloi;
    ArrayList<Integer> vitrioDapAn;
    ArrayList<String> arr2,arr;
    int index=0;
    int index2=0;
    int diem=0;
    CauHoiAdapter adapter;
    DapAnAdapter adap;

    String dapAn,dapAn2;
    FlexboxLayoutManager layoutManager,layoutManager2;
    TextView countdown;
    static int diemR1=0;
    CountDownTimer countDownTimer;
    long timeLeftInMillis = 300000;
    boolean isRunning = true;
    List<CauHoi> list;
    CSDL csdl;
    TextView level;
    ImageView layout_2;
    MediaPlayer mp, mp1;
    RecyclerView listcauhoi,dapan;
    SharedPreferences prefs;
    boolean nhacback;
    boolean nhacXB;
    float volumn1,volumn2;

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
        setContentView(R.layout.activity_cauhoi_roung2);
        countdown=findViewById(R.id.countdown);
        countdown.setText("Mảnh "+id);
        level=findViewById(R.id.level);
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cauhoi_roung2.this.finish();
            }
        });
        layout_2=findViewById(R.id.layout_2);
        mp = new MediaPlayer();
        mp1 = new MediaPlayer();

        listcauhoi = findViewById(R.id.listcauhoi);
        dapan = findViewById(R.id.dapan);
        prefs= getSharedPreferences("game", MODE_PRIVATE);
        nhacback = prefs.getBoolean("isMute", true);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnBack",1);
        volumn2=prefs.getFloat("volumnXB",1);
//        Toast.makeText(this, cauHoi.getDapAn(), Toast.LENGTH_SHORT).show();
        loadTrang();
        ktraAmthanh();
    }
    public void loadTrang(){

        
            vi_tri_dau_cach=new ArrayList<>();
            trogiup=new ArrayList<>();
            vitrioDapAn=new ArrayList<>();
//        slgRuby1= csdl.HienRuby(MainActivity.this);




            String fileName = cauHoi.getHinhAnh().toString(); // Lấy tên tệp ảnh từ đối tượng baiHat
            int resId = getResources().getIdentifier(fileName, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
            if (resId != 0) {
                layout_2.setImageResource(resId);
                layout_2.setBackgroundColor(Color.WHITE);// Thiết lập hình ảnh cho ImageView
            } else {
                // Xử lý trường hợp không tìm thấy tệp ảnh
            }
            dapAn = cauHoi.getDapAn();
            dapAn2 = cauHoi.getHinhAnh();
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

                // nếu ký tự cách
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



            adapter = new CauHoiAdapter(this, arr,this);
            adap = new DapAnAdapter(this, arr2,this);

            layoutManager = new FlexboxLayoutManager(getApplicationContext());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);

            layoutManager2 = new FlexboxLayoutManager(getApplicationContext());
            layoutManager.setFlexDirection(FlexDirection.ROW);
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);


            listcauhoi.setLayoutManager(layoutManager);
            dapan.setLayoutManager(layoutManager2);
            listcauhoi.setAdapter(adapter);
            dapan.setAdapter(adap);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(nhacXB){
                        int[] list_daylagi={R.raw.daylagi0,R.raw.daylagi1,R.raw.daylagi2,R.raw.daylagi3,R.raw.daylagi4};
                        Random random1 = new Random();
                        int randomIndex = random1.nextInt(list_daylagi.length);
                        int randomItem = list_daylagi[randomIndex];
//                        Toast.makeText(MainActivity.this, "bài: "+randomIndex, Toast.LENGTH_SHORT).show();
                        try {
                            mp1.reset();
                            mp1.setDataSource(getResources().openRawResourceFd(randomItem));
                            mp1.setVolume(volumn2,volumn2);
                            mp1.prepare();
                            mp1.start();
                            mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    mp1.reset();
                                    mp1.setVolume(volumn2,volumn2);
                                }
                            });




                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            },2000);
            index2 ++;
        }
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
    MediaPlayer mp3;
    @Override
    public void onItemCauHoiClick(int position) {
// lấy ra text của textview đã click
        mp3=new MediaPlayer();
        try {
            mp3.reset();
            mp3.setDataSource(getResources().openRawResourceFd(R.raw.chamnuoc));
            mp3.setVolume(volumn1,volumn1);
            mp3.prepare();
            mp3.start();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String s=cautraloi.get(position).toString().toUpperCase();
        // nếu s != ""
        if(s.trim().length()>0 &&s!=""&&s!=null && s!="1"){
            // set vị trí đó trở thành ""
            cautraloi.set(position,"");

            // set lại vị trí cữ của từ đó vào arr2
            // vị trí cũ đã được lưu ở mảng vitriodapan
            cautraloi.set(position,"1");
            arr2.set(vitrioDapAn.get(position),s);
            // vị trí đó ở mảng vitriodapan -> =1 (-1: chưa có ký tự; >-1: đã có ký tự)
            vitrioDapAn.set(position,-1);

            //set lại adapter
            listcauhoi.setAdapter( new CauHoiAdapter(this,cautraloi,this));
//            listcauhoi.setLayoutManager(layoutManager);
            dapan.setAdapter( new DapAnAdapter(this,arr2,this));
        }
    }

    @Override
    public void onItemClick(int position) {
        mp3=new MediaPlayer();
        try {
            mp3.reset();
            mp3.setDataSource(getResources().openRawResourceFd(R.raw.chamnuoc));
            mp3.setVolume(volumn1,volumn1);
            mp3.prepare();
            mp3.start();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int dem=0;
        // kiểm tra xem đã có bao nhiêu ký tự trong câu trả lời của người chơi
        for(int i=0;i<cautraloi.size();i++){
            if(cautraloi.get(i).toUpperCase()!="1" && !cautraloi.get(i).trim().isEmpty()){
                dem++;
            }
        }
//        Toast.makeText(this, "vị trí: "+dem+vi_tri_dau_cach.size(), Toast.LENGTH_SHORT).show();
        System.out.println(dem+vi_tri_dau_cach.size());
        String s=arr2.get(position).toString().toUpperCase();
        // nếu chọn ký tự trong listdapan !="" và biến dem < listcauhoi.size()
        if(s.trim().length()>0 &&s!=""&&s!=null && dem+vi_tri_dau_cach.size()<arr.size()) {

            //set vị trí đó trong arr2 => ""
            arr2.set(position, " ");
            // biến dùng để chỉ set myAnswer 1 lần
            boolean foundNegativeIndex = false;
            //lập vòng for vitriodapan
            for (int j = 0; j < vitrioDapAn.size(); j++) {

                // nếu foundNegativeIndex=false và có ký tự "" trong cautraloi
                if (!foundNegativeIndex && vitrioDapAn.get(j) < 0) {
                    // Nếu chưa tìm thấy phần tử âm và vitrioDapAn[j] nhỏ hơn 0, cập nhật arr và đánh dấu đã tìm thấy


                    if (vitrioDapAn.get(j) == -1) {
                        vitrioDapAn.set(j, position);
                        foundNegativeIndex = true;
                        cautraloi.set(j, s);
                        index+=1;


                    } else if (vitrioDapAn.get(j) == -2 && vitrioDapAn.get(j + 1) == -1) {
                        // th: nếu vị trí là dấu cách
                        cautraloi.set(j, " ");
                        cautraloi.set(j + 1, s);

                        vitrioDapAn.set(j + 1, position);
                        index += 1;
                        break;

                    }

                }


            }
            KiemTraDapAn();


            adap.notifyDataSetChanged();
//            adap.notifyDataSetChanged();
//            listcauhoi.setLayoutManager(layoutManager);
            listcauhoi.setAdapter(new CauHoiAdapter(this, cautraloi, this));
            dapan.setAdapter(new DapAnAdapter(this, arr2, this));
        }
    }
    public static String removeDiacritics(String str) {
        // chuyê unicode thành tiếng anh
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(str).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }
    private void KiemTraDapAn(){

        int dem=0;
        // kiểm tra xem đã có bao nhiêu ký tự trong câu trả lời của người chơi
        for(int i=0;i<cautraloi.size();i++){
            if(cautraloi.get(i).toUpperCase()!="1" && !cautraloi.get(i).trim().isEmpty()){
                dem++;
            }
        }

        // nếu vị trí textview cuối cùng đã có ký tự
        if(dem+vi_tri_dau_cach.size()>=arr.size()){

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
                if(nhacXB){
                    int[] list_daylagi={R.raw.chinhxac1,R.raw.chinhxac6,R.raw.chinhxac7,R.raw.chinhxac8,R.raw.chinhxac9};
                    Random random1 = new Random();
                    int randomIndex = random1.nextInt(list_daylagi.length);
                    int randomItem = list_daylagi[randomIndex];
                    try {
                        mp1.reset();
                        mp1.setVolume(volumn2, volumn2);
                        mp1.setDataSource(getResources().openRawResourceFd(randomItem));
                        mp1.prepare();
                        mp1.start();

                        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                diem++;
                                GameShowRound2.traloidung[id-1]=true;
                                GameShowRound2.datraloi[id-1]=true;
                                cauhoi_roung2.this.finish();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(cauhoi_roung2.this, "Đáp án hoàn toàn chính xác", Toast.LENGTH_SHORT).show();
                            diem++;
                            GameShowRound2.traloidung[id-1]=true;
                            GameShowRound2.datraloi[id-1]=true;
                            cauhoi_roung2.this.finish();

                        }
                    }, 1000);


                }

            }
            else {
                if(nhacXB){
                    int[] list_daylagi={R.raw.chuachinhxac0,R.raw.chuachinhxac1,R.raw.chuachinhxac2,R.raw.chuachinhxac3,R.raw.chuachinhxac4,R.raw.chuachinhxac5,R.raw.chuachinhxac6};
                    Random random1 = new Random();
                    int randomIndex = random1.nextInt(list_daylagi.length);
                    int randomItem = list_daylagi[randomIndex];
                    try {
                        mp1.setDataSource(getResources().openRawResourceFd(randomItem));
                        mp1.setVolume(volumn2, volumn2);
                        mp1.prepare();
                        mp1.start();

                        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                GameShowRound2.datraloi[id-1]=true;
                                 cauhoi_roung2.this.finish();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(this, "Đáp án chưa chính xac, tiếp tục", Toast.LENGTH_SHORT).show();
//                    loadTrang();
                    GameShowRound2.datraloi[id-1]=true;
                    cauhoi_roung2.this.finish();

                }

//                Toast.makeText(this, "lew lew gà", Toast.LENGTH_SHORT).show();
            }

        }
    }
    protected void onResume() {
        super.onResume();

        // Kiểm tra xem audio có được tạm dừng không và nếu có thì tiếp tục phát
        if (mp != null && !mp.isPlaying() && nhacback) {
            mp.start();
        }
        if (mp1 != null && !mp1.isPlaying() && nhacXB) {
            mp1.start();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Kiểm tra nếu audio đang phát
        if (mp != null && mp.isPlaying()&& nhacback) {
            // Tạm dừng audio
            mp.pause();
        }
        if (mp1 != null && mp1.isPlaying() && nhacXB) {
            // Tạm dừng audio
            mp1.pause();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Kiểm tra nếu audio đang phát
        if (mp != null && mp.isPlaying()&& nhacback) {
            // Tạm dừng audio
            mp.pause();
        }
        if (mp1 != null && mp1.isPlaying() && nhacXB) {
            // Tạm dừng audio
            mp1.pause();
        }
    }
}