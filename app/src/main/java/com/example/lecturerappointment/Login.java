package com.example.lecturerappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private TextView forgotPassword;
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
        forgotPassword = findViewById(R.id.forgot_assword);
        forgotPassword.setEnabled(false);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recoverPassword();
            }
        });
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
                String p =passowrd.getText().toString().trim() ;
                String u = userName.getText().toString().trim();
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
                    Toast.makeText(Login.this,"authentication successful",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = author.getCurrentUser();
                    updateUI(user);

                }
                else
                {
                    Toast.makeText(Login.this,"authentication failed, click forgot password",Toast.LENGTH_SHORT).show();
                    // forgot password or needs to register
                    forgotPassword.setEnabled(true);
                    updateUI(null);
                }
            }
        });
    }
    public void recoverPassword()
    {
        LinearLayout linearLayout =new LinearLayout(Login.this);
        linearLayout.setPadding(10,10,10,10);
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

        builder.setTitle("Recover password");
        final EditText emailEditText = new EditText(Login.this);
        emailEditText.setHint("Enter your email");
        emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        linearLayout.addView(emailEditText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = emailEditText.getText().toString().trim();
                beginRecovery(email);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
    public void beginRecovery(String email)
    {
        author.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this,"Check your email",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void updateUI(FirebaseUser user)
    {
        if(user==null)
        {
            Toast.makeText(Login.this, "account does nt exist click link below to register", Toast.LENGTH_SHORT).show();
            registerLink.setVisibility(View.VISIBLE);
            registerLink.setText("Register");
            Intent registerIntent = new Intent(Login.this,Register.class);
            startActivity(registerIntent);

        }
        else
        {
            String email = user.getEmail();
            if(Character.isDigit(email.charAt(0)))
            {
                // put extra email,// name of student
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
            }
            else
            {
                Intent lectureActivity = new Intent(getApplicationContext(),LecturerHome.class);
                startActivity(lectureActivity);
            }

            //Toast.makeText(Login.this, "Log in successful", Toast.LENGTH_SHORT).show();
            //Intent mainActivity = new Intent(Login.this,MainActivity.class);
           // startActivity(mainActivity);


        }



    }
}