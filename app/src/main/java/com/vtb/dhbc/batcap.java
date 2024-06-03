package com.vtb.dhbc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vtb.dhbc.ClassDL.Room;
import com.vtb.dhbc.ClassDL.ThongTinNguoiChoi;

public class batcap extends AppCompatActivity {
    String idRoom;
    String userID;
    TextView idPhong, namePlayer1, namePlayer2, ss1, ss2;
    ImageView avt1, avt2;
    CSDL csdl;
    Button sansang, back, kick;

    private void anhXa() {
        csdl = new CSDL(this);
        idPhong = findViewById(R.id.idroom);
        avt1 = findViewById(R.id.avt1);
        avt2 = findViewById(R.id.avt2);
        namePlayer1 = findViewById(R.id.nameplayer1);
        namePlayer2 = findViewById(R.id.nameplayer2);
        sansang = findViewById(R.id.start_sansang);
        ss1 = findViewById(R.id.ss1);
        ss2 = findViewById(R.id.ss2);
        back = findViewById(R.id.back);
        kick = findViewById(R.id.kick);
    }

    String player1, player2;
    String status1, status2;
    DatabaseReference roomRef;
    ValueEventListener roomListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batcap);
        anhXa();
        Intent intent = getIntent();
        idRoom = intent.getStringExtra("roomId");
        userID = intent.getStringExtra("userId");
        idPhong.setText("ID: " + idRoom);
        listenToRoomUpdates(idRoom);
        kick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePlayer2ExitRoom();
            }
        });
        sansang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player2 == null) {
                    Toast.makeText(batcap.this, "Chưa đủ điều kiện để bắt đầu", Toast.LENGTH_SHORT).show();
                } else {
                    SanSangPlay(userID);
                }
                if (status1.equalsIgnoreCase("start") && status2.equalsIgnoreCase("start")) {
                    DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(idRoom);

                    roomRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Room room = dataSnapshot.getValue(Room.class);
                                if (room != null) {
                                    roomRef.child("status").setValue("play")
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Xảy ra lỗi khi cập nhật trạng thái
                                                }
                                            });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userID.equalsIgnoreCase(player2)) {
                    handlePlayer2ExitRoom();
                } else {
                    handlePlayer1ExitRoom();
                }

            }
        });

    }

    private void SanSangPlay(String playerID) {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(idRoom);

        roomRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        Room room = dataSnapshot.getValue(Room.class);
                        if (room != null) {
                            String statusP1 = room.getStatusP1();
                            if (userID.equalsIgnoreCase(player1)) {
                                if (statusP1.equals("waiting")) {
                                    roomRef.child("statusP1").setValue("start")
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    ss1.setVisibility(View.VISIBLE);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Xảy ra lỗi khi cập nhật trạng thái
                                                }
                                            });
                                }
                            }

                            String statusP2 = room.getStatusP2();
                            if (userID.equalsIgnoreCase(player2)) {
                                if (statusP2.equals("waiting")) {
                                    roomRef.child("statusP2").setValue("start")
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    ss2.setVisibility(View.VISIBLE);
                                                    sansang.setVisibility(View.GONE);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Xảy ra lỗi khi cập nhật trạng thái
                                                }
                                            });
                                }
                            }

                        }
                    }
                }
            }
        });
    }

    public void listenToRoomUpdates(String roomId) {
        roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(roomId);

        roomListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room != null) {
                    player1 = room.getPlayer1Id();
                    player2 = room.getPlayer2Id();
                    status1 = room.getStatusP1();
                    status2 = room.getStatusP2();
                    if (room.status.equals("play")) {
                        Intent intent = new Intent(batcap.this, GameShowRound3.class);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("userId", userID);
                        startActivity(intent);
                        finish();
                        roomRef.removeEventListener(this);
                    }
                    updateUI(room);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if needed
            }
        };

        roomRef.addValueEventListener(roomListener);
    }

    private void updateUI(Room room) {
        if (room.getPlayer1Id().equalsIgnoreCase(userID)) {
            ThongTinNguoiChoi tt2 = csdl.HienThongTinNhanVat();
            namePlayer1.setText(tt2.getName());

            String fileAvt = "avt" + tt2.getAvt_id();
            int resId = getResources().getIdentifier(fileAvt, "drawable", getPackageName());
            String fileKhung = "khung" + tt2.getKhung_id();
            int resId2 = getResources().getIdentifier(fileKhung, "drawable", getPackageName());

            if (resId != 0) {
                avt1.setImageResource(resId);
            }
            if (resId2 != 0) {
                avt1.setBackgroundResource(resId2);
            }

            if (room.getPlayer2Id() != null) {
                getPlayer2(room.getPlayer2Id());
                kick.setVisibility(View.VISIBLE);
            } else {
                avt2.setVisibility(View.GONE);
                namePlayer2.setVisibility(View.GONE);
            }
            if (status2.equalsIgnoreCase("start")) {
                ss2.setVisibility(View.VISIBLE);
            }
        } else if (room.getPlayer2Id().equalsIgnoreCase(userID)) {
            ThongTinNguoiChoi tt2 = csdl.HienThongTinNhanVat();
            namePlayer2.setText(tt2.getName());
            String fileAvt = "avt" + tt2.getAvt_id();
            int resId = getResources().getIdentifier(fileAvt, "drawable", getPackageName());
            String fileKhung = "khung" + tt2.getKhung_id();
            int resId2 = getResources().getIdentifier(fileKhung, "drawable", getPackageName());

            if (resId != 0) {
                avt2.setImageResource(resId);
            }
            if (resId2 != 0) {
                avt2.setBackgroundResource(resId2);
            }

            if (player2 == null) {
                finish();
            } else {
                getPlayer1(room.getPlayer1Id());
                avt2.setVisibility(View.VISIBLE);
                namePlayer2.setVisibility(View.VISIBLE);
            }
            if (status1.equalsIgnoreCase("start")) {
                ss1.setVisibility(View.VISIBLE);
            }
        }

        if (status1.equalsIgnoreCase("start") && status2.equalsIgnoreCase("start")) {
            sansang.setBackgroundResource(R.drawable.button_choingay);
        } else {
            sansang.setBackgroundResource(R.drawable.btnss);
        }
    }
    public void getPlayer1(String userID) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(userID);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        ThongTinNguoiChoi thongTinNguoiChoi = dataSnapshot.getValue(ThongTinNguoiChoi.class);
                        if (thongTinNguoiChoi != null) {
                            avt1.setVisibility(View.VISIBLE);
                            namePlayer1.setVisibility(View.VISIBLE);
                            namePlayer1.setText(thongTinNguoiChoi.getName());

                            String fileAvt = "avt" + thongTinNguoiChoi.getAvt_id();
                            int resId = getResources().getIdentifier(fileAvt, "drawable", getPackageName());
                            String fileKhung = "khung" + thongTinNguoiChoi.getKhung_id();
                            int resId2 = getResources().getIdentifier(fileKhung, "drawable", getPackageName());

                            if (resId != 0) {
                                avt1.setImageResource(resId);
                            }
                            if (resId2 != 0) {
                                avt1.setBackgroundResource(resId2);
                            }
                        }
                        else {
                            avt1.setVisibility(View.GONE);
                            namePlayer1.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }
    public void getPlayer2(String userID) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(userID);

        userRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        ThongTinNguoiChoi thongTinNguoiChoi = dataSnapshot.getValue(ThongTinNguoiChoi.class);
                        if (thongTinNguoiChoi != null) {
                            avt2.setVisibility(View.VISIBLE);
                            namePlayer2.setVisibility(View.VISIBLE);
                            namePlayer2.setText(thongTinNguoiChoi.getName());
                            Toast.makeText(batcap.this, thongTinNguoiChoi.getName(), Toast.LENGTH_SHORT).show();

                            String fileAvt = "avt" + thongTinNguoiChoi.getAvt_id();
                            int resId = getResources().getIdentifier(fileAvt, "drawable", getPackageName());
                            String fileKhung = "khung" + thongTinNguoiChoi.getKhung_id();
                            int resId2 = getResources().getIdentifier(fileKhung, "drawable", getPackageName());

                            if (resId != 0) {
                                avt2.setImageResource(resId);
                            }
                            if (resId2 != 0) {
                                avt2.setBackgroundResource(resId2);
                            }
                        }
                        else {
                            avt2.setVisibility(View.GONE);
                            namePlayer2.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(roomRef != null) {
            roomRef.removeEventListener(roomListener);
        }
    }

    private boolean player2ExitHandled = false;

    private void handlePlayer2ExitRoom() {
        if (!player2ExitHandled) {
            DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(idRoom);

            roomRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        if (dataSnapshot.exists()) {
                            Room room = dataSnapshot.getValue(Room.class);
                            if (room != null && room.getPlayer2Id() != null) {
                                roomRef.child("player2Id").setValue(null);
                                roomRef.child("statusP2").setValue("waiting");
                                roomRef.child("statusP1").setValue("waiting");
                                roomRef.child("status").setValue("waiting");
                                player2 = "";
                                avt2.setVisibility(View.GONE);
                                namePlayer2.setVisibility(View.GONE);

                                if (userID.equalsIgnoreCase(player2)) {
                                    finish();
                                }

                                player2ExitHandled = true;
                            }
                        }
                    }
                }
            });
        }
    }

    private boolean player1ExitHandled = false;

    private void handlePlayer1ExitRoom() {
        if (!player1ExitHandled) {
            DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference().child("rooms").child(idRoom);

            roomRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        Room room = dataSnapshot.getValue(Room.class);
                        if (room != null && room.getPlayer2Id() != null) {
                            roomRef.child("player1Id").setValue(player2);
                            roomRef.child("statusP2").setValue("waiting");
                            roomRef.child("statusP1").setValue("waiting");
                            roomRef.child("status").setValue("waiting");
                            roomRef.child("player2Id").setValue(null);
                            player1ExitHandled = true;
                        } else if (room != null && room.getPlayer2Id() == null) {
                            roomRef.removeValue();
                        }
                        finish();
                    }
                }
            });
        }
    }
}
