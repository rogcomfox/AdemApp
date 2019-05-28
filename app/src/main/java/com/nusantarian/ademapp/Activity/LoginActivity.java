package com.nusantarian.ademapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nusantarian.ademapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        Button btn_login = findViewById(R.id.btn_login);
        TextView tv_forgot = findViewById(R.id.tv_forgot);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Silahkan Isi Data Akun Anda Terlebih Dahulu", Toast.LENGTH_LONG).show();
                }
                else if (!isEmailValid(email)){
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Bukan Alamat Email", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(password)){
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Silahkan Masukkan Password Anda", Toast.LENGTH_LONG).show();
                }
                else if (password.length() < 6){
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Password Kurang dari 6 Karakter", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email)){
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Silahkan Masukkan Alamat Email Anda", Toast.LENGTH_LONG).show();
                }else{
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Under Construction", Toast.LENGTH_LONG).show();
                }
            }
        });
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotActivity.class));
            }
        });
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
