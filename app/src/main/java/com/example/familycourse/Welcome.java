package com.example.familycourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Welcome extends AppCompatActivity {

    Animation move_left , move_right , slide_down;
    TextView welcome_title , welcome_desc;
    Button btn_login , btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findId();
        animation();
        login();
        register();
    }

    private void findId(){
        welcome_title = findViewById(R.id.welcome_title);
        welcome_desc = findViewById(R.id.welcome_desc);
        btn_login = findViewById(R.id.btn_login);
        btn_signUp = findViewById(R.id.btn_signUp);
    }
    private void animation(){
        move_left = AnimationUtils.loadAnimation(this,R.anim.move_left_anim);
        welcome_title.setAnimation(move_left);

        slide_down = AnimationUtils.loadAnimation(this,R.anim.slide_down_anim);
        btn_signUp.setAnimation(slide_down);
        btn_login.setAnimation(slide_down);

        move_right = AnimationUtils.loadAnimation(this,R.anim.move_right_anim);
        welcome_desc.setAnimation(move_right);
    }
    private void login(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,Login.class);
                startActivity(intent);
            }
        });
    }
    private void register(){
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this,Register.class);
                startActivity(intent);
            }
        });
    }


}