package com.bauet.bauet;

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

public class Login extends AppCompatActivity {
    // TODO: Add member variables here:
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button signUP;

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

        ImageButton p= (ImageButton) findViewById(R.id.kkkk);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uuu();
            }
        });



        // TODO: Grab an instance of FirebaseAuth

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here

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


        // TODO: Use FirebaseAuth to sign in with email & password



    }

    // TODO: Show error on screen with an alert dialog



}