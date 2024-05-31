package com.vtb.dhbc;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vtb.dhbc.Adapter.Round2_cauhoiAdapter;
import com.vtb.dhbc.Adapter.Round2_dapanAdapter;
import com.vtb.dhbc.ClassDL.CaDao;
import com.vtb.dhbc.ClassDL.Room;
import com.vtb.dhbc.Interface.ItemClick_cauhoi;
import com.vtb.dhbc.Interface.ItemClick_dapan;
import com.vtb.dhbc.views.GameShowRound1;
import com.vtb.dhbc.views.GameShowRound2;
import com.vtb.dhbc.views.cauCaDao_round2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class onlineManhghepSTT extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
    static boolean nhacXB ;
    static boolean nhacback;
    static float volumn1,volumn2;
    static SharedPreferences prefs;
    public static CaDao caDao;

    public static int id=0;
    private String userId;
    private String roomId;
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
    TextView  countdown, backr2;
    TextView manhstt;
    String[] list_DapAn;
    private Handler handler;
    private Runnable runnable;
    private boolean isRunning;
    private long timeLeftInMillis;
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
        setContentView(R.layout.activity_online_manhghep_stt);
        countdown=findViewById(R.id.countdown);
        backr2=findViewById(R.id.level);
        cautraloi=findViewById(R.id.listcauhoi);
        layout_2=findViewById(R.id.layout_2);
        manhstt=findViewById(R.id.manhstt);
        manhstt.setText("Mảnh "+id);
        dapan=findViewById(R.id.dapan);
        mp = new MediaPlayer();
        mp1 = new MediaPlayer();

        prefs= getSharedPreferences("game", MODE_PRIVATE);
        nhacXB = prefs.getBoolean("isXB", true);
        volumn1=prefs.getFloat("volumnBack",1);
        volumn2=prefs.getFloat("volumnXB",1);
        Intent intent = getIntent();
        if (intent != null) {
            userId = intent.getStringExtra("userId");
            roomId = intent.getStringExtra("roomId");
        }
        loadCaDao();
        ktraAmthanh();
//        startTimer1(GameShowRound3.timeLeftInMillis);
//        tym.setText("Mảnh "+id);
        timeLeftInMillis = GameShowRound3.timeLeftInMillis; // Giả định biến này tồn tại và được khởi tạo

        handler = new Handler();
        isRunning = true;

        runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    updateCountdown();

                    if (timeLeftInMillis <= 0) {
                        finish();
                        return;
                    }

                    // Đặt lại runnable này sau một khoảng thời gian nhất định
                    handler.postDelayed(this, 1000); // chạy lại sau 1 giây
                }
            }
        };

        // Bắt đầu thực thi runnable
        handler.post(runnable);

        backr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isFinish=true;
