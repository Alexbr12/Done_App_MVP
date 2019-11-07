package com.example.done_app_mvp.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.done_app_mvp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Cadastro extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private StorageReference mStorageRef;

    private Pessoa pessoa;
    private Button save;
    private ImageView avatar;
    private static final int SELECAO_GALERIA = 200;
    private EditText nome, cpf, regiao, profissao;
    private GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        initializer();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistir(create());
            }
        });


        //Alterar foto do usuário
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if( i.resolveActivity(getPackageManager()) != null ){
                    startActivityForResult(i, SELECAO_GALERIA );
                }
            }
        });


//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//        StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
//
//        riversRef.putFile(file)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        StorageMetadata downloadUrl = taskSnapshot.getMetadata();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {

                //Selecao apenas da galeria
                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }

                //Caso tenha sido escolhido uma imagem
                if (imagem != null) {

                    //Configura imagem na tela
                    avatar.setImageBitmap(imagem);
                }

                //Caso tenha sido escolhido uma imagem
                if ( imagem != null ){

                    //Configura imagem na tela
                    avatar.setImageBitmap( imagem );

                    //Recuperar dados da imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //Salvar imagem no firebase
                    StorageReference imagemRef = mStorageRef
                            .child("imagens")
                            .child("perfil")
                            .child( "teste" + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes( dadosImagem );
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Cadastro.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //Recuperar local da foto
//                            Uri url = taskSnapshot.getDownloadUrl();
//                            atualizarFotoUsuario( url );

                            Toast.makeText(Cadastro.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void persistir(Pessoa pessoa){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("classeTeste");

        myRef.child(pessoa.getProfissao()).child(pessoa.id).setValue(pessoa);

    }

    public Pessoa create(){
        pessoa = new Pessoa();

        pessoa.setName(nome.getText().toString());
        pessoa.setCpf(cpf.getText().toString());
        pessoa.setRegiao(regiao.getText().toString());
        pessoa.setProfissao(profissao.getText().toString());

        pessoa.id = FirebaseAuth.getInstance().getUid();

        return pessoa;
    }

    private void  initializer(){
        nome = findViewById(R.id.nomeTxt);
        cpf = findViewById(R.id.cpfTxt);
        regiao = findViewById(R.id.regiaoTxt);
        profissao = findViewById(R.id.profTxt);
        save = findViewById(R.id.btnSave);
        avatar = findViewById(R.id.imgAvatar);
    }
}
