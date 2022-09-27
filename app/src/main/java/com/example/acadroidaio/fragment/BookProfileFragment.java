package com.example.acadroidaio.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acadroidaio.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookProfileFragment extends Fragment {

    String bookIdStr;

    TextView titleOfBook, subjectName;

    ProgressDialog dialog;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUserID = mAuth.getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference booksRef;
    TextView startReadingBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_profile, container, false);

        dialog = new ProgressDialog(getContext());
        bookIdStr = this.getArguments().getString("bookId");
        booksRef = db.collection("Books").document(bookIdStr);

        titleOfBook = view.findViewById(R.id.titleOfBook);
        subjectName = view.findViewById(R.id.subjectName);
        startReadingBtn = view.findViewById(R.id.startReadingBtn);

        booksRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    String subjName = snapshot.getString("Subject");
                    String bookName = snapshot.getString("BookName");
                    subjectName.setText("Subj: "+subjName);
                    titleOfBook.setText(bookName);
                } else {
                    Toast.makeText(getContext(), "Error Occurred!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}