package com.example.familycourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class OnBoarding extends AppCompatActivity {

    ViewPager viewPager;
    Button btn_get_started , btn_skip , btn_back , btn_next;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Animation bottom_anim;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        findId();
        skip();
        back();
        next();
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

    }
    private void findId(){
        dotsLayout = findViewById(R.id.dots);
        viewPager = findViewById(R.id.slider);
        btn_get_started = findViewById(R.id.btn_get_started);
        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        btn_skip = findViewById(R.id.btn_skip);
        bottom_anim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
    }
    private void skip(){
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this,Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void back(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPos - 1);
            }
        });
    }

    public void next(){
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPos + 1);
            }
        });
    }

    private void addDots(int position){

        currentPos = position;

        dots = new TextView[4];
        dotsLayout.removeAllViews();
        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0){
            dots[position].setTextColor(getResources().getColor(R.color.red));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if (position == 0){
                btn_get_started.setVisibility(View.INVISIBLE);
                btn_skip.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.INVISIBLE);
            }
            else if (position == 1){
                btn_get_started.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_skip.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.VISIBLE);
            }
            else if (position == 2){
                btn_get_started.setVisibility(View.INVISIBLE);
                btn_next.setVisibility(View.VISIBLE);
                btn_skip.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.VISIBLE);
            }
            else {
                bottom_anim = AnimationUtils.loadAnimation(OnBoarding.this,R.anim.bottom_anim);
                btn_get_started.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.INVISIBLE);
                btn_skip.setVisibility(View.INVISIBLE);
                btn_back.setVisibility(View.VISIBLE);
                btn_get_started.setAnimation(bottom_anim);
                btn_get_started.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(OnBoarding.this, Welcome.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}