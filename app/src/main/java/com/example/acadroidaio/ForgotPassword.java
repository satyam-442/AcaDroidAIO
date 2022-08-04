package com.example.acadroidaio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    ImageView backBtn;
    TextView signInBtn, openMailBtn;
    TextInputLayout emailForgotPwd;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    LinearLayout sendLinear, sentLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        sendLinear = findViewById(R.id.sendLinear);
        sentLinear = findViewById(R.id.sentLinear);
        emailForgotPwd = findViewById(R.id.emailForgotPwd);
        openMailBtn = findViewById(R.id.openMailBtn);

        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailForgotPwd.getEditText().getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPassword.this, "Field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.setMessage("please wait");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendLinear.setVisibility(View.GONE);
                                sentLinear.setVisibility(View.VISIBLE);
                                openMailBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.gm");
                                        startActivity(intent);
                                    }
                                });
                                //Toast.makeText(ForgotPassword.this, "Reset link sent successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                sendLinear.setVisibility(View.VISIBLE);
                                sentLinear.setVisibility(View.GONE);
                                Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }
}