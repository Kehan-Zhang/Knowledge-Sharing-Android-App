package com.example.graduation.Activity;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.graduation.Fragment.FragmentMine;
import com.example.graduation.Fragment.FragmentRecommendation;
import com.example.graduation.Fragment.FragmentSearch;
import com.example.graduation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;

import cn.bmob.v3.Bmob;

public class SelectFunctionActivity extends AppCompatActivity {

    private FragmentSearch fragmentSearch;
    private FragmentRecommendation fragmentRecommendation;
    private FragmentMine fragmentMine;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    showNav(R.id.navigation_search);
                    return true;
                case R.id.navigation_recommendation:
                    showNav(R.id.navigation_recommendation);
                    return true;
                case R.id.navigation_mine:
                    showNav(R.id.navigation_mine);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_function);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");
        init();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void init(){
        fragmentSearch=new FragmentSearch();
        fragmentRecommendation=new FragmentRecommendation();
        fragmentMine=new FragmentMine();
        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.container,fragmentSearch).add(R.id.container,fragmentRecommendation).add(R.id.container,fragmentMine);
        beginTransaction.hide(fragmentSearch).hide(fragmentRecommendation).hide(fragmentMine);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        showNav(R.id.navigation_search);
    }

    private void showNav(int navId){
        FragmentTransaction beginTransaction=getFragmentManager().beginTransaction();
        switch (navId){
            case R.id.navigation_search:
                beginTransaction.hide(fragmentRecommendation).hide(fragmentMine);
                beginTransaction.show(fragmentSearch);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_recommendation:
                beginTransaction.hide(fragmentSearch).hide(fragmentMine);
                beginTransaction.show(fragmentRecommendation);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_mine:
                beginTransaction.hide(fragmentRecommendation).hide(fragmentSearch);
                beginTransaction.show(fragmentMine);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
        }
    }
}

