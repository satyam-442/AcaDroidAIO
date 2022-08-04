package com.example.acadroidaio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmailActivity extends AppCompatActivity {

    String email;
    TextView userEmail, sendEmailTxt;
    FirebaseUser user;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        user = mAuth.getCurrentUser();

        email = getIntent().getStringExtra("email");
        userEmail = findViewById(R.id.userEmail);
        userEmail.setText(email);
        sendEmailTxt = findViewById(R.id.sendEmailTxt);
        sendEmailTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(VerifyEmailActivity.this, "E-mail sent successfully", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            Intent loginIntent = new Intent(getApplicationContext(), AuthActivity.class);
                            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(loginIntent);
                            finish();
                        }
                        else {
                            Toast.makeText(VerifyEmailActivity.this, "Error Occurred: "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}