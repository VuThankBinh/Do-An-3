package com.vtb.dhbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.vtb.dhbc.Adapter.CauHoiAdapter;
import com.vtb.dhbc.Adapter.DapAnAdapter;
import com.vtb.dhbc.Adapter.Round2_cauhoiAdapter;
import com.vtb.dhbc.Adapter.Round2_dapanAdapter;
import com.vtb.dhbc.ClassDL.CaDao;
import com.vtb.dhbc.ClassDL.CauHoi;
import com.vtb.dhbc.ClassDL.Room;
import com.vtb.dhbc.Interface.ItemClick_cauhoi;
import com.vtb.dhbc.Interface.ItemClick_dapan;
import com.vtb.dhbc.views.GameShowRound1;
import com.vtb.dhbc.views.GameShowRound2;
import com.vtb.dhbc.views.cauCaDao_round2;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class GameShowRound3 extends AppCompatActivity implements ItemClick_dapan, ItemClick_cauhoi {
    CSDL csdl;
    TextView tym,backr2;
    ImageView layout_2;
    AppCompatButton h1,h2,h3,h4,h5,h6,h7,h8,h9;
    RecyclerView listcauhoi,dapan;
    MediaPlayer mp, mp1;
    static boolean nhacXB ;
    static boolean nhacback;
    static float volumn1,volumn2;
    static SharedPreferences prefs;
    Round2_dapanAdapter dapanAdapter;
    Round2_cauhoiAdapter cauhoiAdapter;
    private String userId;
    private String roomId;
    CauHoi cauHoi;
    List<CaDao> list_cadao;
    TextView turn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_show_round3);
        // Nhận Intent đã được gửi đến Activity này
        Intent intent = getIntent();
        cauHoi=new CauHoi();
        csdl=new CSDL(this);
        layout_2=findViewById(R.id.layout_2);
        listcauhoi=findViewById(R.id.listcauhoi);
        dapan=findViewById(R.id.dapan);
        tym=findViewById(R.id.tym);
        backr2=findViewById(R.id.backr2);
        h1=findViewById(R.id.smallImage1);
        h2=findViewById(R.id.smallImage2);
        h3=findViewById(R.id.smallImage3);
        h4=findViewById(R.id.smallImage4);
        h5=findViewById(R.id.smallImage5);
        h6=findViewById(R.id.smallImage6);
        h7=findViewById(R.id.smallImage7);
        h8=findViewById(R.id.smallImage8);
        h9=findViewById(R.id.smallImage9);
        turn=findViewById(R.id.turn);
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(0),1);
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(1),2);
            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(2),3);
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(3),4);
            }
        });
        h5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(4),5);
            }
        });
        h6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(5),6);
            }
        });
        h7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(6),7);
            }
        });
        h8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(7),8);
            }
        });
        h9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCauCaDao(list_cadao.get(8),9);
            }
        });



        // Lấy giá trị từ extra
        if (intent != null) {
            userId = intent.getStringExtra("userId");
            roomId = intent.getStringExtra("roomId");
        }
        getRoomData(roomId);