//                isFinish2=true;
                finish();
            }
        });
    }
    private void updateCountdown() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        countdown.setText(timeLeftFormatted);

        // Giảm thời gian còn lại mỗi giây
        timeLeftInMillis -= 1000;
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
                                GameShowRound3.isFinish=true;
                                GameShowRound3.isFinish2=true;
                                GameShowRound3.timeLeftInMillis=30000;
                                getRoomData(roomId);
                                finish();
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
                            Toast.makeText(onlineManhghepSTT.this, "Đáp án hoàn toàn chính xác", Toast.LENGTH_SHORT).show();
                            getRoomData(roomId);
                            GameShowRound3.timeLeftInMillis=30000;
                            GameShowRound3.isFinish=true;
                            GameShowRound3.isFinish2=true;
                            finish();

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
//                                finish();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(this, "Đáp án chưa chính xac, tiếp tục", Toast.LENGTH_SHORT).show();
//                    loadTrang();
//                    finish();

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
        isRunning = false;
        handler.removeCallbacks(runnable);
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



    public void getRoomData(String Id) {

        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(Id);

        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room != null) {
                    // Handle the retrieved room data here
                    Log.d("RoomData", "Room ID: " + dataSnapshot.getKey());
                    Log.d("RoomData", "User1 ID: " + room.getPlayer1Id());
                    Log.d("RoomData", "User2 ID: " + room.getPlayer2Id());
                    Log.d("RoomData", "danhSachManhGhepDaMo: " + room.getDanhSachManhGhepDaMo());
                    Log.d("RoomData", "danhSachCauCaDao: " + room.getDanhSachCauCaDao());
                    Log.d("RoomData", "questionTu: " + room.getQuestionTu());
                    Log.d("RoomData", "turn: " + room.getTurn());
                    Log.d("RoomData", "Status: " + room.getStatus());
                    if(GameShowRound3.isFinish2){
                        updateDanhSachManhGhepDaMoAndTurn(room, id,dataSnapshot.getKey());
                        GameShowRound3.isFinish2=false;
                    }




                } else {
                    Log.d("RoomData", "Room data is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("RoomData", "Failed to read room data", databaseError.toException());
            }

        });

    }
    private void updateDanhSachManhGhepDaMoAndTurn(Room room, int newItem, String roomKey) {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomKey);

        String currentDanhSachManhGhepDaMo = room.getDanhSachManhGhepDaMo();
        String currentTurn = room.getTurn();
        String newTurn;

        // Determine the new turn based on the current turn
        if (currentTurn.equalsIgnoreCase(room.getPlayer1Id())) {
            newTurn = room.getPlayer2Id();
        } else if (currentTurn.equalsIgnoreCase(room.getPlayer2Id())) {
            newTurn = room.getPlayer1Id();
        } else {
            Log.e("UpdateData", "Current turn does not match any player IDs");
            return;
        }

        // Append the new item to the existing list
        String updatedDanhSachManhGhepDaMo;
        if (currentDanhSachManhGhepDaMo != null) {
            updatedDanhSachManhGhepDaMo = currentDanhSachManhGhepDaMo + newItem + ",";
        } else {
            // If the current value is null, initialize with the new item
            updatedDanhSachManhGhepDaMo = newItem + ",";
        }

        // Check if there are actual changes before updating Firebase
        if (!currentDanhSachManhGhepDaMo.equals(updatedDanhSachManhGhepDaMo) || !currentTurn.equals(newTurn)) {
            // Update both danhSachManhGhepDaMo and turn fields in Firebase
            Map<String, Object> updates = new HashMap<>();
            updates.put("danhSachManhGhepDaMo", updatedDanhSachManhGhepDaMo);
            updates.put("turn", newTurn);

            roomRef.updateChildren(updates)
                    .addOnSuccessListener(aVoid -> Log.d("UpdateData", "Successfully updated danhSachManhGhepDaMo and turn"))
                    .addOnFailureListener(e -> Log.e("UpdateData", "Failed to update danhSachManhGhepDaMo and turn", e));
        } else {
            Log.d("UpdateData", "No changes detected, skipping Firebase update");
        }
    }
    private void startTimer1(long millisInFuture) {
        GameShowRound3.countDownTimer = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (GameShowRound3.isFinish) {
                    cancel();
                    finish();
                } else {
                    GameShowRound3.timeLeftInMillis = millisUntilFinished;
                    int minutes = (int) (GameShowRound3.timeLeftInMillis / 1000) / 60;
                    int seconds = (int) (GameShowRound3.timeLeftInMillis / 1000) % 60;
                    String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

                    countdown.setText(timeLeftFormatted);
                }
            }

            @Override
            public void onFinish() {
                onlineManhghepSTT.this.finish();
//                switchTurn();
            }
        }.start();
    }



    private void switchTurn() {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomId);
        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Room room = snapshot.getValue(Room.class);


                if (room != null) {
                    String nextTurn = "";
                    if(room.getPlayer1Id().equalsIgnoreCase(room.getTurn())){
                        nextTurn=room.getPlayer2Id();
                    }
                    if(room.getPlayer2Id().equalsIgnoreCase(room.getTurn())){
                        nextTurn=room.getPlayer1Id();
                    }
                    roomRef.child("turn").setValue(nextTurn);
                    GameShowRound3.isRunning = true;
                    GameShowRound3.isFinish = false;
                    GameShowRound3.isFinish2 = false;
                    GameShowRound3.endTurn = false;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GameShowRound3", "Failed to switch turn", error.toException());
            }
        });
    }
}
