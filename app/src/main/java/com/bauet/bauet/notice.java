package com.bauet.bauet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;

public class notice extends AppCompatActivity {

    private PDFView mPDFView;
    private TextView codV;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference =mDatabase.getReference("PDF ANSWER SHEET");
    DatabaseReference mChild =  mDatabaseReference.child("-MU49Bu5qHIRW-j33epK");
    DatabaseReference urlRead = mChild.child("url");
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //teacher_studemt_system_button
        mPDFView =(PDFView)findViewById(R.id.pdfView);
        codV = (TextView) findViewById(R.id.textP);

        urlRead.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressDialog = new ProgressDialog(notice.this);
                progressDialog.setTitle("OPENING");
                progressDialog.closeOptionsMenu();
                progressDialog.show();


                String value = snapshot.getValue(String.class);
                codV.setText(value);
                Toast.makeText(notice.this, "UPDATED", Toast.LENGTH_SHORT).show();
                String url = codV.getText().toString();
                new notice.RetrivePdfStream().execute(url);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(notice.this, "FAILED TO LOAD", Toast.LENGTH_SHORT).show();

            }
        });}

    class RetrivePdfStream extends AsyncTask<String,Void, InputStream>
    {

        @Override
        protected InputStream doInBackground(String... strings) {


            InputStream inputStream = null;
            try
            {
                URL uRl =  new URL(strings[0]);
                HttpURLConnection urlConnection  = (HttpURLConnection)uRl.openConnection();
                if(urlConnection.getResponseCode()==200)
                {


                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            }
            catch (IOException e)
            {
                progressDialog.dismiss();
                return  null;
            }

            return inputStream;
        }



        @Override
        protected void onPostExecute(InputStream inputStream) {

            mPDFView.fromStream(inputStream).load();


        }
    }
}
