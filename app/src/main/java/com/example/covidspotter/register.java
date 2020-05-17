package com.example.covidspotter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    Button csr_register;
    String report="No";
    TextView csr_login;
    private EditText csr_name, csr_mobile, csr_city, csr_email, csr_password;
    private FirebaseAuth firebaseAuth;
    RadioButton csr_male, csr_female;
    private ProgressDialog Loading;
    CheckBox csr_show;

    String name, city, email, password, mobilenumber, userid, gender,image="no-image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        assign_comp();
        firebaseAuth = FirebaseAuth.getInstance();
        csr_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){csr_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());}else{
                    csr_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        csr_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, MainActivity.class));
                finish();
            }
        });
        csr_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Loading.setTitle("Please Wait");
                    Loading.setMessage("Creating account....");
                    Loading.setCanceledOnTouchOutside(false);
                    Loading.show();
                    String m_email = csr_email.getText().toString().trim();
                    String m_password = csr_password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(m_email, m_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                updateinfo();
                                Toast.makeText(register.this, "Registered Sucessfully", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                startActivity(new Intent(register.this, MainActivity.class));
                                finish();

                            } else {
                                Toast.makeText(register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                                Loading.dismiss();
                            }
                        }
                    });

                }

            }
        });


    }

    private void updateinfo() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference().child("users").child(userid);
        userinfo userobj = new userinfo(name, mobilenumber, city, email, gender,report);
        ref.setValue(userobj);
    }

    private void assign_comp() {

            csr_name = (EditText) findViewById(R.id.cs_r_name);
            csr_mobile = (EditText) findViewById(R.id.cs_r_mobileno);
            csr_city = (EditText) findViewById(R.id.cs_r_city);
            csr_email = (EditText) findViewById(R.id.cs_r_email);
            csr_password = (EditText) findViewById(R.id.cs_r_password);

            csr_register = (Button) findViewById(R.id.cs_r_register);
            csr_login = (TextView) findViewById(R.id.cs_r_login);

            csr_male = (RadioButton) findViewById(R.id.cs_r_male);
            csr_female = (RadioButton) findViewById(R.id.cs_r_female);
            csr_show=(CheckBox)findViewById(R.id.cs_r_show);
        Loading = new ProgressDialog(this);



    }

    private Boolean validate() {
        Boolean result = false;
        name = csr_name.getText().toString();
        email = csr_email.getText().toString();
        password = csr_password.getText().toString();
        mobilenumber = csr_mobile.getText().toString();
        city = csr_city.getText().toString();
        gender = "";
        String checkgmail="@";
        if(!csr_male.isChecked() && !csr_female.isChecked()){

            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
        }
        if (csr_male.isChecked()) {
            gender = "Male";

        }
        if (csr_female.isChecked()) {
            gender = "Female";
        }

        else if (name.isEmpty()) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
        } else if (mobilenumber.isEmpty()) {
            Toast.makeText(this, "Please Enter Mobile Number ", Toast.LENGTH_SHORT).show();
        }else if (!(mobilenumber.length() ==10)) {
            Toast.makeText(this, "Check your mobile number", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        }else if (!email.contains(checkgmail)){
            Toast.makeText(this, "Please check your email address", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() ) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        }else if (!(password.length()>8)) {
            Toast.makeText(this, "Password Should be more than 9 characters", Toast.LENGTH_SHORT).show();
        }
        else if (city.isEmpty()) {
            Toast.makeText(this, "Please Enter City", Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;

    }

}
