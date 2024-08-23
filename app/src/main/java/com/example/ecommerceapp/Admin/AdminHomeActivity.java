package com.example.ecommerceapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ecommerceapp.Buyer.HomeActivity;
import com.example.ecommerceapp.Buyer.MainActivity;
import com.example.ecommerceapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class AdminHomeActivity extends AppCompatActivity {

    private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn, checkApproveProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        BottomNavigationView navigationView = findViewById(R.id.admin_bottom_nav);

        navigationView.setOnNavigationItemSelectedListener(admin_nav_listener);
        navigationView.setSelectedItemId(R.id.nav_maintain_products);

//        LogoutBtn = findViewById(R.id.admin_logout_btn);
//        CheckOrdersBtn = findViewById(R.id.check_orders_btn);
//        maintainProductsBtn = findViewById(R.id.maintain_btn);
//        checkApproveProductsBtn = findViewById(R.id.check_approve_products_btn);
//
//        maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
//                intent.putExtra("Admin", "Admin");
//                startActivity(intent);
//            }
//        });
//
//        LogoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//
//            }
//        });
//
//        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHomeActivity.this, AdminNewOrdersActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        checkApproveProductsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener admin_nav_listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_maintain_products:
                    Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                    intent.putExtra("Admin", "Admin");
                    startActivity(intent);
                    return true;
                case R.id.nav_order_admin:
                    Intent intent1 = new Intent(AdminHomeActivity.this, AdminNewOrdersActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.admin_new_products:
                    Intent intent2 = new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_logout_admin:
                    Intent intent4 = new Intent(AdminHomeActivity.this, MainActivity.class);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent4);
                    finish();
                    return true;
            }

            return false;
        }
    };
}