package com.example.familycourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessNewPassword extends AppCompatActivity {

    ImageView img_success;
    TextView txt_success_title , txt_success_desc;
    Animation move_left_anim , move_right_anim , zoom_out_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_new_password);
        findId();
        animation();
        next_activity();
    }
    private void findId(){
        img_success = findViewById(R.id.img_success);
        txt_success_title = findViewById(R.id.txt_success_title);
        txt_success_desc = findViewById(R.id.txt_success_desc);
    }
    private void animation(){
        move_left_anim = AnimationUtils.loadAnimation(this,R.anim.move_left_anim);
        txt_success_title.setAnimation(move_left_anim);

        move_right_anim = AnimationUtils.loadAnimation(this,R.anim.move_right_anim);
        txt_success_desc.setAnimation(move_right_anim);

        zoom_out_anim = AnimationUtils.loadAnimation(this,R.anim.zoom_out_anim);
        img_success.setAnimation(zoom_out_anim);

    }
    private void next_activity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SuccessNewPassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

}