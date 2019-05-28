package com.nusantarian.ademapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nusantarian.ademapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText et_email, et_password;
    private ProgressDialog mDialog;
    private static final String TAG = "";

    private final static int RC_SIGN_IN = 123;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if (currentuser != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        Button btn_login = findViewById(R.id.btn_login);
        TextView tv_forgot = findViewById(R.id.tv_forgot);
        SignInButton btn_google = findViewById(R.id.btn_google);

        mAuth = FirebaseAuth.getInstance();

        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(i, RC_SIGN_IN);
            }
        });

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
                    Toast.makeText(LoginActivity.this, "Password Kurang dari 6 Karakter", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(email)){
                    hideprogressdialog();
                    Toast.makeText(LoginActivity.this, "Silahkan Masukkan Alamat Email Anda", Toast.LENGTH_LONG).show();
                }else{
                   mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               Log.d(TAG, "Sign In Success");
                               hideprogressdialog();
                               startActivity(new Intent(getApplicationContext(), MainActivity.class));
                               finish();
                           }else {
                               Log.d(TAG, "Sign In Failed");
                               hideprogressdialog();
                               Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                           }
                       }
                   });
                }
            }
        });
        tv_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgotActivity.class));
            }
        });
        GoogleSignInOptions googlesignin = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googlesignin);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e){
                Log.w(TAG, "Google Sign In Failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    hideprogressdialog();
                    Log.d(TAG, "Sign In With Credentials Success");
                    FirebaseUser user = mAuth.getCurrentUser();
                }else {
                    hideprogressdialog();
                    Log.w(TAG, "Sign In With Credentials Failed");
                    Toast.makeText(LoginActivity.this, "Auth Fail", Toast.LENGTH_LONG).show();
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
