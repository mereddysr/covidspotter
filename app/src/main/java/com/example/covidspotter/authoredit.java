package com.example.covidspotter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class authoredit extends AppCompatActivity {
    Spinner select_spinner;
    ArrayAdapter<CharSequence> adapter1;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference userdataref;
    Button csfrag_update;

    EditText a_email;
    Button a_search;
    CardView a_hide;
    TextView as_email,as_name;
    String cusid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authoredit);
        mAuth=FirebaseAuth.getInstance();
a_email=(EditText)findViewById(R.id.auth_enter);
        select_spinner = (Spinner) findViewById(R.id.select_spin);
        csfrag_update=(Button)findViewById(R.id.cs_fra_update);
        a_search=(Button)findViewById(R.id.auth_search);
        a_hide=(CardView)findViewById(R.id.auth_sh_hide);
        as_email=(TextView)findViewById(R.id.auth_us_email);
        as_name=(TextView)findViewById(R.id.auth_us_name);
userdataref=FirebaseDatabase.getInstance().getReference().child("users");


        adapter1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.array_poitive, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_spinner.setAdapter(adapter1);

        a_hide.setVisibility(View.GONE);

        a_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tes=a_email.getText().toString();
                if(!tes.isEmpty()){
                    checkemail(tes);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter Email",Toast.LENGTH_SHORT).show();
                }
            }
        });

        csfrag_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value=select_spinner.getSelectedItem().toString();

                if(value.equals("Yes")){

                    DatabaseReference reference=firebaseDatabase.getInstance().getReference().child("users").child(cusid).child("report");
                    reference.setValue(value);

                    DatabaseReference reference2=firebaseDatabase.getInstance().getReference().child("positive").child(cusid).child("report");
                    reference2.setValue(value);

                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();

                }else{
                    DatabaseReference reference3=firebaseDatabase.getInstance().getReference().child("users").child(cusid).child("report");
                    reference3.setValue(value);

                    DatabaseReference reference4=firebaseDatabase.getInstance().getReference().child("positive").child(cusid);
                    reference4.removeValue();

                }

            }
        });
    }

    private void checkemail(final String tes) {
        userdataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dt1:dataSnapshot.getChildren()){
                    final String keys= dt1.getKey();

                    if(dt1.hasChild("email")){
                        userdataref.child(keys).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String demail=dataSnapshot.child("email").getValue().toString();
                                String dreport=dataSnapshot.child("report").getValue().toString();

                                if(demail.equals(tes)){
                                    a_hide.setVisibility(View.VISIBLE);
                                    cusid=keys.toString();
                                    String myname=dataSnapshot.child("name").getValue().toString();
                                    as_email.setText(tes);
                                    as_name.setText(myname);

                                    if(dreport.equals("No")){
                                        select_spinner.setSelection(1);
                                    }
                                    else{
                                        select_spinner.setSelection(0);
                                    }
                                }else{
                                    Log.d("error","notfound");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
