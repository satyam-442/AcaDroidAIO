package com.example.acadroidaio.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.acadroidaio.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class QueriesMainFragment extends Fragment {

    Toolbar toolbar;
    Dialog queryDialog;
    ImageView addQuestionPopup, closePopupImage,closePopupComImage, btnSendComment;
    CircleImageView popupProfilePicture;
    TextView titleTv, popupQueryName, btnSendQuery;
    TextInputLayout popupProfileQuery, popupProfileSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_queries_main, container, false);

        toolbar = getActivity().findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.query_menu);
        queryDialog = new Dialog(getContext());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.query_add:
                        ShowQueryPopup();

                }
                return true;
            }
        });

        return view;
    }

    private void ShowQueryPopup()
    {
        queryDialog.setContentView(R.layout.question_layout);
        closePopupImage = queryDialog.findViewById(R.id.closePopupImage);
        popupProfilePicture = queryDialog.findViewById(R.id.popupProfileImage);
        popupQueryName = queryDialog.findViewById(R.id.popupProfileName);
        popupProfileQuery = queryDialog.findViewById(R.id.popupProfileQuery);
        popupProfileSubject = queryDialog.findViewById(R.id.popupProfileSubject);
        btnSendQuery = queryDialog.findViewById(R.id.btnSendQuery);
        btnSendQuery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String query = popupProfileQuery.getEditText().getText().toString();
                String subject = popupProfileSubject.getEditText().getText().toString();
                if(TextUtils.isEmpty(query))
                {
                    Toast.makeText(getContext(), "Your Question is Empty", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(subject))
                {
                    Toast.makeText(getContext(), "Subject is Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    /*loadingBar.setTitle("Status");
                    loadingBar.setMessage("Submitting under progress");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(false);
                    SaveQueryToDatabase();*/
                }
            }
        });


        /*StudRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fullname = dataSnapshot.child("Fullname").getValue().toString();
                popupQueryName.setText(fullname);

                final String image = dataSnapshot.child("image").getValue().toString();
                if(!image.equals("default"))
                {
                    Picasso.with(QuestionActivity.this).load(image).placeholder(R.drawable.default_avatar).into(popupProfilePicture);
                    Picasso.with(QuestionActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.default_avatar).into(popupProfilePicture, new Callback()
                    {
                        @Override
                        public void onSuccess()
                        {

                        }

                        @Override
                        public void onError()
                        {
                            Picasso.with(QuestionActivity.this).load(image).placeholder(R.drawable.default_avatar).into(popupProfilePicture);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        /*titleTv = (TextView) queryDialog.findViewById(R.id.titleTv);
        messageTv = (LinearLayout) queryDialog.findViewById(R.id.messageTv);*/

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(queryDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        queryDialog.show();
        queryDialog.getWindow().setAttributes(lp);

        queryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        queryDialog.show();

        closePopupImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                queryDialog.dismiss();
            }
        });

        queryDialog.setCanceledOnTouchOutside(false);
    }
}