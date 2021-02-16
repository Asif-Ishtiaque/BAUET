package com.bauet.bauet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Chat extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ImageView active_inactive;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setUpDisplayName();
// TODO: Set up the display name and get the Firebase reference


        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chatView);

        // TODO: Send the message when the "enter" button is pressed


        // TODO: Add an OnClickListener to the sendButton to send a message

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

        // TODO: Grab the text the user typed in and push the message to Firebase

    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.


    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.

    }

}