//

    }
    String turnn="";
    private void showDialogCauCaDao(CaDao cd, int id) {
        onlineManhghepSTT.caDao=cd;
        onlineManhghepSTT.id=id;
        Intent intent = new Intent(GameShowRound3.this,onlineManhghepSTT.class);
        intent.putExtra("roomId", roomId);
        intent.putExtra("userId", userId);
        startActivity(intent);

    }
    boolean isload=false;
    public void getRoomData(String Id) {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(Id);

        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room != null) {
                    isRunning = true;
                    isFinish = false;
                    isFinish2 = false;
                    endTurn = false;
                    // Handle the retrieved room data here
                    Log.d("RoomData", "Room ID: " + dataSnapshot.getKey());
                    Log.d("RoomData", "User1 ID: " + room.getPlayer1Id());
                    Log.d("RoomData", "User2 ID: " + room.getPlayer2Id());
                    Log.d("RoomData", "danhSachManhGhepDaMo: " + room.getDanhSachManhGhepDaMo());
                    Log.d("RoomData", "danhSachCauCaDao: " + room.getDanhSachCauCaDao());
                    Log.d("RoomData", "questionTu: " + room.getQuestionTu());
                    Log.d("RoomData", "turn: " + room.getTurn());
                    Log.d("RoomData", "Status: " + room.getStatus());
                    cauHoi=csdl.getCauHoi(room.getQuestionTu());
                    list_cadao=new ArrayList<>();
                    if (!room.getDanhSachManhGhepDaMo().trim().equals("")) {
                        String[] idManhGhe =  room.getDanhSachManhGhepDaMo().split(",");
                        AnManhGhep(idManhGhe);
                    }
                    String[] idStrings =  room.getDanhSachCauCaDao().split(",");
                    for (String idStr : idStrings) {
                        try {
                            // Convert each ID string to an integer
                            int id = Integer.parseInt(idStr.trim());

                            // Retrieve the question by ID
                            CaDao cauHoi = csdl.getCaDao(id);

                            // Add the question to the list
                            if (cauHoi != null) {
                                list_cadao.add(cauHoi);
                                Log.d("RoomData", "Ca dao: " + cauHoi.getId());
                            }
                        } catch (NumberFormatException e) {
                            Log.e("RoomData", "Invalid ID format: " + idStr, e);
                        }
                    }
                    turnn=room.getTurn().trim();
                    if(room.getTurn().trim().equalsIgnoreCase(userId.trim())){
                        turn.setText("Lượt của bạn còn: ");
                        timeLeftInMillis=30000;
                        startTimer1(timeLeftInMillis);
                        VisibilityItem();
                    }
                    else {
                        turn.setText("Lượt đối phương còn: ");
                        GoneItem();
                        timeLeftInMillis=30000;
                        startTimer2(timeLeftInMillis);
                    }
                    if(!isload){
                        isload=true;
                        loadcauhoi();
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


    private void AnManhGhep(String[] idManhGhe) {
        List<AppCompatButton> buttonList = new ArrayList<>();
        buttonList.add(h1);
        buttonList.add(h2);
        buttonList.add(h3);
        buttonList.add(h4);
        buttonList.add(h5);
        buttonList.add(h6);
        buttonList.add(h7);
        buttonList.add(h8);
        buttonList.add(h9);
        for(int i=0;i<idManhGhe.length;i++){
//            if(idManhGhe[i]==){
                buttonList.get(Integer.parseInt(idManhGhe[i].trim())-1).setBackgroundResource(R.drawable.transparent_color);
                buttonList.get(Integer.parseInt(idManhGhe[i].trim())-1).setText("");
                buttonList.get(Integer.parseInt(idManhGhe[i].trim())-1).setEnabled(false);
//            }
        }
    }
    private void VisibilityItem(){
        List<AppCompatButton> buttonList = new ArrayList<>();
        buttonList.add(h1);
        buttonList.add(h2);
        buttonList.add(h3);
        buttonList.add(h4);
        buttonList.add(h5);
        buttonList.add(h6);
        buttonList.add(h7);
        buttonList.add(h8);
        buttonList.add(h9);
        for(int i=0;i<buttonList.size();i++){
            buttonList.get(i).setEnabled(true);
//            }
        }
        listcauhoi.setVisibility(View.VISIBLE);
        dapan.setVisibility(View.VISIBLE);
    }
    private void GoneItem(){
        List<AppCompatButton> buttonList = new ArrayList<>();
        buttonList.add(h1);
        buttonList.add(h2);
        buttonList.add(h3);
        buttonList.add(h4);
        buttonList.add(h5);
        buttonList.add(h6);
        buttonList.add(h7);
        buttonList.add(h8);
        buttonList.add(h9);
        for(int i=0;i<buttonList.size();i++){
            buttonList.get(i).setEnabled(false);
//            }
        }
        listcauhoi.setVisibility(View.GONE);
        dapan.setVisibility(View.GONE);
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
    public void loadcauhoi(){

            vi_tri_dau_cach=new ArrayList<>();
            trogiup=new ArrayList<>();
            vitrioDapAn=new ArrayList<>();

            String fileName = cauHoi.getHinhAnh().toString(); // Lấy tên tệp ảnh từ đối tượng baiHat
            int resId = getResources().getIdentifier(fileName, "drawable", getPackageName()); // Tìm ID tài nguyên dựa trên tên tệp ảnh
            if (resId != 0) {
                layout_2.setImageResource(resId);
                layout_2.setBackgroundColor(Color.BLACK);// Thiết lập hình ảnh cho ImageView
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
                            Toast.makeText(GameShowRound3.this, "Đáp án hoàn toàn chính xác", Toast.LENGTH_SHORT).show();

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
//                                loadTrang();
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    Toast.makeText(this, "Đáp án chưa chính xac, tiếp tục", Toast.LENGTH_SHORT).show();
//                    loadTrang();
                }

//                Toast.makeText(this, "lew lew gà", Toast.LENGTH_SHORT).show();
            }

        }
    }
   static long timeLeftInMillis = 30000;
   static boolean isRunning = true;
   static boolean isFinish = false,isFinish2=false,endTurn=false;
   static CountDownTimer countDownTimer;
   private void startTimer1(long millisInFuture) {
       if(countDownTimer != null) {
           countDownTimer.cancel();
           timeLeftInMillis = 30000;
       }
        String text=turn.getText().toString();
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinish) {
                    cancel();
                } else {
                    GameShowRound3.timeLeftInMillis = millisUntilFinished;
                    int minutes = (int) (millisUntilFinished / 1000) / 60;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

                    turn.setText(text+ timeLeftFormatted);
                    if (millisUntilFinished == 0) {
                        isFinish = true;
                    }
                }

            }

            @Override
            public void onFinish() {
                if (!isFinish) {
                    endTurn = true;
                    isRunning = false;
//                    timeLeftInMillis=30000;
                    switchTurn();
//                    showDialogEndRound1();
                }
            }
        }.start();
    }
    private void startTimer2(long millisInFuture) {
        if(countDownTimer != null) {
            countDownTimer.cancel();
            timeLeftInMillis = 30000;
        }
        String text=turn.getText().toString();
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (isFinish) {
                    cancel();
                } else {
                    GameShowRound3.timeLeftInMillis = millisUntilFinished;
                    int minutes = (int) (millisUntilFinished / 1000) / 60;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

                    turn.setText(text+ timeLeftFormatted);
                    if (millisUntilFinished == 0) {
                        isFinish = true;
                    }
                }

            }

            @Override
            public void onFinish() {
                if (!isFinish) {
                    endTurn = true;
                    isRunning = false;
                    timeLeftInMillis=30000;
//                    Toast.makeText(onlineManhghepSTT.this, "Trò chơi kết thúc", Toast.LENGTH_SHORT).show();
//                    finish();
//                    switchTurn();
//                    showDialogEndRound1();
                }
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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GameShowRound3", "Failed to switch turn", error.toException());
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(timeLeftInMillis !=0){
            if(turnn.equalsIgnoreCase(userId)){
                turn.setText("Lượt của bạn còn: ");
                startTimer1(timeLeftInMillis);
            }
            else {
                turn.setText("Lượt của đối phương còn: ");
                startTimer2(timeLeftInMillis);
            }
        }

    }

}


