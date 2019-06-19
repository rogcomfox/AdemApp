package com.nusantarian.ademapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.nusantarian.ademapp.R;

import java.util.Objects;

public class HijratunNotifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijratun_notif);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Hijriatun Notif");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Switch sw_mode = findViewById(R.id.sw_mode);

        final TextView tv_kalender = findViewById(R.id.tv_kalendar);

        final CalendarView calendarView = findViewById(R.id.calendar);
        calendarView.getDate();

        sw_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sw_mode.setTextOn("Yes");
                    calendarView.setVisibility(View.VISIBLE);
                    tv_kalender.setVisibility(View.VISIBLE);
                }else {
                    sw_mode.setTextOff("No");
                    calendarView.setVisibility(View.INVISIBLE);
                    tv_kalender.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
