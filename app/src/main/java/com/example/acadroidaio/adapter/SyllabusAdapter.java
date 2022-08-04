package com.example.acadroidaio.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acadroidaio.R;

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.SyllabusViewHolder> {

    private String[] name;
    public SyllabusAdapter(String[] name){
        this.name = name;
    }

    @Override
    public SyllabusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.syllabus_layout,parent,false);
        return new SyllabusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SyllabusViewHolder holder, int position) {
        String bkName = name[position];
        holder.syllabusBookName.setText(bkName);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class SyllabusViewHolder extends RecyclerView.ViewHolder{
        ImageView syllabusImg;
        TextView syllabusBookName;
        public SyllabusViewHolder(View itemView){
            super(itemView);
            syllabusImg = itemView.findViewById(R.id.syllabusImg);
            syllabusBookName = itemView.findViewById(R.id.syllabusBookName);
        }
    }
}