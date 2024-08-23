package com.example.ecommerceapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Model.Users;
import com.example.ecommerceapp.Prevalent.Prevalent;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Sellers.SellerHomeActivity;
import com.example.ecommerceapp.Sellers.SellerRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowButton, loginButton;
    private ProgressDialog loadingBar;
    private TextView sellerBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Paper.init(this);

            joinNowButton = findViewById(R.id.join_now_btn);
            loginButton = findViewById(R.id.main_login_btn);
            sellerBegin = findViewById(R.id.seller_begin);
            loadingBar = new ProgressDialog(this);

            final String phoneKey = Paper.book().read(Prevalent.UserPhoneKey);
            final String passwordKey = Paper.book().read(Prevalent.UserPasswordKey);

            sellerBegin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent = new Intent(MainActivity.this, SellerRegistrationActivity.class);
                        startActivity(intent);
                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, e.getMessage() + "/n" + e.getCause(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);

                        if (phoneKey != "" && passwordKey != "") {
                            if (!TextUtils.isEmpty(phoneKey) && !TextUtils.isEmpty(passwordKey)) {
                                AllowAccess(phoneKey, passwordKey);

                                loadingBar.setTitle("Already Logged in");
                                loadingBar.setMessage("Please wait...");
                                loadingBar.setCanceledOnTouchOutside(false);
                                loadingBar.show();
                            }
                        }
                    }catch(Exception e){
                        Toast.makeText(MainActivity.this, e.getMessage() + "/n" + e.getCause(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            joinNowButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });

        }catch(Exception e){
            Toast.makeText(MainActivity.this, e.getMessage() + ".." + e.getCause(), Toast.LENGTH_LONG).show();
        }


    }

    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phone).exists()){
                    Users usersData = snapshot.child("Users").child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone)){
                        if(usersData.getPassword().equals(password)){
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this,
                                    "Logged in successfully...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);
                        }else{
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this,
                                    "Incorrect password.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Account with this " + phone
                            + " does not exist.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}