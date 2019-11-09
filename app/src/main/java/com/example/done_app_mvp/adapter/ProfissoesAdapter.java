package com.example.done_app_mvp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.done_app_mvp.Locais;
import com.example.done_app_mvp.Profissoes;
import com.example.done_app_mvp.R;
import com.example.done_app_mvp.model.Pessoa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;


public class ProfissoesAdapter extends RecyclerView.Adapter<ProfissoesAdapter.ProfViewHolder>{
    private ArrayList<CharSequence> mDataProf;
    private String nomeClasse;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<Pessoa> pessoaArrayList;

    private static final String TAG = "ADAPTER: ";

    public ProfissoesAdapter(ArrayList<CharSequence> myDataset, String nomeClasse){
        this.mDataProf = myDataset;
        this.nomeClasse = nomeClasse;
        pessoaArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_btn_item, parent, false);

        return (new ProfViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull ProfViewHolder holder, int position) {
        holder.btnProf.setText(mDataProf.get(position));
        final int posi = position;

        holder.btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPessoas(mDataProf.get(posi).toString(), v);


//                int size = pessoaArrayList.size();
//                if(size > 0) {
//                    String [] nomes     = new String[size];
//                    double [] longitude = new double[size];
//                    double [] latitude  = new double[size];
//
//                    for (int i = 0; i < size; i++) {
//                        nomes[i]     = pessoaArrayList.get(i).getName();
//                        longitude[i] = pessoaArrayList.get(i).log;
//                        latitude[i]  = pessoaArrayList.get(i).lat;
//                    }
//
//                    Intent i = new Intent(v.getContext(), Locais.class);
//                    i.putExtra("nomes", nomes);
//                    i.putExtra("longitude", longitude);
//                    i.putExtra("latitude", latitude);
//
//                    startActivity(v.getContext(), i, new Bundle());
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataProf.size();
    }


    public void getPessoas(String profissao, final View view){
        database = FirebaseDatabase.getInstance();

        try {
            myRef = database.getReference(nomeClasse).child(profissao);
        }catch (DatabaseException e){
            Toast.makeText(view.getContext(), "Desculpe",Toast.LENGTH_SHORT).show();
            return;
        }

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "PASSOU AQUI!");

                for (DataSnapshot data: dataSnapshot.getChildren())
                    pessoaArrayList.add(data.getValue(Pessoa.class));


                int size = pessoaArrayList.size();
                if(size > 0) {
                    String [] nomes     = new String[size];
                    double [] longitude = new double[size];
                    double [] latitude  = new double[size];

                    for (int i = 0; i < size; i++) {
                        nomes[i]     = pessoaArrayList.get(i).getName();
                        longitude[i] = pessoaArrayList.get(i).log;
                        latitude[i]  = pessoaArrayList.get(i).lat;
                    }

                    Intent i = new Intent(view.getContext(), Locais.class);
                    i.putExtra("nomes", nomes);
                    i.putExtra("longitude", longitude);
                    i.putExtra("latitude", latitude);

                    startActivity(view.getContext(), i, new Bundle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void flush(){
        pessoaArrayList.removeAll(pessoaArrayList);
    }

    /* Class View Holder */
    public static class ProfViewHolder extends RecyclerView.ViewHolder {
        private final Button btnProf;
        public ProfViewHolder(@NonNull View itemView) {
            super(itemView);
            btnProf = (Button) itemView.findViewById(R.id.btnProfRec);

        }
    }

}
