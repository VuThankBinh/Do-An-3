package com.example.dhbc.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhbc.Adapter.CauHoiAdapter;
import com.example.dhbc.Adapter.DapAnAdapter;
import com.example.dhbc.CSDL;
import com.example.dhbc.ClassDL.CauHoi;
import com.example.dhbc.Interface.ItemClick_cauhoi;
import com.example.dhbc.Interface.ItemClick_dapan;
import com.example.dhbc.R;
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

public class GameShowRound1 extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
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
    ImageView nextcau,backr1;
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
        setContentView(R.layout.activity_game_show_round1);
        countdown=findViewById(R.id.countdown);
        level=findViewById(R.id.level);
        layout_2=findViewById(R.id.layout_2);
        nextcau=findViewById(R.id.next);
        backr1=findViewById(R.id.backr1);
        backr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBack();
            }
        });
        nextcau.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialogNext();
            }
        });
        csdl=new CSDL(GameShowRound1.this);
        list=csdl.getCauHoiRound1(10);
        mp = new MediaPlayer();
        mp1 = new MediaPlayer();


        prefs= getSharedPreferences("game", MODE_PRIVATE);
        nhacback = prefs.getBoolean("isMute", true);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnBack",1);
        volumn2=prefs.getFloat("volumnXB",1);
        startTimer(timeLeftInMillis);
        loadTrang();
    }

    private void showDialogNext() {
        Dialog dialog = new Dialog(GameShowRound1.this, android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_nextcau);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        Button chapnhan=dialog.findViewById(R.id.chapnhan);
        Button tuchoi=dialog.findViewById(R.id.tuchoi);
        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTrang();
                dialog.dismiss();

            }
        });
        tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialogBack() {
        Dialog dialog = new Dialog(GameShowRound1.this, android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.dialog_dunggame);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        Button chapnhan=dialog.findViewById(R.id.chapnhan);
        Button tuchoi=dialog.findViewById(R.id.tuchoi);
        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                GameShowRound1.this.finish();
            }
        });
        tuchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

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
    public void loadTrang(){

        if(index2>9){
            showDialogEndRound1();
        }
        else {
            vi_tri_dau_cach=new ArrayList<>();
            trogiup=new ArrayList<>();
            vitrioDapAn=new ArrayList<>();
//        slgRuby1= csdl.HienRuby(MainActivity.this);


            level.setText(String.valueOf(diem)+"/10");

            String fileName = list.get(index2).getHinhAnh().toString(); // Lấy tên tệp ảnh từ đối tượng baiHat
            int resId = getResources().getIdentifier(fileName, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
            if (resId != 0) {
                layout_2.setImageResource(resId);
                layout_2.setBackgroundColor(Color.WHITE);// Thiết lập hình ảnh cho ImageView
            } else {
                // Xử lý trường hợp không tìm thấy tệp ảnh
            }
            dapAn = list.get(index2).getDapAn();
            dapAn2 = list.get(index2).getHinhAnh();
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

            level.setText(String.valueOf(diem)+"/10");
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

    }

    private void showDialogEndRound1() {
        Dialog dialog = new Dialog(GameShowRound1.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_game_end_round1);
        dialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        ImageView ketthuc=dialog.findViewById(R.id.lydoend);
        ImageView choi=dialog.findViewById(R.id.choi);
        TextView diemtv=dialog.findViewById(R.id.diem);
        Button btnHuy=dialog.findViewById(R.id.choilai);
        Button btnchoitiep=dialog.findViewById(R.id.choitiep);
        if(isRunning){
            ketthuc.setImageResource(R.drawable.ketthuc);
            diemtv.setText("Điểm số: "+ diem +"/10");
            if(diem>=5){
                choi.setImageResource(R.drawable.bn_tiep);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        GameShowRound1.this.finish();
                        csdl.UpdateRuby(GameShowRound1.this,5);
                    }
                });
                btnchoitiep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        diemR1=diem;
                        startActivity(new Intent(GameShowRound1.this,GameShowRound2.class));
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        GameShowRound1.this.finish();
                    }
                });
            }
            else {
                choi.setImageResource(R.drawable.bn_choilai);
                btnHuy.setBackgroundResource(R.drawable.btntuchoi);
                btnchoitiep.setBackgroundResource(R.drawable.btnchoilai);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        GameShowRound1.this.finish();
                    }
                });
                btnchoitiep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                        dialog.dismiss();
                        csdl.UpdateRuby(GameShowRound1.this,5);
                    }
                });
            }
        }
        else {
            ketthuc.setImageResource(R.drawable.hetgio);
            diemtv.setText("Điểm của bạn: "+ diem +"/10");
            if(diem>=5){
                choi.setImageResource(R.drawable.bn_tiep);
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        GameShowRound1.this.finish();
                        csdl.UpdateRuby(GameShowRound1.this,5);
                    }
                });
                btnchoitiep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        diemR1=diem;
                        startActivity(new Intent(GameShowRound1.this,GameShowRound2.class));
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                        GameShowRound1.this.finish();
                    }
                });
            }
            else {
                choi.setImageResource(R.drawable.bn_choilai);
                btnHuy.setBackgroundResource(R.drawable.btntuchoi);
                btnchoitiep.setBackgroundResource(R.drawable.btnchoilai);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        GameShowRound1.this.finish();
                    }
                });
                btnchoitiep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                        dialog.dismiss();
                        csdl.UpdateRuby(GameShowRound1.this,5);
                    }
                });
            }
        }
        dialog.show();

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

//                                showDialogChucMung();
//                                mp1.reset();
//                                mp1.setVolume(volumn2, volumn2);
                                diem++;
                                loadTrang();
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
                            Toast.makeText(GameShowRound1.this, "Đáp án hoàn toàn chính xác", Toast.LENGTH_SHORT).show();
                            diem++;
                            loadTrang();
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
//                                mp1.reset();
//                                mp1.setVolume(volumn2, volumn2);
                                loadTrang();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(this, "Đáp án chưa chính xac, tiếp tục", Toast.LENGTH_SHORT).show();
                    loadTrang();
                }

//                Toast.makeText(this, "lew lew gà", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Kiểm tra xem audio có được tạm dừng không và nếu có thì tiếp tục phát
        if (mp != null && !mp.isPlaying() && nhacback) {
            mp.start();
        }
        if (mp1 != null && !mp1.isPlaying() && nhacXB) {
            mp1.start();

        }
        if (isRunning) {
            startTimer(timeLeftInMillis);
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
        if (countDownTimer != null) {
            countDownTimer.cancel();
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
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    private void startTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                countdown.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                countdown.setText("00:00");
                isRunning = false;
                Toast.makeText(GameShowRound1.this, "Trò chơi kết thúc", Toast.LENGTH_SHORT).show();
                showDialogEndRound1();
            }
        }.start();
//        isRunning = true;

    }
}