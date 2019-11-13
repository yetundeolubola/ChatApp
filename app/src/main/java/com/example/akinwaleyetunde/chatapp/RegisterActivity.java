package com.example.akinwaleyetunde.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt_email,edt_password,edt_confirm,edt_name;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_name = findViewById(R.id.ed_name);
        edt_email = findViewById(R.id.ed_mail);
        edt_password = findViewById(R.id.ed_pass);
        edt_confirm = findViewById(R.id.ed_Confirm);
        TextView userLog = findViewById(R.id.User_login);
        Button signUp = findViewById(R.id.Signup);
        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("messsage" ,message);
        startActivity(intent);


        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        userLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    public void registerUser(){
        String mail = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String confirmPass = edt_confirm.getText().toString().trim();
        String username = edt_name.getText().toString().trim();

        if(username.isEmpty()){
            edt_name.setError("Enter Name");
        }
        if(mail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            edt_email.setError("Invalid email address");
            return;
        }
        if(password.isEmpty() || password.length() < 8){
            edt_password.setError("at least 8 characters");
            return;
        }
        if(confirmPass.isEmpty() || !password.equals(confirmPass)){
            edt_confirm.setError("Password does match");
            return;
        }

        dialog.setMessage("Registering User...");
        dialog.setCancelable(false);
        dialog.show();

        mAuth.createUserWithEmailAndPassword(mail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
