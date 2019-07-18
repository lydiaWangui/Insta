package com.lydia.intagramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button mylogin, myregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mylogin = findViewById(R.id.login);
        myregister = findViewById(R.id.btn_Reg);

        mylogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirects user to login activity

                startActivity(new Intent(StartActivity.this, LogInActivity.class));
                finish();
            }
        });
        myregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }



}
