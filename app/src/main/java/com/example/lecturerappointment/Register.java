package com.example.lecturerappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private Button registerButton;
    private EditText createPassword;
    private EditText confirmPassword;
    private EditText preferdName;
    private EditText emailEdit;
    HashMap<String, String> userMap = new HashMap<>();

    String name, email, password, password2;
    
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  FirebaseAuth createUser;
    private DatabaseReference databaseReference = database.getReference();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createUser = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.button_register);
        createPassword = findViewById(R.id.create_password);

        emailEdit = findViewById(R.id.edit_email_inst);

        confirmPassword = findViewById(R.id.confirm_passowrd);
        
        preferdName = findViewById(R.id.edit_name);

        registerButton.setEnabled(true);

        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                password= createPassword.getText().toString();
                password2= confirmPassword.getText().toString();

                if(!password.equals(password2))
                {
                    Toast.makeText(Register.this, "Passwords do not match, enter passwords again", Toast.LENGTH_SHORT).show();
                    createPassword.getText().clear();
                    confirmPassword.getText().clear();
                    registerButton.setEnabled(false);
                    return;
                }

                else if(password.equals(password2) && password.length()<7)
                {

                    Toast.makeText(Register.this, "Password short, make passwords contain more that 8 characters , enter passwords again", Toast.LENGTH_SHORT).show();
                    createPassword.getText().clear();
                    confirmPassword.getText().clear();
                    registerButton.setEnabled(false);
                    return;
                }
                else
                {
                    registerButton.setEnabled(true);

                }


                name = preferdName.getText().toString();
                email=emailEdit.getText().toString();


                createNewUser(email,password);



            }
        });
        
    }
    public void createNewUser(String email,String password)
    {
        createUser.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {



                    FirebaseUser user = createUser.getCurrentUser();
                    userMap.put("name",name);
                    userMap.put("email",email);
                    databaseReference.child("Users").push().setValue(userMap);
                    Toast.makeText(Register.this, "account created", Toast.LENGTH_SHORT).show();


                    updateUI(user);

                }
                else
                {

                    Toast.makeText(Register.this, "account1 cannot be created", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }
    // start activities according to login users.
    public void updateUI(FirebaseUser user)
    {
        if(user!=null)
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

        }
        else
        {
            reload();
        }

    }

    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = createUser.getCurrentUser();
        if(currentUser!=null)
        {
            reload();
        }
    }
    public void reload()
    {
        Toast.makeText(this, "invalid user", Toast.LENGTH_SHORT).show();
        createPassword.getText().clear();
        confirmPassword.getText().clear();
        preferdName.getText().clear();
        emailEdit.getText().clear();
        Intent login = new Intent(getApplicationContext(),Login.class);
        startActivity(login);
    }
    public void recoverPassword()
    {
        LinearLayout linearLayout =new LinearLayout(Register.this);
        linearLayout.setPadding(10,10,10,10);
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

        builder.setTitle("Recover password");
        final EditText emailEditText = new EditText(Register.this);
        emailEditText.setHint("enter your email");
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
        createUser.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Register.this,"Check your email",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}