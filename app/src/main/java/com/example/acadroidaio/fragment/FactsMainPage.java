package com.example.acadroidaio.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acadroidaio.HomeActivity;
import com.example.acadroidaio.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FactsMainPage extends Fragment {

    TextView asciiCodesBtn, binaryCodesBtn, memoryUnitsBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facts_main_page, container, false);

        memoryUnitsBtn = view.findViewById(R.id.memoryUnitsBtn);
        memoryUnitsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemoryUnitsFragment unitsFragment = new MemoryUnitsFragment();
                ((HomeActivity) getActivity()).replaceFragment(unitsFragment,"fragmentB");
            }
        });

        binaryCodesBtn = view.findViewById(R.id.binaryCodesBtn);
        binaryCodesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BinaryCodesFragment binaryCodesFragment = new BinaryCodesFragment();
                ((HomeActivity) requireActivity()).replaceFragment(binaryCodesFragment, "fragmentB");
            }
        });

        asciiCodesBtn = view.findViewById(R.id.asciiCodesBtn);
        asciiCodesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsciiCodeFragment asciiCodeFragment = new AsciiCodeFragment();
                ((HomeActivity) requireActivity()).replaceFragment(asciiCodeFragment, "fragmentB");
            }
        });

        return view;
    }

    private void openPricesBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.ascii_bottom_sheet);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }

}