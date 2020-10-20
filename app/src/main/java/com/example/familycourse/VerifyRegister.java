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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyRegister extends AppCompatActivity {

    PinView et_verify_register;
    TextView txt_verify_register;
    Button btn_verify_register;
    String phone , name , password, codeBySystem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_register);
        findId();
        database();
        btn_verify_register();
    }

    private void findId(){
        et_verify_register = findViewById(R.id.et_verify_register);
        txt_verify_register = findViewById(R.id.txt_verify_register);
        btn_verify_register = findViewById(R.id.btn_verify_register);
    }
    private void database(){
        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");

        txt_verify_register.setText("Send code your phone number: " + phone);

        sendVerificationCodeToUser(phone);
    }
    private void btn_verify_register(){
        btn_verify_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_verify_register.getText().toString();
                if (code != null){
                    verifyCode(code);
                }
            }
        });
    }
    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
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
                et_verify_register.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyRegister.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(VerifyRegister.this, "Verification not Completed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void storeNewUserData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        UserHelperClass userHelperClass = new UserHelperClass(name,phone,password);
        reference.child(phone).setValue(userHelperClass);
        Toast.makeText(VerifyRegister.this, "Verification Completed!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(VerifyRegister.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}