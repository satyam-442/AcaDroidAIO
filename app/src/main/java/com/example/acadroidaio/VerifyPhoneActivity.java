package com.example.acadroidaio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    Button verifyOTPBtn;
    String fname, mname, lname, section, gradeText, email, password, phone,phoneGaurd , otpID, gender;
    TextView otpText;
    TextInputLayout tenantOTPLay;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        fname = getIntent().getStringExtra("fname");
        mname = getIntent().getStringExtra("mname");
        lname = getIntent().getStringExtra("lname");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        phone = getIntent().getStringExtra("phone");
        phoneGaurd = getIntent().getStringExtra("phoneGaurd");
        section = getIntent().getStringExtra("section");
        gradeText = getIntent().getStringExtra("sem");
        gender = getIntent().getStringExtra("gender");

        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        otpText = findViewById(R.id.otpText);
        otpText.setText("OTP sent to +91" + phone);

        tenantOTPLay = findViewById(R.id.tenantOTPLay);

        initiateOTP();

        verifyOTPBtn = findViewById(R.id.verifyOTPBtn);
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = tenantOTPLay.getEditText().getText().toString();
                if (otp.isEmpty()) {
                    Toast.makeText(VerifyPhoneActivity.this, "OTP is empty", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(otpID, otp);
                    signInWithPhoneAuthCredential(authCredential);
                }
                /*Intent intent = new Intent(getApplicationContext(), AddDocumentActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void initiateOTP() {
        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential credential) {
                                signInWithPhoneAuthCredential(credential);
                                dialog.dismiss();
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                dialog.dismiss();
                                otpID = verificationId;
                                //mResendToken = token;
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    AuthCredential credentialAuth = EmailAuthProvider.getCredential(email, password);
                    mAuth.getCurrentUser().linkWithCredential(credentialAuth);
                    FirebaseUser user = task.getResult().getUser();
                    String userID = user.getUid();

                    HashMap userMap = new HashMap();
                    userMap.put("FName", fname);
                    userMap.put("LName", lname);
                    userMap.put("Email", email);
                    userMap.put("Password", password);
                    userMap.put("Phone", phone);
                    userMap.put("PhoneGuardian", phoneGaurd);
                    userMap.put("uid", userID);
                    userMap.put("image", "default");
                    userMap.put("MName", mname);
                    userMap.put("Section", section);
                    userMap.put("Semester", gradeText);
                    userMap.put("Gender", gender);
                    userMap.put("About", "Hello There, I'm apprentice. I'm using this app to grow my knowledge and master the skills.");
                    db.collection("Students").document(userID).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(VerifyPhoneActivity.this, "OTP Verified ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), VerifyEmailActivity.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(VerifyPhoneActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    // Update UI
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}