package com.example.lecturerappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity
{
    private Button loginButton;
    private EditText userName;
    private EditText passowrd;
    private TextView registerLink;
    private FirebaseAuth author;
    private int count;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.edit_email);
        passowrd = findViewById(R.id.edit_password);

        author = FirebaseAuth.getInstance();
        registerLink = findViewById(R.id.registerlink);
        registerLink.setText("Register");
        registerLink.setVisibility(View.VISIBLE);
        registerLink.setEnabled(true);
        loginButton = findViewById(R.id.button_login);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(getApplicationContext(),Register.class);
                startActivity(registerIntent);

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String p =passowrd.getText().toString() ;
                String u = userName.getText().toString();
                signIn(u,p);

            }
        });


    }
    public void signIn(String email, String password)
    {
        author.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(Login.this,"authentication sussessful ",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = author.getCurrentUser();
                    updateUI(user);

                }
                else
                {
                    Toast.makeText(Login.this,"authentication failed ",Toast.LENGTH_SHORT).show();
                    // forgot passord or needs to register
                    updateUI(null);
                }
            }
        });

    }
    public void updateUI(FirebaseUser user)
    {
        if(user==null)
        {
            Toast.makeText(Login.this, "account doesnt exist click link below to register or reset password", Toast.LENGTH_SHORT).show();
            registerLink.setVisibility(View.VISIBLE);
            registerLink.setText("Register");
            Intent registerIntent = new Intent(Login.this,Register.class);
            startActivity(registerIntent);

        }
        else
        {
            Toast.makeText(Login.this, "Log in successful", Toast.LENGTH_SHORT).show();
            Intent mainActivity = new Intent(Login.this,MainActivity.class);
            startActivity(mainActivity);


        }



    }
}