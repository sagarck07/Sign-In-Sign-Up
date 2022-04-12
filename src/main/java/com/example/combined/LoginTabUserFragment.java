package com.example.combined;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginTabUserFragment extends Fragment implements View.OnClickListener{

    View ObjectLoginUserFragment;

    private TextView ul_click_createAccount, ul_forgot_Password;
    private EditText editTextEmail, editTextPassword;
    private Button Login;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;


    EditText ul_email, ul_password;
    Button ul_login;


    public LoginTabUserFragment(){


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ObjectLoginUserFragment = inflater.inflate(R.layout.activity_login_tab_user_fragment, container, false);


        ul_click_createAccount = ul_click_createAccount.findViewById(R.id.ul_click_createAccount);
        ul_click_createAccount.setOnClickListener(this);

        ul_login = ul_login.findViewById(R.id.ul_login);
        Login.setOnClickListener(this);

        editTextEmail = editTextEmail.findViewById(R.id.ul_email);
        editTextPassword = editTextPassword.findViewById(R.id.ul_password);

        progressBar = progressBar.findViewById(R.id.progressBar);


        mAuth = FirebaseAuth.getInstance();

        return ObjectLoginUserFragment;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ul_click_createAccount:
                startActivity(new Intent(v.getContext(), RegisterUser.class));
                break;

            case R.id.ul_login:
                userLogin();
                break;

        }

    }


    private void userLogin() {
        String ul_email = editTextEmail.getText().toString().trim();
        String ul_password = editTextPassword.getText().toString().trim();

        if(ul_email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(ul_password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(ul_password.length()<8){
            editTextPassword.setError("P Minimum length is 8 characters");
        }
        progressBar.setVisibility(View.VISIBLE);


        if(mAuth !=null){
            progressBar.setVisibility(View.VISIBLE);
            ul_login.setEnabled(false);

            mAuth.signInWithEmailAndPassword(ul_email, ul_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                }
            });
        }



        mAuth.signInWithEmailAndPassword(ul_email, ul_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            private void checkUser(){
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if(mAuth !=null){


                }
            }


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful() ){
                    startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));

                }else{
                    Toast.makeText(getActivity().getApplicationContext(), "Failed to login Please Check your credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}


