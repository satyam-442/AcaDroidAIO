package com.example.acadroidaio.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadroidaio.R;
import com.example.acadroidaio.adapter.SyllabusAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    TextView currentDate;
    TextView userName;
    RecyclerView syllabusRec;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUserID = mAuth.getCurrentUser().getUid();
    DocumentReference usersRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

       syllabusRec = view.findViewById(R.id.syllabusRec);
       syllabusRec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
       String[] syllabusBkName = {"Digital Electronics","Imperative","Mathematics","Digital Electronics","Imperative","Mathematics"};
       syllabusRec.setAdapter(new SyllabusAdapter(syllabusBkName));

       currentDate = view.findViewById(R.id.currentDate);
       userName = view.findViewById(R.id.userName);
       usersRef = db.collection("Students").document(currentUserID);
       usersRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot snapshot) {
               if (snapshot.exists()){
                   String name = snapshot.getString("FName");
                   userName.setText("Hi "+name+",");
               }
               else {
                   Toast.makeText(getContext(), "Error Occurred!", Toast.LENGTH_SHORT).show();
               }
           }
       });

       DateFormat dateFormat = new SimpleDateFormat("dd MMMM");
       Date date = new Date();
       currentDate.setText(dateFormat.format(date));

       return view;
   }
}