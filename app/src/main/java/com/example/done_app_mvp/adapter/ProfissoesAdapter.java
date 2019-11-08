package com.example.done_app_mvp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.done_app_mvp.Locais;
import com.example.done_app_mvp.R;
import com.example.done_app_mvp.model.Pessoa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

import static androidx.core.content.ContextCompat.startActivity;


public class ProfissoesAdapter extends RecyclerView.Adapter<ProfissoesAdapter.ProfViewHolder>{
    private ArrayList<CharSequence> mDataProf;
    private String nomeClasse;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private static final String TAG = "PESSOA: ";

    public ProfissoesAdapter(ArrayList<CharSequence> myDataset, String nomeClasse){
        this.mDataProf = myDataset;
        this.nomeClasse = nomeClasse;
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
    public void onBindViewHolder(@NonNull ProfViewHolder holder, final int position) {
        holder.btnProf.setText(mDataProf.get(position));

        holder.btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i(TAG,""+mDataProf.get(position));

                getPessoas(mDataProf.get(position).toString());
//                Intent i = new Intent(v.getContext(), Locais.class);
//                startActivity(v.getContext(), i, new Bundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataProf.size();
    }

    public void getPessoas(String profissao){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(nomeClasse).child(profissao);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Pessoa pessoa = dataSnapshot.getValue(Pessoa.class);

                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Log.i(TAG, " " + data.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static class ProfViewHolder extends RecyclerView.ViewHolder{
        public Button btnProf;
        public ProfViewHolder(@NonNull View itemView) {
            super(itemView);
            btnProf = (Button) itemView.findViewById(R.id.btnProfRec);
        }
    }

}
