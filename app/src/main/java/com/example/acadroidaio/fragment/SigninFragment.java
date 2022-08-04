package com.example.acadroidaio.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadroidaio.ForgotPassword;
import com.example.acadroidaio.HomeActivity;
import com.example.acadroidaio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SigninFragment extends Fragment {

    TextView signInBtn, forgotPwd;
    TextInputLayout emailSignIn, passwordSignIn;
    FirebaseAuth mAuth;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(getContext());
        //HOOKS FOR LOGIN
        emailSignIn = view.findViewById(R.id.emailSignIn);
        passwordSignIn = view.findViewById(R.id.passwordSignIn);
        forgotPwd = view.findViewById(R.id.forgotPwd);
        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgotPassword.class);
                startActivity(intent);
            }
        });

        signInBtn = view.findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailSignIn.getEditText().getText().toString();
                String password = passwordSignIn.getEditText().getText().toString();
                if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(getContext(), "Field's are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.setMessage("please wait");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                //SendUserSignInMail(email);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        return view;
    }

    private void SendUserSignInMail(String email) {
        String username = "sbmsoultions95@gmail.com";
        String password = "SBMsoultion1432";
        String msgToSend = "Hello User!\nYou have just logged in to your DYK Portal.";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH-MM-ss");//HOUR-MINUTE-SECOND
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");//HOUR-MINUTE-SECOND-MILLISECOND
        String currentTime = time.format(calendar.getTime());
        String currentDate = date.format(calendar.getTime());
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("DYK: Login Attempted on "+currentDate + " " +currentTime);
            message.setText(msgToSend);
            Transport.send(message);
            //Toast.makeText(getContext(), "Mail Sent", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}