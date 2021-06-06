package com.example.acadamy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {
    ImageView imageView;
    EditText contentname;
    EditText acadnytname;
    EditText locationname;
    EditText emailname;
    EditText phonenamber;
    EditText price;
    Button save;


    private Uri imageuri;
    private DatabaseReference root=FirebaseDatabase.getInstance().getReference().child("acadmy");
    private StorageReference reference=FirebaseStorage.getInstance().getReference("image");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        contentname=findViewById(R.id.editTextTextPersonName);
        acadnytname=findViewById(R.id.editTextTextPersonName2);
        locationname=findViewById(R.id.editTextTextPersonName5);
        emailname=findViewById(R.id.editTextTextPersonName4);
        phonenamber=findViewById(R.id.editTextTextPersonName3);
        price=findViewById(R.id.editTextTextPersonName6);
        save=findViewById(R.id.button2);
        imageView=findViewById(R.id.imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                choosepicture();
            }
        });


         //Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String content = contentname.getText().toString();
               String acadmyname = acadnytname.getText().toString();
               String location = locationname.getText().toString();
               String email = emailname.getText().toString();
               String phone = phonenamber.getText().toString();
               String pricenum = price.getText().toString();


               if (content.isEmpty() || acadmyname.isEmpty() || location.isEmpty() || email.isEmpty() ||
                       phone.isEmpty() || pricenum.isEmpty() ) {
                   Toast.makeText(SecondActivity.this, "Some Details Missing", Toast.LENGTH_SHORT).show();
               }



               else {
                   StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtention(imageuri));
                   fileRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   HashMap<String, Object> map = new HashMap<>();

                                   map.put("content", content);
                                   map.put("acadamy name", acadmyname);
                                   map.put("location", location);
                                   map.put("email", email);
                                   map.put("phone", phone);
                                   map.put("price", pricenum);
                                   String image =  uri.toString();
                                   map.put("image",image);
                                   root.push().setValue(map);
                               }
                           });
                       }
                   }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(SecondActivity.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
                       }
                   });

                   Toast.makeText(SecondActivity.this, "done", Toast.LENGTH_SHORT).show();

                   Intent i=new Intent(SecondActivity.this,MainActivity.class);
                   startActivity(i);

               }
           }
       });


    }

    private void choosepicture() {

        Intent i=new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,101);


    }





    public void capture(View view) {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==101&&resultCode==RESULT_OK&&data!=null)){
            imageuri=data.getData();
            imageView.setImageURI(imageuri);



        }
    }

    private String getFileExtention(Uri mUri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));


    }


    public void back(View view) {

    }


}