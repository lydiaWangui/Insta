package com.lydia.intagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;


public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    ProgressDialog mDialog;

    Button mRegister;
    TextView mMember;
    EditText mUsername,mEmail,mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        init();

        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LogInActivity.class));
                finish();

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("Please wait");
                mDialog.show();

                String username= mUsername.getText().toString().trim();
                String email= mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();

                if(!username.isEmpty()){
                    if (!email.isEmpty()){
                        if (!password.isEmpty()){
                            if(password.length()>=6){
                            }else {
                                Toast.makeText(RegisterActivity.this, "Minimum passsword length should be at least 6 characters", Toast.LENGTH_SHORT).show();
                            }

                        }else mPassword.setError("password cannot be empty");
                                mPassword.requestFocus();

                    }else mEmail.setError("Email cannot be empty");
                        mEmail.requestFocus();

                }else {
                    mUsername.setError("Username cannot be empty");
                    mUsername.requestFocus();
                }
            }
            });
        }
    private void register(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            mReference = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username",username);
                            hashMap.put("bio", "");
                            hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/my-test-project-8eb44.appspot.com/o/IMG_20190530_101149_0.jpg?alt=media&token=c3c8518e-2cd8-44dd-8105-16d5fc2cb28c");


                            mReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mDialog.dismiss();
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                        finish();
                                    }
                                    else {
                                        mDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });
    }

    private void init() {
        mUsername = findViewById(R.id.reg_username);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mRegister= findViewById(R.id.btn_Reg);
        mMember = findViewById(R.id.alreadyAmember);

    }
}
