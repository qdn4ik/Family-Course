package com.example.familycourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyForgetPassword extends AppCompatActivity {

    PinView et_verify_forget;
    TextView txt_verify_forget;
    Button btn_verify_forget;
    String forgetPhone , codeBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forget_password);
        findId();
        database();
        btn_verify_forget();
    }
    private void findId(){
        et_verify_forget = findViewById(R.id.et_verify_forget);
        txt_verify_forget = findViewById(R.id.txt_verify_forget);
        btn_verify_forget = findViewById(R.id.btn_verify_forget);
    }
    private void database(){
        forgetPhone = getIntent().getStringExtra("forgetPhone");

        txt_verify_forget.setText(forgetPhone);

        sendVerificationCodeToUser(forgetPhone);
    }
    private void btn_verify_forget(){
        btn_verify_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_verify_forget.getText().toString();
                if (code != null){
                    verifyCode(code);
                }
            }
        });
    }
    private void sendVerificationCodeToUser(String forgetPhone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                forgetPhone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks
            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                et_verify_forget.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyForgetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            storeNewUserData();
                        } else {
                            Toast.makeText(VerifyForgetPassword.this, "Verification not Completed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void storeNewUserData() {

        Intent intent = new Intent(VerifyForgetPassword.this,NewPasswordActivity.class);
        intent.putExtra("phone",forgetPhone);
        startActivity(intent);
        finish();
    }
}