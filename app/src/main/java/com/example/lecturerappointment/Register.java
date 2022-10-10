package com.example.lecturerappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    String name, email, password, password2;
    
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private  FirebaseAuth createUser;
    private DatabaseReference rootNode = database.getReference();
    
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
        password= createPassword.getText().toString();
        password2= confirmPassword.getText().toString();

        if(!password.equals(password2))
        {
            Toast.makeText(this, "Passwords do not match, enter passwords again", Toast.LENGTH_SHORT).show();
            registerButton.setEnabled(false);
        }
        else
        {
            registerButton.setEnabled(true);
        }
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name = preferdName.getText().toString();
                email=emailEdit.getText().toString();
                password= createPassword.getText().toString();
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("name",name);
                userMap.put("email",email);
                userMap.put("password",password);


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
                    Log.d("sign in completed","created account ");

                    FirebaseUser user = createUser.getCurrentUser();
                    Toast.makeText(Register.this, "account created", Toast.LENGTH_SHORT).show();
                    updateUI(user);

                }
                else
                {
                    Log.w("Sign in unsuccesful",task.getException());
                    Toast.makeText(Register.this, "account cannot be created", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }
    // start activities according to login users.
    public void updateUI(FirebaseUser user)
    {

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

    }
}