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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText et_phone_register ,  et_password_register , et_confirm_password_register , et_name_register ;
    Button btn_register;
    TextView txt_register;
    Animation move_left_anim , slide_down_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findId();
        animation();
        btn_register();

    }

    private void findId(){
        et_name_register = findViewById(R.id.et_name_register);
        et_phone_register = findViewById(R.id.et_phone_register);
        et_password_register = findViewById(R.id.et_password_register);
        et_confirm_password_register = findViewById(R.id.et_confirm_password_register);
        btn_register = findViewById(R.id.btn_register);
        txt_register = findViewById(R.id.txt_register);
    }
    private void animation(){
        move_left_anim = AnimationUtils.loadAnimation(this,R.anim.move_left_anim);
        txt_register.setAnimation(move_left_anim);

        slide_down_anim = AnimationUtils.loadAnimation(this,R.anim.slide_down_anim);
        btn_register.setAnimation(slide_down_anim);
    }
    private void btn_register(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phone_register.getText().toString();
                String name = et_name_register.getText().toString();
                String password = et_password_register.getText().toString();
                String confirm = et_confirm_password_register.getText().toString();

                Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(phone);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()){
                            if (name.isEmpty()){
                                et_name_register.setError("The field is empty ...");
                            }
                            else if (name.length() > 16){
                                et_name_register.setError("Your name too long ...");
                            }
                            else if (phone.isEmpty()){
                                et_phone_register.setError("The field is empty ...");
                            }
                            else if (phone.length() != 12 ){
                                et_phone_register.setError("Your phone number not valid ... ");
                            }
                            else if (password.isEmpty()){
                                et_password_register.setError("The field is empty ...");
                            }
                            else if (password.length() < 8  ){
                                et_password_register.setError("Your password too short ...");
                            }
                            else if (password.length() > 16  ){
                                et_password_register.setError("Your password too long ...");
                            }
                            else if (confirm.isEmpty()){
                                et_confirm_password_register.setError("The field is empty ...");
                            }
                            else if (!password.equals(confirm) ){
                                et_confirm_password_register.setError("Password must equals ...");
                            }
                            else {
                                Intent intent = new Intent(Register.this,VerifyRegister.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("phone",phone);
                                intent.putExtra("name",name);
                                intent.putExtra("password",password);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(Register.this, "Your phone number uje registered ...", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}