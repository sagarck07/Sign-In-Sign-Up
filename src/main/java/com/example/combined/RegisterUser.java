package com.example.combined;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView ul_click_Login;
    private EditText editTextFullName,editTextAge, editTextGender, editTextEmail,editTextMobile, editTextCity, editTextPassword;
    private ProgressBar progressBar;
    private Button signUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        ul_click_Login = (TextView) findViewById(R.id.ul_click_Login);
        ul_click_Login.setOnClickListener(this);

        signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);


        editTextFullName =(EditText) findViewById(R.id.su_name);
        editTextGender =(EditText) findViewById(R.id.su_gender);
        editTextEmail =(EditText) findViewById(R.id.ul_email);
        editTextMobile =(EditText) findViewById(R.id.su_mobile);
        editTextCity =(EditText) findViewById(R.id.su_city);
        editTextPassword =(EditText) findViewById(R.id.ul_password);
        editTextAge=(EditText)findViewById(R.id.su_age);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ul_click_Login:
                startActivity(new Intent(this, LoginTabUserFragment.class));
                break;
            case R.id.signUp:
                signUp();
                break;
        }

    }

    private void signUp() {
        String ul_email = editTextEmail.getText().toString().trim();
        String ul_Password = editTextPassword.getText().toString().trim();
        String su_name = editTextFullName.getText().toString().trim();
        String su_age = editTextAge.getText().toString().trim();
        String su_mobile = editTextMobile.getText().toString().trim();
        String su_city = editTextCity.getText().toString().trim();
        String su_gender = editTextGender.getText().toString().trim();

        if (su_name.isEmpty()) {
            editTextFullName.setError("Name is required");
            editTextFullName.requestFocus();
            return;
        }
        if (su_gender.isEmpty()) {
            editTextGender.setError("Name is required");
            editTextGender.requestFocus();
            return;
        }
        if(ul_email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(ul_email).matches()) {
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(su_age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }

        if(su_mobile.isEmpty()){
            editTextMobile.setError("Mobile Number is Required");
            editTextMobile.requestFocus();
            return;
        }
        if(su_city.isEmpty()){
            editTextCity.setError("city is required");
            editTextCity.requestFocus();
            return;
        }
        if(ul_Password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }
        if(ul_Password.length()<8){
            editTextPassword.setError("Min password length should be 8 characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(ul_email,ul_Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(su_name, su_age,ul_email, su_gender,su_mobile, su_city);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        startActivity(new Intent(RegisterUser.this, LoginTabUserFragment.class));
                                        finish();

                                    }else{
                                        Toast.makeText(RegisterUser.this, "Failed to register! Try again", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this,"Failed to register", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }

                    }
                });


    }
}