package com.example.covidspotter;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.MapFragment;

public class homeactivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);

        viewPager=findViewById(R.id.view_page);
        tabLayout=findViewById(R.id.tab_layout);

        fragadapter Fragadapter=new fragadapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(Fragadapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
