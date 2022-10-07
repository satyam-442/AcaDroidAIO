package com.example.acadroidaio.staff;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acadroidaio.R;

public class StaffLoginActivity extends AppCompatActivity {

    TextView staffForgotPwd, staffSignInBtn;
    ImageView staffBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        staffBackBtn = findViewById(R.id.staffBackBtn);
        staffBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        staffForgotPwd = findViewById(R.id.staffForgotPwd);
        staffSignInBtn = findViewById(R.id.staffSignInBtn);
    }
}