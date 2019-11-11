package com.example.done_app_mvp.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.done_app_mvp.R;
import com.example.done_app_mvp.model.Pessoa;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import static com.example.done_app_mvp.R.mipmap.ic_launcher;

public class PerfilActivity extends AppCompatActivity {

    ImageView avatar;
    TextView name;
    StorageReference mStorageRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        initializer();
        Pessoa pessoa = (Pessoa) getIntent().getExtras().getParcelable("pessoaParcel");



        final StorageReference imagemRef = mStorageRef
                .child("imagens")
                .child("perfil")
                .child("sNuS6fRhMFRH8rwtJ8OcD8LblNo2.jpeg");//pessoa.getAvatar());//"qmcm5HQCNZdkpLZab3WS2fk90i43" + ".jpeg");

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
//        Glide.with(this /* context */)
//                .load(imagemRef)
//                .into(avatar);
//
//            Bitmap bitmap = null;
//
//
//            bitmap =  MediaStore.Images.Media.getBitmap(getContentResolver(), );
//
//
//            avatar.setImageResource(R.drawable.class_ti);

        try {
            final File localFile = File.createTempFile("images", ".jpg");


            imagemRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            // Successfully downloaded data to local file
                            // ...
                            try {
                                Bitmap imagem = null;
                                Uri localImagemSelecionada = Uri.fromFile(localFile);
                                imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                                //Configura imagem na tela
                                if (imagem != null) {
                                    avatar.setImageBitmap(imagem);
                                }
                                else {
                                    Toast.makeText(PerfilActivity.this, "MERDA", Toast.LENGTH_SHORT).show();
                                }

                            }catch (Exception e){
                                Log.i("ERRO: ", ""+e);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle failed download
                            // ...
                            Log.i("ERRO: ", "Nao criou");
                        }
            });

           // pessoa.setAvatar(imagemRef.toString());
        }catch (Exception e){

        }

//        imagemRef.child("users/me/profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                // Got the download URL for 'users/me/profile.png'
//                Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
//                generatedFilePath = downloadUri.toString(); /// The string(file link) that you need
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });

     }

    public void initializer(){
        avatar  = findViewById(R.id.imgAvatarPerfil);
        name    = findViewById(R.id.txtNamePerfil);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }
}
