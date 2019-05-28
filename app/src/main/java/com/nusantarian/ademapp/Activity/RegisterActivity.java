package com.nusantarian.ademapp.Activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.nusantarian.ademapp.R;

import java.util.Objects;

import static com.nusantarian.ademapp.Activity.LoginActivity.isEmailValid;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText et_fullname, et_username, et_email, et_phone, et_password, et_confpass;
    private DatabaseReference mDatabase;
    private ProgressDialog mDialog;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        et_fullname = findViewById(R.id.et_fullname);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_confpass = findViewById(R.id.et_confpass);
        Button btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                final String fullname = et_fullname.getText().toString();
                final String username = et_username.getText().toString();
                String email = et_email.getText().toString();
                final String phone = et_phone.getText().toString();
                String password = et_password.getText().toString();
                String confpass = et_confpass.getText().toString();

                if (TextUtils.isEmpty(fullname) && TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confpass)){
                    hideprogressdialog();
                    Toast.makeText(getApplicationContext(), "Silahkan Masukkan Data Anda Terlebih Dahulu", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(fullname)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Silahkan Masukkan Nama Anda", Toast.LENGTH_LONG).show();
                }
                else if (!isEmailValid(email)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Bukan Alamat Email", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(email)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Silahkan Masukkan Alamat Email Anda", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(phone)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Silahkan Masukkan Nomor Telepon Anda", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(username)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Silahkan Masukkan Username Anda", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(password)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Silahkan Masukkan Password Anda", Toast.LENGTH_LONG).show();
                }
                else if (password.length() < 6){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Password Kurang dari 6 Karakter", Toast.LENGTH_LONG).show();
                }
                else if (!password.equals(confpass)){
                    hideprogressdialog();
                    Toast.makeText(RegisterActivity.this, "Password Tidak Cocok", Toast.LENGTH_LONG).show();
                }else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            hideprogressdialog();
                            uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            mDatabase.child("fullname").setValue(fullname);
                            mDatabase.child("phone").setValue(phone);
                            mDatabase.child("username").setValue(username);
                            mDatabase.child("username").child(username).runTransaction(new Transaction.Handler() {
                                @NonNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                    if (mutableData.getValue() == null) {
                                        mutableData.setValue(mAuth.getUid());
                                        return Transaction.success(mutableData);
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Username Telah Ada", Toast.LENGTH_LONG).show();
                                    }
                                    return Transaction.abort();
                                }

                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

                                }
                            });
                        }
                    });
                }
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
            mDialog.setTitle("Membuat Akun Anda");
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
}
