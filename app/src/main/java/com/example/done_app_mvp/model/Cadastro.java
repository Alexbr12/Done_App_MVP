package com.example.done_app_mvp.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.done_app_mvp.ClassesActivity;
import com.example.done_app_mvp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Cadastro extends AppCompatActivity {

    private String senha, email;
    public ProgressDialog mProgressDialog;

    //Dados FireBase
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;

    //Dados Pessoa
    private Pessoa pessoa;
    private ImageView avatar;
    private EditText nome, cpf, profissao, classe, nascimento;

    //Dados Endereco
    private Endereco endereco;
    private EditText cep, rua, num, bairro, cidade, estado;

    private Button save;
    private static final int SELECAO_GALERIA = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        initializer();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        senha = intent.getStringExtra("senha");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email, senha);
                signIn(email, senha);
            }
        });


        //Alterar foto do usuário
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(i, SELECAO_GALERIA);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
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
                if (imagem != null) {

                    //Configura imagem na tela
                    avatar.setImageBitmap(imagem);

                    //Recuperar dados da imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //Salvar imagem no firebase
                    StorageReference imagemRef = mStorageRef
                            .child("imagens")
                            .child("perfil")
                            .child("teste" + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
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


    private void signIn(String email, String password) {
//        Log.d(TAG, "signIn:" + email);
//        if (!validateForm()) {
//            return;
//        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            try {
                                createPessoa();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            createAccount(pessoa, endereco);
                            startActivity(new Intent(getApplicationContext(), ClassesActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Cadastro.this, "Problemas na criação da conta" +
                                            "\nEntre em contato com o Suporte.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);

                        }

                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    public void createAccount(Pessoa pessoa, Endereco endereco) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(pessoa.getClasse());
        myRef.child(pessoa.getProfissao()).child(mAuth.getUid()).setValue(pessoa);

        myRef = database.getReference("ENDERECOS");
        myRef.child(mAuth.getUid()).setValue(endereco);
    }

    private Address local(String local) throws IOException {
        List<Address> adressList;
        Geocoder geocoder = new Geocoder(this);

        adressList = geocoder.getFromLocationName(local, 3);

        if (!adressList.isEmpty())
            return adressList.get(0);
        else
            return null;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void createPessoa()throws IOException {
        //Cria endereço
        endereco = new Endereco();
        endereco.setCep(cep.getText().toString());
        endereco.setRua(rua.getText().toString());
        endereco.setNumero(num.getText().toString());
        endereco.setBairro(bairro.getText().toString());
        endereco.setCidade(cidade.getText().toString());
        endereco.setEstado(estado.getText().toString());

        Address address = local(endereco.toString());
        //Cria pessoa
        pessoa = new Pessoa();
        pessoa.setName(nome.getText().toString());
        pessoa.setCpf(cpf.getText().toString());
        pessoa.setProfissao(profissao.getText().toString());
        pessoa.setClasse(classe.getText().toString());
        pessoa.setNascimento(nascimento.getText().toString());
        pessoa.lat = address.getLatitude();
        pessoa.log = address.getLongitude();
    }

    private void  initializer(){
        //Dados Pessoa
        nome        = findViewById(R.id.nomeTxt);
        cpf         = findViewById(R.id.cpfTxt);
        nascimento  = findViewById(R.id.txtNascimento);
        classe      = findViewById(R.id.txtClasse);
        profissao   = findViewById(R.id.profTxt);
        avatar      = findViewById(R.id.imgAvatar);

        //Dados Endereco
        cep     = findViewById(R.id.txtCep);
        rua     = findViewById(R.id.txtRua);
        num     = findViewById(R.id.txtNumber);
        bairro  = findViewById(R.id.txtBairro);
        cidade  = findViewById(R.id.txtCidade);
        estado  = findViewById(R.id.txtEstado);

        //Buttons
        save = findViewById(R.id.btnSave);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }
}
