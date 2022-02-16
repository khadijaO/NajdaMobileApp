package com.example.najdaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class LoginActivity extends AppCompatActivity {
 TabLayout  tabLayout;
    ViewPager viewPager;
    Button login,register;
    TextView forget,haveAccount;

    float v=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Button Login=findViewById(R.id.login);
//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(getApplicationContext(),ProfilActivity.class);
//                startActivity(i);
//            }
//        });
        setContentView(R.layout.activity_login);
        tabLayout=findViewById(R.id.tab_layout);
        viewPager =findViewById(R.id.view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Register"));
login=findViewById(R.id.login);
forget=findViewById(R.id.forgot);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter loginAdapter=new LoginAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(loginAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        login.setTranslationY(300);
//        login.setAlpha(v);
//        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        forget.setTranslationY(300);
//        forget.setAlpha(v);
//        forget.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
    }
}
