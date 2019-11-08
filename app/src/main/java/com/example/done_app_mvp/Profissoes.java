package com.example.done_app_mvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.done_app_mvp.adapter.ProfissoesAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Profissoes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CharSequence> myDataset;
    private String nomeClasse;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissoes);

        EditText title = findViewById(R.id.titleClasse);
        recyclerView = (RecyclerView) findViewById(R.id.recycleBtn);

        Intent i = getIntent();
        myDataset = i.getCharSequenceArrayListExtra("lista");
        nomeClasse = i.getStringExtra("classe");
        title.setText(nomeClasse);


        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ProfissoesAdapter(myDataset, nomeClasse);
        recyclerView.setAdapter(mAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbarPrincipal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnLogout:
                deslogarUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario(){
        try{
            FirebaseAuth.getInstance().signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
