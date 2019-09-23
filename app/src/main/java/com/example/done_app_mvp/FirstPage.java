package com.example.done_app_mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstPage extends AppCompatActivity {

    private static final String TAG = "FirstPage";
    private TextView teste;
    private EditText campoProfissao;
    private Button   btnProcurar, btnListar;
    private String   profissao;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        inicializarComponentes();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        gravar();


        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ler();
                startActivity(new Intent(getApplicationContext(), Lista.class));
            }
        });

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profissao = campoProfissao.getText().toString();

                if( !profissao.isEmpty() ){
                    findProfissional(profissao);
                }else{
                    Toast.makeText(FirstPage.this, "Informe a Profissão.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void listarTodos(){
        Toast.makeText(FirstPage.this, "Aqui estao todos profissionais", Toast.LENGTH_SHORT).show();
    }

    public void findProfissional(String prof){
        Toast.makeText(FirstPage.this, "Deseja esse profissional: " + prof, Toast.LENGTH_SHORT).show();
    }

    public void gravar(){
        // Write a message to the database
        myRef.setValue("Hello, World!");
    }

    public void ler(){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                teste.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void inicializarComponentes(){
        campoProfissao = findViewById(R.id.campoProf);
        btnProcurar    = findViewById(R.id.btnProSearch);
        btnListar      = findViewById(R.id.btnListProf);
        teste          = findViewById(R.id.teste);
    }
}
