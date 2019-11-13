package com.example.akinwaleyetunde.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
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

public class LoginActivity extends AppCompatActivity {

    EditText ed_pass, ed_mail ;
    TextView tx_create;
    Button btn_login;
    ProgressDialog dialog;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tx_create = findViewById(R.id.Create_acct) ;

     ed_mail = findViewById(R.id.Email_Address);
     ed_pass = findViewById(R.id.Password);
     btn_login = findViewById(R.id.Login);

     dialog = new ProgressDialog(this);

     mAuth = FirebaseAuth.getInstance();

     btn_login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             signIn();
         }
     });



//for Create user account
     tx_create.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent IntentCreate = new Intent(LoginActivity.this,RegisterActivity.class);
              startActivity(IntentCreate);
              finish();
          }
      });
    }
    public void signIn (){
        String email = ed_mail.getText().toString();
        String password = ed_pass.getText().toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ed_mail.setError("Invalid email address");
            return;
        }
        if(password.isEmpty() || password.length() < 8){
            ed_pass.setError("at least 8 characters");
            return;
        }

        dialog.setMessage("Signing User...");
        dialog.setCancelable(false);
        dialog.show();
 //for the Login activity
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ChatActivity.class));
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
