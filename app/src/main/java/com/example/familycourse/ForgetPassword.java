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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    EditText et_phone_forget ;
    Button btn_forget ;
    TextView txt_forget;
    Animation move_left_anim , slide_down_anim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        findId();
        animation();

    }

    private void findId(){
        txt_forget = findViewById(R.id.txt_forget);
        et_phone_forget = findViewById(R.id.et_phone_forget);
        btn_forget = findViewById(R.id.btn_forget);
    }
    private void animation(){
        move_left_anim = AnimationUtils.loadAnimation(this,R.anim.move_left_anim);
        txt_forget.setAnimation(move_left_anim);

        slide_down_anim = AnimationUtils.loadAnimation(this,R.anim.slide_down_anim);
        btn_forget.setAnimation(slide_down_anim);

    }
    private void btn_forget(){
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String forgetPhone = et_phone_forget.getText().toString();
                if (forgetPhone.isEmpty()){
                    et_phone_forget.setError("The field is empty ...");
                }
                else {
                    Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(forgetPhone);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()){
                                Intent intent = new Intent(ForgetPassword.this,VerifyForgetPassword.class);
                                intent.putExtra("forgetPhone",forgetPhone);
                                startActivity(intent);
                            }
                            else {
                                et_phone_forget.setError("No such user exist ...");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }
}