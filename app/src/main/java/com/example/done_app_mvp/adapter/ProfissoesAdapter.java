package com.example.done_app_mvp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.done_app_mvp.Locais;
import com.example.done_app_mvp.R;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;


public class ProfissoesAdapter extends RecyclerView.Adapter<ProfissoesAdapter.ProfViewHolder>{
    private ArrayList<CharSequence> mDataProf;

    public ProfissoesAdapter(ArrayList<CharSequence> myDataset){
        this.mDataProf = myDataset;
    }

    @NonNull
    @Override
    public ProfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_btn_item, parent, false);

        ProfViewHolder vh = new ProfViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfViewHolder holder, int position) {
        holder.btnProf.setText(mDataProf.get(position));

        holder.btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Locais.class);
                startActivity(v.getContext(), i, new Bundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataProf.size();
    }

    public static class ProfViewHolder extends RecyclerView.ViewHolder{
        public Button btnProf;
        public ProfViewHolder(@NonNull View itemView) {
            super(itemView);
            btnProf = (Button) itemView.findViewById(R.id.btnProfRec);
        }
    }

}
