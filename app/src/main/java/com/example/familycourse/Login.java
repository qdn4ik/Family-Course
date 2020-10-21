package com.example.familycourse;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText et_phone_login ,  et_password_login ;
    Button btn_login , btn_forget_login;
    TextView txt_login;
    Animation move_left_anim , slide_down_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findId();
        animation();
        forgetPassword();
        btn_login();

    }
    private void findId(){
        et_phone_login = findViewById(R.id.et_phone_login);
        et_password_login = findViewById(R.id.et_password_login);
        btn_login = findViewById(R.id.btn_login);
        txt_login = findViewById(R.id.txt_login);
        btn_forget_login = findViewById(R.id.btn_forget_login);
    }
    private void animation(){
        move_left_anim = AnimationUtils.loadAnimation(this,R.anim.move_left_anim);
        txt_login.setAnimation(move_left_anim);

        slide_down_anim = AnimationUtils.loadAnimation(this,R.anim.slide_down_anim);
        btn_login.setAnimation(slide_down_anim);
        btn_forget_login.setAnimation(slide_down_anim);
    }
    private void forgetPassword(){
        btn_forget_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
            }
        });
    }
    private void btn_login(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = et_phone_login.getText().toString();
                String password = et_password_login.getText().toString();

                if (phone.isEmpty()){
                    et_phone_login.setError("The field is empty ...");
                }
                else if (password.isEmpty()){
                    et_password_login.setError("The field is empty ...");
                }
                else {
                    Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(phone);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                et_phone_login.setError(null);

                                String systemPassword = snapshot.child(phone).child("password").getValue(String.class);

                                if (systemPassword.equals(password)){

                                    et_phone_login.setError(null);

                                    String name = snapshot.child(phone).child("name").getValue(String.class);
                                    Toast.makeText(Login.this, "Welcome to " + name, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else {
                                    et_password_login.setError("Wrong password ...");
                                    et_password_login.requestFocus();
                                }
                            }else {
                                et_phone_login.setError("No such user exist ...");
                                et_phone_login.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}