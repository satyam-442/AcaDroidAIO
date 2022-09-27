package com.example.acadroidaio.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acadroidaio.HomeActivity;
import com.example.acadroidaio.R;
import com.example.acadroidaio.modals.Syllabus;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {

    TextView currentDate, userName, classNameTV, semesterName, noDataAvailableTxt;
    RecyclerView syllabusRec;
    ProgressDialog dialog;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUserID = mAuth.getCurrentUser().getUid();
    DocumentReference usersRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference syllabusRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dialog = new ProgressDialog(getContext());

        noDataAvailableTxt = view.findViewById(R.id.noDataAvailableTxt);

        currentDate = view.findViewById(R.id.currentDate);
        userName = view.findViewById(R.id.userName);
        classNameTV = view.findViewById(R.id.className);
        semesterName = view.findViewById(R.id.semesterName);
        syllabusRef = db.collection("Books");
        usersRef = db.collection("Students").document(currentUserID);
        usersRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.getString("FName");
                    String cName = snapshot.getString("Section");
                    String yName = snapshot.getString("Semester");
                    classNameTV.setText(cName.toLowerCase());
                    semesterName.setText(yName.toLowerCase());

                    userName.setText("Hi " + name + ",");

                    if (semesterName.getText().toString().equals(yName.toLowerCase())) {
                        showSyllabusPdfs();
                    } else {
                        Toast.makeText(getContext(), "Fetching details...", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM");
        Date date = new Date();
        currentDate.setText(dateFormat.format(date));

        syllabusRec = view.findViewById(R.id.syllabusRec);
        syllabusRec.setHasFixedSize(true);
        syllabusRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //syllabusRec.setLayoutManager(new LinearLayoutManager(getContext()));

        //showSyllabusPdfs();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showSyllabusPdfs() {
        String sem = semesterName.getText().toString().toUpperCase();
        syllabusRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                if (snapshot.isEmpty()) {
                    //noFactText.setVisibility(View.VISIBLE);
                    noDataAvailableTxt.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                } else {
                    Query query = syllabusRef.whereEqualTo("Semester", sem).orderBy("Subject", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Syllabus> options = new FirestoreRecyclerOptions.Builder<Syllabus>().setQuery(query, Syllabus.class).build();
                    FirestoreRecyclerAdapter<Syllabus, SyllabusHolder> fireAdapter = new FirestoreRecyclerAdapter<Syllabus, SyllabusHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull SyllabusHolder holder, int position, @NonNull Syllabus model) {
                            holder.setSubjectTitle(model.getSubject());
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("bookId", model.getBookId());
                                    BookProfileFragment bookProfileFragment = new BookProfileFragment();
                                    bookProfileFragment.setArguments(bundle);
                                    ((HomeActivity) requireActivity()).replaceFragment(bookProfileFragment, "fragmentB");
                                }
                            });
                            dialog.dismiss();
                        }

                        @NonNull
                        @Override
                        public SyllabusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.syllabus_layout, parent, false);
                            return new SyllabusHolder(view);
                        }
                    };
                    syllabusRec.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                }
            }
        });
    }

    class SyllabusHolder extends RecyclerView.ViewHolder {

        TextView syllabusBookName;

        public SyllabusHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void setSubjectTitle(String title) {
            syllabusBookName = itemView.findViewById(R.id.syllabusBookName);
            syllabusBookName.setText(title);
        }
    }
}