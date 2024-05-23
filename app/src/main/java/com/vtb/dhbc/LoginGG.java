package com.vtb.dhbc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginGG extends AppCompatActivity {

    static GoogleSignInClient mgs;
    public static int AC_Sign_In=100;
    Button login,dangxauat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_gg);
         login=findViewById(R.id.login);
         dangxauat=findViewById(R.id.dangxuat);
        dangxauat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mgs.signOut();
                Toast.makeText(LoginGG.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                dangxauat.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
            }
        });
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgs= GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            login.setVisibility(View.GONE);
        }
        else {
            dangxauat.setVisibility(View.GONE);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }
    private void signIn(){
        Intent signInIntent= mgs.getSignInIntent();
        startActivityForResult(signInIntent,AC_Sign_In);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==AC_Sign_In){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    public void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {

            GoogleSignInAccount acc = task.getResult(ApiException.class);

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            if (account != null) {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personID = account.getId();
                Uri personPhoyo = account.getPhotoUrl();
                ImageView avt=findViewById(R.id.avt);
                avt.setImageURI(personPhoyo);
                System.out.println(account.getId());
                login.setVisibility(View.GONE);
                dangxauat.setVisibility(View.VISIBLE);
                Toast.makeText(this, personEmail, Toast.LENGTH_SHORT).show();

            }
        } catch (ApiException e) {
//            throw new RuntimeException(e);
            Log.d("Message", e.toString());
        }
    }
}