package com.bauet.bauet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class exam extends AppCompatActivity {

    EditText selection_storage;
    ImageButton upload_button;
     StorageReference mStorageReference;
     DatabaseReference mDatabaseReference;
     ImageButton equestion_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        selection_storage =(EditText)findViewById(R.id.upload_pdf);
        upload_button =(ImageButton) findViewById(R.id.upload_button);


        //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //User Name Update
        dataUI();



        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("PDF ANSWER SHEET");
        upload_button.setEnabled(false);


        selection_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileselection();
            }
        });


        equestion_button = (ImageButton) findViewById(R.id.pdf_hiamge);
        equestion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdf_activity();
            }
        });
    }

    protected  void pdf_activity()
    {
        Intent intent = new Intent(this, pdviewer.class);
        startActivity(intent);
    }



    private  void fileselection()
    {
        Intent myFile = new Intent();
        myFile.setType("application/pdf");
        myFile.setAction(myFile.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(myFile,"PDF FILE SELECT"),12);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==12 && resultCode ==RESULT_OK &&  data!=null &&  data.getData() !=null)
        {
            upload_button.setEnabled(true);
            selection_storage.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));

            upload_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  uploadpdftofirebase(data.getData());
                }
            });
        }
    }

    private void uploadpdftofirebase(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is Uploading");
        progressDialog.show();
        StorageReference reference = mStorageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                    while(!uriTask.isComplete());
                    Uri uri = uriTask.getResult();

                        putPDF  putPDF = new putPDF(selection_storage.getText().toString(),uri.toString());
                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(putPDF);
                        progressDialog.dismiss();
                         showDialogs("File Uploaded Successfully");


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress =  (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("File  is  Uploading.. "+(int) progress+"%");

            }
        });
    }

    private  void showDialogs(String meassge)
    {
        new AlertDialog.Builder(this)
                .setTitle("Upload Status")
                .setMessage(meassge)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(R.drawable.ic_bauet)
                .show();
    }




    protected void dataUI()
    {

        TextView username_text_field =(TextView) findViewById(R.id.author_exam);;

        SharedPreferences fardin =  getApplicationContext().getSharedPreferences("UserRegisterPers", Context.MODE_PRIVATE);
        String name =fardin.getString("usernameLS","");


        username_text_field.setText(name);




    }

}