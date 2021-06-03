package com.example.acadamy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link f1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class f1 extends Fragment {
    private View acadmyview;
    private RecyclerView contentlist;
    private DatabaseReference databaseReference,userref;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public f1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment f1.
     */
    // TODO: Rename and change types and number of parameters
    public static f1 newInstance(String param1, String param2) {
        f1 fragment = new f1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        acadmyview = inflater.inflate(R.layout.fragment_f1, container, false);


        contentlist = (RecyclerView) acadmyview.findViewById(R.id.recycler);
        contentlist.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("acadmy");

      //  databaseReference=FirebaseDatabase.getInstance().getReference().child("acadmy");
        userref=FirebaseDatabase.getInstance().getReference().child("acadmy");


       // Button button = view.findViewById(R.id.button1);
       // button.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
                //  getFragmentManager().beginTransaction().replace(R.id.container,new f2()).commit();
                //Intent i = new Intent(getActivity(), SecondActivity.class);
               // startActivity(i);


          //  }


       // });
        return acadmyview;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<content>()
                        .setQuery(databaseReference, content.class)
                        .build();

        FirebaseRecyclerAdapter<content, Viewholder> adapter
                = new FirebaseRecyclerAdapter<content, Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Viewholder viewholder, int i, @NonNull content content) {

                viewholder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userid=getRef(i).getKey();
                        Intent i=new Intent(getActivity(),profile.class);
                        i.putExtra("userid",userid);
                        startActivity(i);
                    }
                });



            String acadmyid=getRef(i).getKey();
             userref.child(acadmyid).addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if (snapshot.hasChild("acadamy name")) {
                         String name = snapshot.child("acadamy name").getValue().toString();
                         String price = snapshot.child("price").getValue().toString();



                         viewholder.nametext.setText(name);
                         viewholder.pricetext.setText(price);
                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });
            }

            @NonNull
            @Override
            public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drow, parent, false);
                Viewholder viewholder = new Viewholder(view);
                return viewholder;

            }

        };
        contentlist.setAdapter(adapter);
        adapter.startListening();

    }

    public static class Viewholder extends RecyclerView.ViewHolder {

        TextView nametext, pricetext;



        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nametext = itemView.findViewById(R.id.textView2);
            pricetext = itemView.findViewById(R.id.textView3);

        }
    }
}
