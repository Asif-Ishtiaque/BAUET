package com.bauet.bauet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ImageView active_inactive;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mDatabaseReference;
    private  bChatListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //Firebase Database Reference
          mDatabaseReference = FirebaseDatabase.getInstance().getReference();



// TODO: Set up the display name and get the Firebase reference

        setUpDisplayName();



        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chatView);

        // TODO: Send the message when the "enter" button is pressed



        mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });
        // TODO: Add an OnClickListener to the sendButton to send a message
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }



    ///Display Name Setup
    private void setUpDisplayName()
    {
        TextView username_text_field =(TextView) findViewById(R.id.active_user);
        ImageView isActive =(ImageView) findViewById(R.id.user_active);

        SharedPreferences perfs =  getSharedPreferences(Register.CHAT_PREFS, MODE_PRIVATE);
        mDisplayName = perfs.getString(Register.DISPLAY_NAME_KEY,null);
        if(mDisplayName == null)
        {
            isActive.setImageResource(R.drawable.ic_anno);
            mDisplayName ="Anonymous";
        }
        else
        {
            isActive.setImageResource(R.drawable.ic_active);
            username_text_field.setText(mDisplayName);
        }
    }









    // TODO: Retrieve the display name from the Shared Preferences


    private void sendMessage() {

        String Chat_Time;
        TimePull tpull  = new TimePull();
        Chat_Time = tpull.TimePull();




        // TODO: Grab the text the user typed in and push the message to Firebase
        Toast.makeText(Chat.this, "Sent", Toast.LENGTH_SHORT).show();
        String  input_text = mInputText.getText().toString();
       if(!input_text.equals(""))
       {
           bChat chat = new bChat(input_text,mDisplayName,Chat_Time);
           mDatabaseReference.child("bchat").push().setValue(chat);
           mInputText.setText("");
       }
    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.

    @Override
    public void onStart()
    {
        super.onStart();
        mAdapter = new bChatListAdapter(this, mDatabaseReference,mDisplayName);
        mChatListView.setAdapter(mAdapter);

    }

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
        mAdapter.washUP();

    }

}
