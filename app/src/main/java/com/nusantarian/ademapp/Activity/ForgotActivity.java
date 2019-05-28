package com.nusantarian.ademapp.Activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nusantarian.ademapp.R;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotActivity extends AppCompatActivity {

    private EditText et_email;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Lupa Kata Sandi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        et_email = findViewById(R.id.et_email);
        Button btn_forgot = findViewById(R.id.btn_forgot);

        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                String email = et_email.getText().toString();
                if (TextUtils.isEmpty(email)){
                    hideprogressdialog();
                    Toast.makeText(ForgotActivity.this, "Silahkan Isi Alamat Email Anda", Toast.LENGTH_LONG).show();
                }
                else if (!isEmailValid(email)){
                    hideprogressdialog();
                    Toast.makeText(ForgotActivity.this, "Masukkan Alamat Email dengan Benar", Toast.LENGTH_LONG).show();
                }else {
                    hideprogressdialog();
                    Toast.makeText(ForgotActivity.this, "Under Construction", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void showProgressDialog(){
        if (mDialog == null){
            mDialog = new ProgressDialog(this);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setIndeterminate(true);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setMessage("Please Wait....");
            mDialog.setTitle("Login to your account");
        }
        mDialog.show();
    }
    private void hideprogressdialog(){
        if (mDialog.isShowing()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1000);
        }
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
