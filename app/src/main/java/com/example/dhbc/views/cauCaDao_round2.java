package com.example.dhbc.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhbc.Adapter.Round2_cauhoiAdapter;
import com.example.dhbc.Adapter.Round2_dapanAdapter;
import com.example.dhbc.CSDL;
import com.example.dhbc.ClassDL.CaDao;
import com.example.dhbc.Interface.ItemClick_cauhoi;
import com.example.dhbc.Interface.ItemClick_dapan;
import com.example.dhbc.R;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class cauCaDao_round2 extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
    static boolean nhacXB ;
    static boolean nhacback;
    static float volumn1,volumn2;
    static SharedPreferences prefs;
    static CaDao caDao;
    static int id=0;
    String[] caDaoTucNgu = {
            "công", "cha", "như", "núi", "Thái", "Sơn", "nghĩa", "mẹ", "như", "nước",
            "trong", "nguồn", "chảy", "ra", "một", "cây", "làm", "chẳng", "nên", "non",
            "ba", "cây", "chụm", "lại", "nên", "hòn", "núi", "cao", "bầu", "ơi",
            "thương", "lấy", "bí", "cùng", "tuy", "rằng", "khác", "giống", "nhưng", "chung",
            "một", "giàn", "ăn", "quả", "nhớ", "kẻ", "trồng", "cây", "uống", "nước",
            "nhớ", "nguồn", "tốt", "gỗ", "hơn", "tốt", "nước", "sơn", "gần", "mực",
            "thì", "đen", "gần", "đèn", "thì", "sáng", "có", "công", "mài", "sắt",
            "có", "ngày", "nên", "kim", "chim", "khôn", "kêu", "tiếng", "rảnh", "ranh",
            "người", "khôn", "nói", "tiếng", "dịu", "dàng", "đói", "cho", "sạch", "rách",
            "cho", "thơm", "đêm", "đến", "gió", "ngày", "trưa", "sự", "học", "một",
            "khác", "chi", "thuyền", "trôi", "biển", "khơi", "nhớ", "mẹ", "ruột", "nghèo",
            "mà", "thắm", "người", "trên", "kính", "dưới", "nhường", "công", "danh", "phú",
            "quý", "làm", "chi", "dạ", "đã", "lạnh", "thì", "lòng", "chẳng", "ấm",
            "ông", "bà", "cha", "mẹ", "công", "cha", "nghĩa", "mẹ", "chẳng", "quên",
            "ai", "có", "đức", "mới", "thành", "công", "đời", "cây", "đắng", "kết",
            "quả", "ngọt", "trời", "cho", "chẳng", "bằng", "cha", "mẹ", "dạy", "giàu",
            "vì", "bạn", "sang", "vì", "vợ", "một", "nắm", "gạo", "cơm", "một", "đốt",
            "đèn", "cũng", "sáng", "nhà", "dưới", "mái", "chèo", "lên", "đời", "mẹ",
            "cha", "công", "ơn", "sinh", "dưỡng", "nhất", "tự", "vi", "sư", "bán",
            "tự", "vi", "sư", "bách", "chiến", "bách", "thắng", "không", "bằng", "một",
            "lần", "biết", "cách", "cư", "xử"
    };
    MediaPlayer mp, mp1;
    CSDL csdl;
    ImageView layout_2;
    RecyclerView dapan, cautraloi;
    Round2_dapanAdapter dapanAdapter;
    Round2_cauhoiAdapter cauhoiAdapter;
    String DapAn="";
    String[] list_tu,list_cautraloi;
    FlexboxLayoutManager layoutManager,layoutManager2;
    TextView tym,backr2;
    String[] list_DapAn;
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
        setContentView(R.layout.activity_cau_ca_dao_round2);
        tym=findViewById(R.id.countdown);
        backr2=findViewById(R.id.level);
        cautraloi=findViewById(R.id.listcauhoi);
        layout_2=findViewById(R.id.layout_2);
        dapan=findViewById(R.id.dapan);
        mp = new MediaPlayer();
        mp1 = new MediaPlayer();

        prefs= getSharedPreferences("game", MODE_PRIVATE);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnBack",1);
        volumn2=prefs.getFloat("volumnXB",1);
        loadCaDao();
        ktraAmthanh();
        tym.setText("Mảnh "+id);
        backr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cauCaDao_round2.this.finish();
            }
        });

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
    public void loadCaDao(){
        String fileName = caDao.getHinhAnh().toString();
        int resId = getResources().getIdentifier(fileName, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
        if (resId != 0) {
            layout_2.setImageResource(resId);
            layout_2.setBackgroundColor(Color.WHITE);// Thiết lập hình ảnh cho ImageView
        } else {
            // Xử lý trường hợp không tìm thấy tệp ảnh
        }
        DapAn=caDao.getDapAn();

        list_DapAn = DapAn.split(" ");
        list_cautraloi=new String[list_DapAn.length];
        Arrays.fill(list_cautraloi,"1");
        vitrioDapAn=new int[list_DapAn.length];
        Arrays.fill(vitrioDapAn, -1);
        // Sử dụng Set để đảm bảo không có từ nào bị trùng
        Set<String> randomWordsSet = new HashSet<>();
        Random random = new Random();

        // Lấy ngẫu nhiên 5 từ từ mảng caDaoTucNgu
        while (randomWordsSet.size() < 5) {
            int randomIndex = random.nextInt(caDaoTucNgu.length);
            randomWordsSet.add(caDaoTucNgu[randomIndex]);
        }
        // Chuyển Set thành mảng
        String[] randomWordsArray = randomWordsSet.toArray(new String[0]);
        // Tạo mảng mới với kích thước đủ lớn để chứa cả list_DapAn và randomWordsArray
        list_tu= new String[list_DapAn.length + randomWordsArray.length];
        // Sao chép các từ từ list_DapAn vào mảng mới
        System.arraycopy(list_DapAn, 0, list_tu, 0, list_DapAn.length);
        // Sao chép các từ ngẫu nhiên vào mảng mới
        System.arraycopy(randomWordsArray, 0, list_tu, list_DapAn.length, randomWordsArray.length);
        List<String> list = Arrays.asList(list_tu);

        // Xáo trộn danh sách
        Collections.shuffle(list);

        // Chuyển danh sách trở lại mảng
        list.toArray(list_tu);

        cauhoiAdapter = new Round2_cauhoiAdapter(this, list_cautraloi,this);
        dapanAdapter = new Round2_dapanAdapter(this, list_tu,this);
        layoutManager = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);

        layoutManager2 = new FlexboxLayoutManager(getApplicationContext());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);


        cautraloi.setLayoutManager(layoutManager);
        dapan.setLayoutManager(layoutManager2);
        cautraloi.setAdapter(cauhoiAdapter);
        dapan.setAdapter(dapanAdapter);

    }
    MediaPlayer mp3;
    @Override
    public void onItemCauHoiClick(int position) {
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
        String s = list_cautraloi[position].toUpperCase();
        if(s.trim().length()>0 &&s!=""&&s!=null && s!="1"){
            // set vị trí đó trở thành ""
            list_cautraloi[position]="1";

            // set lại vị trí cữ của từ đó vào arr2
            // vị trí cũ đã được lưu ở mảng vitriodapan
//            c[position]="";
            list_tu[vitrioDapAn[position]]=s;
            // vị trí đó ở mảng vitriodapan -> =1 (-1: chưa có ký tự; >-1: đã có ký tự)
            vitrioDapAn[position]=-1;

            //set lại adapter
            cautraloi.setAdapter(new Round2_cauhoiAdapter(this, list_cautraloi, this));
            dapan.setAdapter(new Round2_dapanAdapter(this, list_tu, this));
        }

    }

    int[] vitrioDapAn;
    @Override
    public void onItemClick(int position) {
        mp3 = new MediaPlayer();
        try {
            mp3.reset();
            mp3.setDataSource(getResources().openRawResourceFd(R.raw.chamnuoc));
            mp3.setVolume(volumn1, volumn1);
            mp3.prepare();
            mp3.start();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int dem = 0;
        // kiểm tra xem đã có bao nhiêu ký tự trong câu trả lời của người chơi
        for (int i = 0; i < list_cautraloi.length; i++) {
            if (list_cautraloi[i].toUpperCase() != "1" && !list_cautraloi[i].trim().isEmpty()) {
                dem++;
            }
        }

        String s = list_tu[position].toUpperCase();
        // nếu chọn ký tự trong listdapan !="" và biến dem < listcauhoi.size()
        if (s.trim().length() > 0 && s != "" && s != null && dem < vitrioDapAn.length) {

            //set vị trí đó trong arr2 => ""
            list_tu[position] = "";
            // biến dùng để chỉ set myAnswer 1 lần
            boolean foundNegativeIndex = false;
            //lập vòng for vitriodapan
            for (int j = 0; j < vitrioDapAn.length; j++) {

                // nếu foundNegativeIndex=false và có ký tự "" trong cautraloi
                if (!foundNegativeIndex && vitrioDapAn[j] < 0) {
                    // Nếu chưa tìm thấy phần tử âm và vitrioDapAn[j] nhỏ hơn 0, cập nhật arr và đánh dấu đã tìm thấy


                    if (vitrioDapAn[j] == -1) {
                        vitrioDapAn[j] = position;
                        foundNegativeIndex = true;
                        list_cautraloi[j] = s;


                    }


                }


            }
            KiemTraDapAn();


//            adap.notifyDataSetChanged();
//            adap.notifyDataSetChanged();
//            listcauhoi.setLayoutManager(layoutManager);
            cautraloi.setAdapter(new Round2_cauhoiAdapter(this, list_cautraloi, this));
            dapan.setAdapter(new Round2_dapanAdapter(this, list_tu, this));
        }
    }
    private void KiemTraDapAn(){
        int dem=0;
        // kiểm tra xem đã có bao nhiêu ký tự trong câu trả lời của người chơi
        for(int i=0;i<list_cautraloi.length;i++){
            if(list_cautraloi[i].toUpperCase()!="1" && !list_cautraloi[i].trim().isEmpty()){
                dem++;
            }
        }
        if(dem>=list_DapAn.length){
            boolean areEqual1 = arraysEqualIgnoreCase(list_DapAn, list_cautraloi);
            if(areEqual1){
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

                                GameShowRound2.traloidung[id-1]=true;
                                GameShowRound2.datraloi[id-1]=true;
                                cauCaDao_round2.this.finish();
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
                            Toast.makeText(cauCaDao_round2.this, "Đáp án hoàn toàn chính xác", Toast.LENGTH_SHORT).show();

                            GameShowRound2.traloidung[id-1]=true;
                            GameShowRound2.datraloi[id-1]=true;
                            cauCaDao_round2.this.finish();

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
                                cauCaDao_round2.this.finish();
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
                    cauCaDao_round2.this.finish();

                }
            }
        }
    }
    public static boolean arraysEqualIgnoreCase(String[] array1, String[] array2) {
        // Kiểm tra nếu hai mảng có chiều dài khác nhau
        if (array1.length != array2.length) {
            return false;
        }

        // So sánh từng phần tử không phân biệt chữ hoa và chữ thường
        for (int i = 0; i < array1.length; i++) {
            if (!array1[i].equalsIgnoreCase(array2[i])) {
                return false;
            }
        }

        return true;
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