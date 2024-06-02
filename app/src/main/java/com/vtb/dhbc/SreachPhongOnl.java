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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import com.vtb.dhbc.ClassDL.KeyGenerator;
import com.vtb.dhbc.ClassDL.Room;
import com.vtb.dhbc.Interface.RoomIdCallback;

import java.util.List;

public class SreachPhongOnl extends AppCompatActivity {
    private String userId;
    private String roomId;
    private Button btnFindMatch, btnCreateRoom,btnFindRoom;
    String idRoom="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sreach_phong_onl);
        csdl = new CSDL(this);
        //Ánh xạ các thành phần trong layout
        btnFindMatch = findViewById(R.id.btn_challenge_find_match);
        btnCreateRoom = findViewById(R.id.btn_challenge_create_room);
        btnFindRoom=findViewById(R.id.btn_challenge_find_room);

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
                    createRoom(userId);
                    //Chuyển đến giao diện phòng chơi của chế độ thách đấu

                }
            });
            btnFindRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialogFindRoom();
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

        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Nếu room tồn tại, thực hiện thêm player2 và cập nhật trạng thái
                    roomRef.child("player2Id").setValue(userId);
                    roomRef.child("status").setValue("ongoing");

                    //Chuyển đến Activity thách đấu
                    if (!isStartActivity) {
                        Intent intent = new Intent(SreachPhongOnl.this, batcap.class);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("userId", userId);
                        finish();
                        startActivity(intent);
                    }
                } else {
                    // Nếu roomId không tồn tại, hiển thị thông báo
                    Toast.makeText(SreachPhongOnl.this, "Phòng không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
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
                Room room = new Room(userId, null, getDSCauCaDao(), "", csdl.getCauHoiRound1(1).get(0).getId(), userId, "waiting","","waiting","waiting");
                roomsRef.child(roomId).setValue(room);
                listenToRoomUpdates(roomId);
//                idRoom=roomId;
//                showWaiting("Đang tìm đối thủ...");
                Intent intent = new Intent(SreachPhongOnl.this, batcap.class);
                intent.putExtra("roomId", roomId);
                intent.putExtra("userId", userId);
                startActivity(intent);
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

//                        Intent intent = new Intent(SreachPhongOnl.this, batcap.class);
//                        intent.putExtra("roomId", roomId);
//                        intent.putExtra("userId", userId);
//                        startActivity(intent);
                        finish();

                        // Remove the listener after the condition is met
                        roomRef.removeEventListener(this);
                    }
                } else {
//                    Toast.makeText(SreachPhongOnl.this, "Room is NULL", Toast.LENGTH_SHORT).show();
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
    private  void ShowDialogFindRoom(){
        Dialog waitingDialog = new Dialog(SreachPhongOnl.this, android.R.style.Theme_Dialog);
        waitingDialog.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        waitingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        waitingDialog.setContentView(R.layout.dialog_timphong);
        EditText tvMessage = waitingDialog.findViewById(R.id.tvname);
        waitingDialog.setCancelable(false);
        Button xn=waitingDialog.findViewById(R.id.btnname);
        TextView cham=waitingDialog.findViewById(R.id.cham);
        Animation blinkk= AnimationUtils.loadAnimation(this,R.anim.blink2);
        cham.setAnimation(blinkk);
        cham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waitingDialog.dismiss();
            }
        });
        xn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                joinRoom(tvMessage.getText().toString().trim(),userId);
            }
        });
        waitingDialog.show();

    }

}
