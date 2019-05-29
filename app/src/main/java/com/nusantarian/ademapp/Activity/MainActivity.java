package com.nusantarian.ademapp.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.nusantarian.ademapp.Fragment.AchievementFragment;
import com.nusantarian.ademapp.Fragment.ChatFragment;
import com.nusantarian.ademapp.Fragment.HomeFragment;
import com.nusantarian.ademapp.R;

public class MainActivity extends AppCompatActivity {

    private final Fragment fragment1 = new HomeFragment();
    private final Fragment fragment2 = new ChatFragment();
    private final Fragment fragment3 = new AchievementFragment();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.content_frame, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.content_frame, fragment1, "1").commit();

        BottomNavigationView bottom_nav = findViewById(R.id.bottom_nav);
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        fragmentManager.beginTransaction().hide(active).show(fragment1).commit();
                        break;
                    case R.id.nav_chat:
                        fragmentManager.beginTransaction().hide(active).show(fragment2).commit();
                        break;
                    case R.id.nav_achievement:
                        fragmentManager.beginTransaction().hide(active).show(fragment3).commit();
                        break;
                }
                return true;
            }
        });
    }
}
