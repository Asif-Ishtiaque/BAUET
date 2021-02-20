package com.bauet.bauet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    // Constants
    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;

    private EditText mID;
    private EditText mYS;
    private EditText mDepartment;
    private EditText mBatch;


    // Firebase instance variables

private FirebaseAuth mAuth;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mEmailView = (EditText) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mUsernameView = (EditText) findViewById(R.id.register_username);

        mID = (EditText) findViewById(R.id.eneter_ID) ;
        mYS = (EditText) findViewById(R.id.YEAR_SEMSTER_BA) ;
        mDepartment = (EditText) findViewById(R.id.Department_Filed) ;
        mBatch = (EditText) findViewById(R.id.batch_filed) ;


        // Keyboard sign in action
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == 200 || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth

        mAuth = FirebaseAuth.getInstance();









        Button signUP = (Button) findViewById(R.id.signUP);
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtoLogin();
            }
        });




    }


    public void backtoLogin()
    {
        Intent intent = new Intent(this, com.bauet.bauet.Login.class);
        finish();
        startActivity(intent);
    }




    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String username = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }


        //UserName Check
        if(TextUtils.isEmpty(username))
        {
            mUsernameView.setError("User Name Required");
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createNewFirebaseUser();

        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@bauet.ac.bd");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        return password.length() >6;
    }
    private boolean isUserNameValid(String username) {
        //TODO: Add own logic to check for a valid username (minimum 3 characters)
        return username.length() >3;
    }




    // TODO: Create a Firebase user
    public void createNewFirebaseUser()
    {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               showErrorDialogs("Successfully Registered, Login From Login Menu");


               if(!task.isSuccessful())
               {

                   showErrorDialogs("UnSuccessfull Registration , Make Sure Your have Proper Internet Connection");
               }
               else
               {
                   //Saved Username Locally
                   savedDisplayName();
                   savedTOInformationMVC();
                   //After Suceessfuly Registering it will go back to Login Activity
                   Intent intent = new Intent(Register.this,Login.class);
                   finish();
                   startActivity(intent);
               }
           }
       });

    }


    // TODO: Save the display name to Shared Preferences


    // TODO: Create an alert dialog to show in case registration failed
   private  void showErrorDialogs(String meassge)
   {
       new AlertDialog.Builder(this)
               .setTitle("REGISTRATION ALERT")
               .setMessage(meassge)
               .setPositiveButton(android.R.string.ok,null)
               .setIcon(R.drawable.ic_bauet)
               .show();
   }

   private void savedDisplayName()
   {
       String username = mUsernameView.getText().toString();
       SharedPreferences perfs =  getSharedPreferences(CHAT_PREFS,0);
       perfs.edit().putString(DISPLAY_NAME_KEY,username).apply();
   }


    private void savedTOInformationMVC()
    {

        String ID = mID.getText().toString();
        String YS = mYS.getText().toString();
        String Dept = mDepartment.getText().toString();
        String Batch = mBatch.getText().toString();

        SharedPreferences mSharedPreferences =getSharedPreferences("UserRegisterPers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("idLS",ID);
        editor.putString("ysLS",YS);
        editor.putString("deptLS",Dept);
        editor.putString("batchLS",Batch);
        editor.commit();
        Toast.makeText(Register.this, "Data Saved", Toast.LENGTH_SHORT).show();



    }





}