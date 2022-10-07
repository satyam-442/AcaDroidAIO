package com.example.acadroidaio.fragment;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.acadroidaio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BookProfileFragment extends Fragment {

    String bookIdStr, downloadUrl, bookName;

    TextView titleOfBook, subjectName;

    ProgressDialog dialog;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String currentUserID = mAuth.getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference booksRef;
    TextView startReadingBtn, downloadBookBtn;

    FirebaseStorage booksFirebaseStorage;
    StorageReference booksStorageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_profile, container, false);

        dialog = new ProgressDialog(getContext());
        bookIdStr = this.getArguments().getString("bookId");
        downloadUrl = this.getArguments().getString("downloadUrl");
        bookName = this.getArguments().getString("bookName");
        booksRef = db.collection("Books").document(bookIdStr);

        booksFirebaseStorage = FirebaseStorage.getInstance();
        booksStorageRef = booksFirebaseStorage.getReference().child("UploadBooks").child(bookName);

        titleOfBook = view.findViewById(R.id.titleOfBook);
        subjectName = view.findViewById(R.id.subjectName);
        startReadingBtn = view.findViewById(R.id.startReadingBtn);

        downloadBookBtn = view.findViewById(R.id.downloadBookBtn);
        downloadBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile();
            }
        });

        booksRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    String subjName = snapshot.getString("Subject");
                    String bookName = snapshot.getString("BookName");
                    String subjNameStr = "Subj: " + subjName;
                    subjectName.setText(subjNameStr);
                    titleOfBook.setText(bookName);
                } else {
                    Toast.makeText(getContext(), "Error Occurred!", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    private void downloadFile() {
        ProgressDialog  pd = new ProgressDialog(getContext());
        pd.setTitle(bookName);
        pd.setMessage("Downloading Please Wait!");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        booksStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                downloadBookFunction(getContext(),bookName, ".pdf",DIRECTORY_DOWNLOADS, uri.toString());
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(getContext(), "downloading", Toast.LENGTH_SHORT).show();
                } else {
                    String msg = task.getException().getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadBookFunction(Context context, String fileName, String extension, String destinationDir, String url) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(destinationDir,"SBM Labs/"+"Books/"+fileName+extension);
        manager.enqueue(request);
    }
}