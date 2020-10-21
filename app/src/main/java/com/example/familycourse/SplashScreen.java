package com.example.familycourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {

    ImageView img_splash;
    TextView txt_splash;
    Animation top_anim , bottom_anim;
    private static int SPLASH_TIME = 3000;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        findId();
        nextActivity();

    }

    private void findId(){
        img_splash = findViewById(R.id.img_logo);
        txt_splash = findViewById(R.id.txt_splash);

        top_anim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        img_splash.setAnimation(top_anim);
        txt_splash.setAnimation(bottom_anim);
    }

    private void nextActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPreferences = getSharedPreferences("OnBoarding",MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);
                if (isFirstTime){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();

                    Intent intent = new Intent(SplashScreen.this,OnBoarding.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,Welcome.class);
                    startActivity(intent);
                    finish();
                }


            }
        },SPLASH_TIME);
    }

}