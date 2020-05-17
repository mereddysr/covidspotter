package com.example.covidspotter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class fragadapter extends FragmentPagerAdapter {



    Context context;

    public fragadapter(FragmentManager fn,Context context){
        super(fn);
        this.context=context;

    }

    @Override
    public Fragment getItem(int i) {
        if(i==0)
            return firstfrag.getINSTANCE();
        else if(i==1)
            return secondfrag.getINSTANCE();
        else if(i==2)
            return thirdfrag.getINSTANCE();
        else
            return  null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Home";
            case 1:
                return "Updates";
            case 2:
                return "Profile";

        }
        return "";
    }
}
