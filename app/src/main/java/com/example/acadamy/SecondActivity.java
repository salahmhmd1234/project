package com.example.acadamy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
 ImageView imageView;
 EditText contentname;
    EditText acadnytname;
    EditText locationname;
    EditText emailname;
    EditText phonenamber;
    EditText price;
    Button save;
    Uri uri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        imageView=findViewById(R.id.imageView);
        contentname=findViewById(R.id.editTextTextPersonName);
        acadnytname=findViewById(R.id.editTextTextPersonName2);
        locationname=findViewById(R.id.editTextTextPersonName5);
        emailname=findViewById(R.id.editTextTextPersonName4);
        phonenamber=findViewById(R.id.editTextTextPersonName3);
        price=findViewById(R.id.editTextTextPersonName6);
        save=findViewById(R.id.button2);



         //Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //final DatabaseReference myRef = database.getReference();
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
                       phone.isEmpty() || pricenum.isEmpty()) {
                   Toast.makeText(SecondActivity.this, "Some Details Missing", Toast.LENGTH_SHORT).show();
               } else {


                   HashMap<String, Object> map = new HashMap<>();
                   map.put("content", content);
                   map.put("acadamy name", acadmyname);
                   map.put("location", location);
                   map.put("email", email);
                   map.put("phone", phone);
                   map.put("price", pricenum);
                   // map.put("image",uri);
                   FirebaseDatabase.getInstance().getReference().child("acadmy").push().setValue(map);

                   Toast.makeText(SecondActivity.this, "done", Toast.LENGTH_SHORT).show();


                   Intent i=new Intent(SecondActivity.this,MainActivity.class);
                   startActivity(i);


               }
           }
       });


    }

    public void capture(View view) {
        Intent i=new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==101&&resultCode==RESULT_OK&&data!=null)){
            uri=data.getData();
            imageView.setImageURI(uri);


        }
    }

    public void back(View view) {

    }


}