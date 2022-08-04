package com.example.acadroidaio.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadroidaio.R;
import com.example.acadroidaio.VerifyPhoneActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SingupFragment extends Fragment {

    TextView signUpBtn, selectedGenderTxt;
    TextInputLayout fNameSignUp, mNameSignUp, lNameSignUp, emailSignUp, passwordSignUp, phoneSignUp,phoneGuardSignUp;
    FirebaseAuth mAuth;
    ProgressDialog dialog;

    Spinner selectClassSpinner, selectYearSpinner;
    TextView classText, gradeText;
    RadioButton male, female, others;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singup, container, false);

        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(getContext());

        //HOOKS FOR SIGNUP
        fNameSignUp = view.findViewById(R.id.fNameSignUp);
        mNameSignUp = view.findViewById(R.id.mNameSignUp);
        lNameSignUp = view.findViewById(R.id.lNameSignUp);
        emailSignUp = view.findViewById(R.id.emailSignUp);
        passwordSignUp = view.findViewById(R.id.passwordSignUp);
        phoneSignUp = view.findViewById(R.id.phoneSignUp);
        phoneGuardSignUp = view.findViewById(R.id.phoneGuardSignUp);

        //HOOKS FOR GENDER RADIO BUTTON
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        others = view.findViewById(R.id.others);
        selectedGenderTxt = view.findViewById(R.id.selectedGenderTxt);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonCLicked(view);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonCLicked(view);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonCLicked(view);
            }
        });

        //HOOKS FOR SPINNER COMPONENTS
        selectClassSpinner = view.findViewById(R.id.selectClassSpinner);
        selectYearSpinner = view.findViewById(R.id.selectYearSpinner);
        classText = view.findViewById(R.id.classText);
        gradeText = view.findViewById(R.id.gradeText);

        signUpBtn = view.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = fNameSignUp.getEditText().getText().toString();
                String mname = mNameSignUp.getEditText().getText().toString();
                String lname = lNameSignUp.getEditText().getText().toString();
                String email = emailSignUp.getEditText().getText().toString();
                String password = passwordSignUp.getEditText().getText().toString();
                String phone = phoneSignUp.getEditText().getText().toString();
                String phoneGuard = phoneGuardSignUp.getEditText().getText().toString();

                if (fname.isEmpty() && lname.isEmpty() && email.isEmpty() && password.isEmpty() && phone.isEmpty() && phoneGuard.isEmpty()){
                    Toast.makeText(getContext(), "Field's are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getContext(), VerifyPhoneActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("phone", phone);
                    intent.putExtra("fname", fname);
                    intent.putExtra("lname", lname);
                    intent.putExtra("mname", mname);
                    intent.putExtra("phoneGaurd", phoneGuard);
                    intent.putExtra("gender", selectedGenderTxt.getText().toString());
                    intent.putExtra("class", classText.getText().toString());
                    intent.putExtra("sem", gradeText.getText().toString());
                    startActivity(intent);
                }
            }
        });

        //SPINNER CODE FOR CLASS AND GRADE
        ArrayAdapter<CharSequence> adapter4Class = ArrayAdapter.createFromResource(getContext(), R.array.class_array, android.R.layout.simple_spinner_item);
        adapter4Class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectClassSpinner.setAdapter(adapter4Class);
        selectClassSpinner.setOnItemSelectedListener(new ClassSpinner());

        ArrayAdapter<CharSequence> adapter4Grade = ArrayAdapter.createFromResource(getContext(), R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter4Grade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectYearSpinner.setAdapter(adapter4Grade);
        selectYearSpinner.setOnItemSelectedListener(new GradeSpinner());

        return view;
    }

    private void onRadioButtonCLicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.male:
                if (checked)
                    selectedGenderTxt.setText("Male");
                break;
            case R.id.female:
                if (checked)
                    selectedGenderTxt.setText("Female");
                break;
            case R.id.others:
                if (checked)
                    selectedGenderTxt.setText("other");
                break;
        }
    }

    private class ClassSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            classText.setText(itemSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    private class GradeSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            gradeText.setText(itemSpinner);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}