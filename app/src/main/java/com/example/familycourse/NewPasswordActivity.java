package com.example.familycourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPasswordActivity extends AppCompatActivity {

    EditText et_password_new , et_confirm_password_new ;
    Button btn_new ;
    TextView txt_new;
    Animation move_left_anim , slide_down_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        findId();
        animation();
    }
    private void findId(){
        et_password_new = findViewById(R.id.et_password_new);
        et_confirm_password_new = findViewById(R.id.et_confirm_password_new);
        btn_new = findViewById(R.id.btn_new);
        txt_new = findViewById(R.id.txt_new);
    }
    private void animation(){
        move_left_anim = AnimationUtils.loadAnimation(this,R.anim.move_left_anim);
        txt_new.setAnimation(move_left_anim);

        slide_down_anim = AnimationUtils.loadAnimation(this,R.anim.slide_down_anim);
        btn_new.setAnimation(slide_down_anim);
    }
    private void btn_new(){
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password_new.getText().toString();
                String confirm = et_confirm_password_new.getText().toString();
                String phone = getIntent().getStringExtra("phone");

                if (password.isEmpty()){
                    et_password_new.setError("The field is empty ...");
                }
                else if (password.length() < 8  ){
                    et_password_new.setError("Your password too short ...");
                }
                else if (password.length() > 16  ){
                    et_password_new.setError("Your password too long ...");
                }
                else if (confirm.isEmpty()){
                    et_confirm_password_new.setError("The field is empty ...");
                }
                else if (!password.equals(confirm) ){
                    et_confirm_password_new.setError("Password must equals ...");
                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.child(phone).child("password").setValue(password);
                    Intent intent = new Intent(getApplicationContext(),SuccessNewPassword.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}