package com.example.done_app_mvp.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.Toolbar;
import com.example.done_app_mvp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ClassesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView  direito, comunicacao, eventos;
    private ImageView gestao, engenharia, construcao, educacao;
    private ImageButton ti;
    private Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        initializer();
        listeners();


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
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ArrayList<CharSequence> createList(String str){
        ArrayList<CharSequence> myList = new ArrayList<>();

        switch (str){
            case ("comunicacao"):
                myList.add("Tradutor"); myList.add("");
                myList.add(""); myList.add("");
                myList.add(""); myList.add("");
                break;
            case ("engenharia"):
                myList.add("Civil");     myList.add("Militar");
                myList.add("Eletrico");  myList.add("Produção");
                myList.add("Alimentos"); myList.add("");
                break;
            case ("educacao"):
                myList.add("Professor Primario"); myList.add("Matematica");
                myList.add("Diretor");            myList.add("Portugues");
                break;
            case ("construcaoC"):
                myList.add("Pedreiro");       myList.add("Ajudante");
                myList.add("Chefe de Obras"); myList.add("Fornecedor");
                break;
            case ("eventos"):
                myList.add(""); myList.add("");
                myList.add(""); myList.add("");
                myList.add(""); myList.add("");
                break;
            case ("gestao"):
                myList.add("Diretor"); myList.add("Secretario");
                myList.add("Contador");
                break;
            case ("direito"):
                myList.add("Cível");    myList.add("Criminal");
                myList.add("Família");  myList.add("Trabalho");
                myList.add("Tributário");
                break;
            case ("ti"):
                myList.add("Redes"); myList.add("Programador");
                myList.add("Analista de Requisitos"); myList.add("QA");
                myList.add("Tecnico");
                break;
            default:
                Toast.makeText(this, "Botao errado", Toast.LENGTH_SHORT).show();
        }

        return (myList);
    }

    public void initializer(){
        ti          = findViewById(R.id.btnTi);
        direito     = findViewById(R.id.imgDireito);
        comunicacao = findViewById(R.id.imgCom);
        eventos     = findViewById(R.id.imgEventos);
        gestao      = findViewById(R.id.imgGestao);
        engenharia  = findViewById(R.id.imgEngenharia);
        construcao  = findViewById(R.id.imgConstrucaoC);
        educacao    = findViewById(R.id.imgEdu);
    }

    public void listeners(){
        ti.setOnClickListener(this);
        comunicacao.setOnClickListener(this);
        eventos.setOnClickListener(this);
        gestao.setOnClickListener(this);
        engenharia.setOnClickListener(this);
        construcao.setOnClickListener(this);
        educacao.setOnClickListener(this);
        direito.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent i = new Intent(getApplicationContext(), ProfissoesActivity.class);
        String nameClasse = view.getTag().toString();

        i.putCharSequenceArrayListExtra("lista",createList(nameClasse));
        i.putExtra("classe", nameClasse);
        startActivity(i);
    }

    private void deslogarUsuario(){
        try{
            FirebaseAuth.getInstance().signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
