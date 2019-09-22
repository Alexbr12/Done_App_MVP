package com.example.done_app_mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstPage extends AppCompatActivity {

    private EditText campoProfissao;
    private Button   btnProcurar, btnListar;
    private String   profissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        inicializarComponentes();

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarTodos();
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


    public void inicializarComponentes(){
        campoProfissao = findViewById(R.id.campoProf);
        btnProcurar    = findViewById(R.id.btnProSearch);
        btnListar      = findViewById(R.id.btnListProf);
    }
}
