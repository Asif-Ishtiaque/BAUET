package com.bauet.bauet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class books extends AppCompatActivity {

    private String mDisplayName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


       //setting up user name
      setUpDisplayName();

        ImageButton library_book_01 = (ImageButton) findViewById(R.id.book_01);
        library_book_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksViwer_ActivityOpener_book_01();
            }
        });

        ImageButton library_book_02 = (ImageButton) findViewById(R.id.book_02);
        library_book_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksViwer_ActivityOpener_book_02();
            }
        });

        ImageButton library_book_03 = (ImageButton) findViewById(R.id.book_03);
        library_book_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booksViwer_ActivityOpener_book_03();
            }
        });





    }

    protected void booksViwer_ActivityOpener_book_01() {
        Intent intent = new Intent(this, booksViewer.class);
        startActivity(intent);
    }


    protected void booksViwer_ActivityOpener_book_02() {
        Intent intent = new Intent(this, books2.class);
        startActivity(intent);
    }

    protected void booksViwer_ActivityOpener_book_03() {
        Intent intent = new Intent(this, books3.class);
        startActivity(intent);
    }


    private void setUpDisplayName()
    {
        TextView booksname_text_field =(TextView) findViewById(R.id.library_user_name);
        SharedPreferences fardin =  getApplicationContext().getSharedPreferences("UserRegisterPers", Context.MODE_PRIVATE);
        String name =fardin.getString("usernameLS","");
        booksname_text_field.setText(name);

    }
}