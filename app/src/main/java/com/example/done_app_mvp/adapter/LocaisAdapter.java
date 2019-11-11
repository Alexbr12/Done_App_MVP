package com.example.done_app_mvp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.done_app_mvp.activitys.PerfilActivity;
import com.example.done_app_mvp.R;
import com.example.done_app_mvp.model.Pessoa;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class LocaisAdapter extends RecyclerView.Adapter<LocaisAdapter.LocaisViewHolder>{
    private ArrayList<String> mDatset;
    private Parcelable[] parcelable;

    public LocaisAdapter(ArrayList<String> myDataset, Parcelable[] parcelable){
        this.mDatset = myDataset;
        this.parcelable = parcelable;
    }

    @NonNull
    @Override
    public LocaisAdapter.LocaisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_local_item, parent, false);

        LocaisAdapter.LocaisViewHolder vh = new LocaisAdapter.LocaisViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LocaisAdapter.LocaisViewHolder holder, final int position) {
        holder.button.setText((CharSequence) mDatset.get(position));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), PerfilActivity.class);
                Pessoa pessoa = (Pessoa) parcelable[position];
                i.putExtra("pessoaParcel", pessoa);
                startActivity(v.getContext(), i, new Bundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatset.size();
    }

    public static class LocaisViewHolder extends RecyclerView.ViewHolder{
        public Button button;
        public LocaisViewHolder(@NonNull View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.btnLocal);

        }
    }
}
