package com.vtb.dhbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vtb.dhbc.ClassDL.CaDao;
import com.vtb.dhbc.ClassDL.Room;

import java.util.List;
import java.util.Random;

public class SreachPhongOnl extends AppCompatActivity {
    private String userId;
    private String roomId;
    private Button btnFindMatch, btnCreateRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreach_phong_onl);
        csdl = new CSDL(this);
        //Ánh xạ các thành phần trong layout
        btnFindMatch = findViewById(R.id.btn_challenge_find_match);
        btnCreateRoom = findViewById(R.id.btn_challenge_create_room);

        //Lấy userId của người đang đăng nhập
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userId != null){
            btnFindMatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findOrCreateRoom(SreachPhongOnl.this, userId);
                }
            });
            //Bắt sự kiện khi bấm vào nút 'Tạo phòng'
            btnCreateRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Chuyển đến giao diện phòng chơi của chế độ thách đấu
//                Intent intent = new Intent(ChallengeActivity.this, ChallengeActivity.class);
//                startActivity(intent);
                }
            });
        }
        else {
            Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
        }
        //Bắt sự kiện khi bấm vào nút 'Tìm đối thủ'

    }
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void findOrCreateRoom(Context context, String userId) {
        if (!isNetworkAvailable(context)) {
            // Hiển thị thông báo lỗi cho người dùng
            Toast.makeText(context, "Không có kết nối mạng. Vui lòng kiểm tra kết nối và thử lại.", Toast.LENGTH_LONG).show();
            return;
        }

        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms");

        roomsRef.orderByChild("status").equalTo("waiting").limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Có phòng chờ, người chơi sẽ tham gia phòng này
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        roomId = snapshot.getKey();
                        joinRoom(roomId, userId);
                        break;
                    }
                } else {
                    // Không có phòng chờ, tạo phòng mới
                    createRoom(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }
    public void joinRoom(String roomId, String userId) {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomId);
        roomRef.child("player2Id").setValue(userId);
        roomRef.child("status").setValue("ongoing");
//        Toast.makeText(SreachPhongOnl.this, "Bắt đầu chơi", Toast.LENGTH_LONG).show();
        //Chuyển đến Activity thách đấu
        if(!isStartActivity){
            Intent intent = new Intent(SreachPhongOnl.this,GameShowRound3.class);
            intent.putExtra("roomId", roomId);
            intent.putExtra("userId", userId);
            startActivity(intent);
            finish();

        }
    }
    CSDL csdl;
    public String getDSCauCaDao(){
        String DScauCD="";
        List<CaDao> dscd=csdl.getCauHoiRound2(9);
        for (CaDao cd:dscd) {
            DScauCD +=String.valueOf(cd.getId())+",";
        }
        return  DScauCD;
    }


    public void createRoom(final String userId) {
        final DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference().child("rooms");
        generateUniqueRoomId(roomsRef, new RoomIdCallback() {
            @Override
            public void onRoomIdGenerated(String roomId) {
                Room room = new Room(userId, null, getDSCauCaDao(), "", csdl.getCauHoiRound1(1).get(0).getId(), userId, "waiting","");
                roomsRef.child(roomId).setValue(room);
                listenToRoomUpdates(roomId);
                showWaiting("Đang tìm đối thủ...");
            }
        });
    }

    private void generateUniqueRoomId(final DatabaseReference roomsRef, final RoomIdCallback callback) {
        final String roomId = KeyGenerator.generateKey();
        roomsRef.child(roomId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Key already exists, generate a new one
                    generateUniqueRoomId(roomsRef, callback);
                } else {
                    // Key is unique
                    callback.onRoomIdGenerated(roomId);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors
            }
        });
    }
    boolean isStartActivity=false;
//    public void listenToRoomUpdates(String roomId) {
//        if(!isStartActivity) {
//            DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomId);
//
//            roomRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Room room = dataSnapshot.getValue(Room.class);
//                    if (room != null) {
//// Nếu phòng đã chuyển sang trạng thái 'on going' thì chuyển đến Activity chơi game
//                        if (room.status.equals("ongoing")) {
//                            isStartActivity=true;
//
//                            Intent intent = new Intent(SreachPhongOnl.this, GameShowRound3.class);
//                            intent.putExtra("roomId", roomId);
//                            intent.putExtra("userId", userId);
//                            startActivity(intent);
//                            finish();
//
////                        Toast.makeText(SreachPhongOnl.this, "Bắt đầu chơi", Toast.LENGTH_LONG).show();
//
//
//                        }
//                    } else {
//                        Toast.makeText(SreachPhongOnl.this, "Room is NULL", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Xử lý lỗi nếu có
//                }
//            });
//        }
//    }
public void listenToRoomUpdates(String roomId) {
    if (isStartActivity) {
        return;
    } else {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomId);

        ValueEventListener roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room != null) {
                    // If the room status changes to 'ongoing', start the game activity
                    if (room.status.equals("ongoing")) {
                        isStartActivity = true;

                        Intent intent = new Intent(SreachPhongOnl.this, GameShowRound3.class);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();

                        // Remove the listener after the condition is met
                        roomRef.removeEventListener(this);
                    }
                } else {
                    Toast.makeText(SreachPhongOnl.this, "Room is NULL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        };

        roomRef.addValueEventListener(roomListener);
    }
}

    //Hàm hiển thị dialog chờ
    private void showWaiting(String message) {
        //Khởi tạo dialog chờ
        Dialog waitingDialog = new Dialog(SreachPhongOnl.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        waitingDialog.setContentView(R.layout.dialog_waiting);
        waitingDialog.setCancelable(false);
        //Ẩn thanh công cụ
        waitingDialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ImageView ivLoading = waitingDialog.findViewById(R.id.iv_waiting_gif);
        TextView tvMessage = waitingDialog.findViewById(R.id.tv_waiting_massage);

        //Hiển thị gif loading
        Glide.with(this)
                .load(R.drawable.loading)
                .into(ivLoading);
        //Hiển thị message
        tvMessage.setText(message);

        waitingDialog.show();
    }

}
class KeyGenerator {
    private static final String CHAR_POOL = "0123456789";
    private static final int KEY_LENGTH = 6;
    private static Random random = new Random();

    public static String generateKey() {
        StringBuilder key = new StringBuilder(KEY_LENGTH);
        for (int i = 0; i < KEY_LENGTH; i++) {
            key.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return key.toString();
    }
}
interface RoomIdCallback {
    void onRoomIdGenerated(String roomId);
}