package com.nusantarian.ademapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nusantarian.ademapp.R;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nusantarian.ademapp.Activity.LoginActivity.isEmailValid;

public class ForgotActivity extends AppCompatActivity {

    private EditText et_email;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Lupa Kata Sandi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
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
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                hideprogressdialog();
                                alertdialog();
                            }else {
                                hideprogressdialog();
                                Toast.makeText(getApplicationContext(), "gagal Untuk Mereset Password", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
    private void alertdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Password Success");
        builder.setMessage("Silahkan Cek Email Anda");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
