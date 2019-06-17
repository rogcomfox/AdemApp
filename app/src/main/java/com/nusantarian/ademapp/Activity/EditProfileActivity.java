package com.nusantarian.ademapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.nusantarian.ademapp.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private EditText et_name, et_username,et_email, et_bio;
    private CircleImageView img_profpic;

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
        img_profpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(EditProfileActivity.this, img_profpic);
                popupMenu.getMenuInflater().inflate(R.menu.changeprofpic_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.nav_choose:
                                Toast.makeText(EditProfileActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_take:
                                Toast.makeText(EditProfileActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_del:
                                Toast.makeText(EditProfileActivity.this, "Under Construction", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String username = et_username.getText().toString();
                String bio = et_bio.getText().toString();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
