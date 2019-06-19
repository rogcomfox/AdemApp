package com.nusantarian.ademapp.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.nusantarian.ademapp.R;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private EditText et_name, et_username,et_email, et_bio;
    private ImageView img_profpic;
    private String uid, name, username, email, bio;
    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        img_profpic = findViewById(R.id.img_profpic);

        et_name = findViewById(R.id.et_name);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_bio = findViewById(R.id.et_bio);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        uid = Objects.requireNonNull(mUser).getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        btn_save = findViewById(R.id.btn_save);

        if (mDatabase != null){
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        name = Objects.requireNonNull(dataSnapshot.child("fullname").getValue()).toString();
                        username = Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
                        email = mUser.getEmail();
                        bio = Objects.requireNonNull(dataSnapshot.child("bio").getValue()).toString();

                        et_name.setText(name);
                        et_username.setText(username);
                        et_email.setText(email);
                        et_bio.setText(bio);
                        btn_save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDatabase.child("fullname").setValue(et_name.getText());
                                mDatabase.child("username").setValue(et_username.getText());
                                mDatabase.child("bio").setValue(et_bio.getText());
                                mUser.updateEmail(et_email.getText().toString());
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
