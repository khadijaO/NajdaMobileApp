package com.example.najdaapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    @Override
    public int getCount() {
        return total_tabs;
    }

    private Context context;
    int total_tabs;

    public LoginAdapter(FragmentManager fm, Context context,int total_tabs) {
        super(fm);
        this.context = context;
        this.total_tabs=total_tabs;
    }
    public Fragment getItem(int position){
        switch (position){
            case 0:
            LoginTabFragment loginTabFragment=new LoginTabFragment();
            return loginTabFragment;
            case 1:
                RegisterTabFragment registerTabFragment=new RegisterTabFragment();
                return registerTabFragment;


                default: return null;
        }
    }

}
