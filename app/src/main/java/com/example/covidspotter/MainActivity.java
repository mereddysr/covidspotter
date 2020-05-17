package com.example.covidspotter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
//declare components
    EditText csm_email,csm_password;
    Button csm_logbtn;
    TextView csm_forgot,csm_register;
    CheckBox csm_show;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressDialog Loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assign_comp();
        mAuth=FirebaseAuth.getInstance();
//listen user session
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, homeactivity.class);
                    startActivity(intent);
                    finishAffinity();
                    return;
                }
            }

        };

 //show or hide password
        csm_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    csm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    csm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
  //listen forgot click
        csm_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showreset();
            }
        });
 //listen register button
        csm_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Loading.setTitle("Please Wait");
                Loading.setMessage("Loading");
                Loading.setCanceledOnTouchOutside(false);
                Loading.show();
                startActivity(new Intent(MainActivity.this, register.class));
                finish();
            }
        });
//listen login button
        csm_logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( validate()) {
                    Loading.setTitle("Please Wait");
                    Loading.setMessage("Logging....");
                    Loading.setCanceledOnTouchOutside(true);
                    Loading.show();
                    String m_email =csm_email.getText().toString();
                    String m_password = csm_password.getText().toString();
                    mAuth.signInWithEmailAndPassword(m_email, m_password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                Loading.dismiss();

                            } else {
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    });
                }
            }
        });


    }

    private void assign_comp() {
        csm_logbtn=(Button)findViewById(R.id.cs_m_logbtn);
        csm_forgot =(TextView)findViewById(R.id.cs_m_forgot) ;
        csm_register=(TextView)findViewById(R.id.cs_m_register);
        csm_show=(CheckBox)findViewById(R.id.cs_m_show);
        csm_email=(EditText)findViewById(R.id.cs_m_email);
        csm_password=(EditText)findViewById(R.id.cs_m_pass);
        Loading = new ProgressDialog(this);

    }

    private  Boolean validate(){
        Boolean result=false;

        String m_email=csm_email.getText().toString();
        String m_password=csm_password.getText().toString();

        if( m_password.isEmpty() && m_email.isEmpty()){
            Toast.makeText(this,"Email/Password is Empty", Toast.LENGTH_SHORT).show();}
        else if( m_password.isEmpty()){
            Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

//forgot password dialog and verification
private void showreset() {
    AlertDialog.Builder builder=new AlertDialog.Builder(this);
    builder.setTitle("Reset Password");
    LinearLayout linearLayout=new LinearLayout(this);
    final EditText emt=new EditText(this);
    emt.setHint("Enter Registered email");
    emt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    emt.setMinEms(17);
    linearLayout.addView(emt);
    linearLayout.setPadding(40,10,60,10);
    builder.setView(linearLayout);
    builder.setPositiveButton("Send reset Email", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String ema = emt.getText().toString().trim();
            if (!ema.isEmpty()) {
                shrun(ema);
            } else {
                Toast.makeText(getApplicationContext(), "Please Enter Email ", Toast.LENGTH_SHORT).show();
            }


        }
    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    builder.create().show();
}

    private void shrun(String emails) {
        Loading.setMessage("Sending email....");Loading.show();
        mAuth.sendPasswordResetEmail(emails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Loading.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Email Sent ",Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(getApplicationContext(),"Failed to sent Email ",Toast.LENGTH_SHORT).show();}
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Loading.dismiss();
                Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
