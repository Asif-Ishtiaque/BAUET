package com.bauet.bauet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;






import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    // TODO: Add member variables here:
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button signUP;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 100 || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signUP = (Button) findViewById(R.id.signUP);
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });





        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

    }

    // Executed when Sign in button pressed
    public void logIN(View v)   {
        attemptLogin();

    }

// Executed when Register button pressed
 public void registerNewUser()
 {
          Intent intent = new Intent(this, com.bauet.bauet.Register.class);
            finish();
           startActivity(intent);
}

    public void uuu()
    {
        Intent intent = new Intent(this, com.bauet.bauet.MainActivity.class);
        finish();
        startActivity(intent);
    }



    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {




       String email_log =  mEmailView.getText().toString();
       String password_log =  mPasswordView.getText().toString();

        if(email_log.equals("")||password_log.equals("")) return;
        Toast.makeText(Login.this, "Login in Progress", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password

        mAuth.signInWithEmailAndPassword(email_log,password_log).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(!task.isSuccessful())
                {
                    showErrorDialogs("Login Failed , Make Sure, 1.Your have Account Registered" +
                            "2.You Have Proper Internet Connection" +
                            "3.You Entered Right Email and Password");
                }
                else
                {
                    //After Suceessfuly Registering it will go back to Login Activity
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });


    }

    // TODO: Show error on screen with an alert dialog

    private  void showErrorDialogs(String meassge)
    {
        new AlertDialog.Builder(this)
                .setTitle("LOGIN ALERT")
                .setMessage(meassge)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(R.drawable.ic_bauet)
                .show();
    }

}