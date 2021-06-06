package com.example.acadamy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {

    private String recevieracadmy;
    private ImageView image;
    private TextView profilepersonname,profileacadamyname,profilelocation,profileemail,profilephone,profileprice;
    private DatabaseReference userref;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userref= FirebaseDatabase.getInstance().getReference().child("acadmy");

        recevieracadmy=getIntent().getExtras().get("userid").toString();
         image=findViewById(R.id.imageView2);
         profilepersonname=findViewById(R.id.textView10);
         profileacadamyname=findViewById(R.id.textView11);
         profilelocation=findViewById(R.id.textView12);
         profileemail=findViewById(R.id.textView13);
         profilephone=findViewById(R.id.textView14);
         profileprice=findViewById(R.id.textView15);


         RetriveInformation();
    }

    private void RetriveInformation() {
        userref.child(recevieracadmy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String personname=snapshot.child("content").getValue().toString();
                    String acadmyname=snapshot.child("acadamy name").getValue().toString();
                    String location=snapshot.child("location").getValue().toString();
                    String email=snapshot.child("email").getValue().toString();
                    String price=snapshot.child("price").getValue().toString();
                    String phone=snapshot.child("phone").getValue().toString();
                    String imagee = snapshot.child("image").getValue().toString();


                    Picasso.get().load(imagee).placeholder(R.drawable.add).into(image);
                       profileacadamyname.setText(acadmyname);
                       profileemail.setText(email);
                       profilelocation.setText(location);
                       profilepersonname.setText(personname);
                       profilephone.setText(phone);
                       profileprice.setText(price);

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profilee,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call:
            Intent i = new Intent();
            i.setAction(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+profilephone.getText()));
            startActivity(i);

            break;
        }

        return super.onOptionsItemSelected(item);
    }
}