package com.example.covidspotter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class firstfrag extends Fragment{
    private static firstfrag INSTANCE=null;

    View view;
    TextView conf,rec,act;

    FirebaseAuth mAuth;
    DatabaseReference dataref;
    public firstfrag(){

    }

    public static firstfrag getINSTANCE() {
        if(INSTANCE==null)
            INSTANCE=new firstfrag();
        return INSTANCE;}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.firstfrag,container,false);
        conf=(TextView)view.findViewById(R.id.case_conf);

        act=(TextView)view.findViewById(R.id.case_act);

        rec=(TextView)view.findViewById(R.id.case_rec);
        mAuth=FirebaseAuth.getInstance();
        dataref = FirebaseDatabase.getInstance().getReference().child("cdata");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getdata();
    }

    private void getdata() {

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String r=dataSnapshot.child("rec").getValue().toString();
                    String a=dataSnapshot.child("act").getValue().toString();
                    String c=dataSnapshot.child("conf").getValue().toString();
                    conf.setText(c);
                    rec.setText(r);
                    act.setText(a);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
