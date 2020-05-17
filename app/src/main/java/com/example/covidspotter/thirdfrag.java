package com.example.covidspotter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class thirdfrag extends Fragment { private static thirdfrag INSTANCE=null;

    String userid;
    View view;
    private ProgressDialog Loading;
    String vern;
    FirebaseAuth mAuth;
DatabaseReference userdataref;
    TextView se_uname, se_umob, se_ugender, se_ucity, se_uemail, se_ureport, se_aver;
    CardView s__logout,change_p,authref;

    public thirdfrag(){

    }

    public static thirdfrag getINSTANCE() {
        if(INSTANCE==null)
            INSTANCE=new thirdfrag();
        return INSTANCE;}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.thirdfrag,container,false);
authref=(CardView)view.findViewById(R.id.cs_tf_sh);
        se_uname = (TextView) view.findViewById(R.id.sett_name);
        se_umob = (TextView) view.findViewById(R.id.sett_mob);
        se_ugender = (TextView)view. findViewById(R.id.sett_gen);
        se_ucity = (TextView)view.findViewById(R.id.sett_city);
        se_uemail = (TextView)view.findViewById(R.id.sett_email);
        se_ureport = (TextView)view. findViewById(R.id.sett_bgrp);
        se_aver = (TextView)view.findViewById(R.id.sett_ver);
        s__logout=(CardView)view.findViewById(R.id.sett_log);
        change_p=(CardView)view.findViewById(R.id.ch_p);
        Loading=new ProgressDialog(getActivity());
        mAuth=FirebaseAuth.getInstance();
        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        vern=BuildConfig.VERSION_NAME;
        userdataref = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        authref.setVisibility(View.GONE);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //spinner
        s__logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlogout();
            }
        });


        change_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    getemail();}catch (Exception e){}
            }
        });


        authref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),authoredit.class);
                startActivity(intent);
            }
        });

        getuserdata();

    }


    public void onlogout() {
        Loading.setTitle("Please Wait");
        Loading.setMessage("Logging out...");
        Loading.setCanceledOnTouchOutside(true);
        Loading.show();
        mAuth.getInstance().signOut();

        Toast.makeText(getActivity(), "Logout Sucess", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
getActivity().finish();

    }
    private void getemail() {
        userdataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String v_emk= String.valueOf(dataSnapshot.child("email").getValue());
                try {
                    showreset(v_emk);}catch (Exception e){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showreset(final String v_emk) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Password");
        LinearLayout linearLayout=new LinearLayout(getActivity());
        TextView te=new TextView(getActivity());
        te.setHint("Click Ok to send Password Update link to your Email: "+v_emk);
        te.setMinEms(15);
        linearLayout.addView(te);
        linearLayout.setPadding(70,10,60,10);
        builder.setView(linearLayout);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Loading.setMessage("Sending email....");Loading.show();


                mAuth.sendPasswordResetEmail(v_emk).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Loading.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Email Sent ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to sent Email ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Loading.dismiss();
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void getuserdata() {

        userdataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if ((dataSnapshot.exists())) {
                        String n = dataSnapshot.child("name").getValue().toString();
                        String e = dataSnapshot.child("email").getValue().toString();
                        String m = dataSnapshot.child("mob").getValue().toString();
                        String g = dataSnapshot.child("gender").getValue().toString();
                        String c = dataSnapshot.child("city").getValue().toString();
                        String b = dataSnapshot.child("report").getValue().toString();
                        try{}catch(Exception es){
                                                    String auth=dataSnapshot.child("adminstat").getValue().toString();

                            
if(auth.equals("1")){
    authref.setVisibility(View.VISIBLE);
}else{
    authref.setVisibility(View.GONE);

}
                        }

                        se_uname.setText(n);
                        se_umob.setText(m);
                        se_uemail.setText(e);
                        se_ugender.setText(g);
                        se_ucity.setText(c);
                        se_aver.setText(vern);
                        //database status add


                    } else {
                        String n = dataSnapshot.child("name").getValue().toString();
                        String e = dataSnapshot.child("email").getValue().toString();
                        String m = dataSnapshot.child("mob").getValue().toString();
                        String g = dataSnapshot.child("gender").getValue().toString();
                        String c = dataSnapshot.child("city").getValue().toString();
                        String b = dataSnapshot.child("report").getValue().toString();
                        se_uname.setText(n);
                        se_umob.setText(m);
                        se_uemail.setText(e);
                        se_ugender.setText(g);
                        se_ucity.setText(c);
                        se_ureport.setText(b);
                        se_aver.setText(vern);

                        //Toast.makeText(getApplicationContext(), " complaint", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
