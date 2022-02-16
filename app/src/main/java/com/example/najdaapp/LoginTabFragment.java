package com.example.najdaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class LoginTabFragment extends Fragment {

    Button login,register;
    TextView forget,haveAccount;
    float v=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      ViewGroup root=(ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);
        login=root.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getContext(),MainActivity.class);
                startActivity(i);
            }
        });
        forget=root.findViewById(R.id.forgot);
//        login.setTranslationY(300);
//        login.setAlpha(v);
//        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
//        forget.setTranslationY(300);
//        forget.setAlpha(v);
//        forget.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        return root;
    }
}
