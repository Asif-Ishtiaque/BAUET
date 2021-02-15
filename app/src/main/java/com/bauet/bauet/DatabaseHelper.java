package com.bauet.bauet;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {

    String DB_PATH = null;
    private static String DB_NAME = "routine_class.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }





////////////////////////////////////////////****** Data Pulling From Database*************////////////////////////////////////////////////////////\
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////
     /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Sunday Data Pull
    ////////////////////////////////
    public Cursor sunday_first_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("sundayfirst", null, null, null, null, null, null);
    }
      public Cursor sunday_second_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("sundaysecond", null, null, null, null, null, null);
    }
      public Cursor sunday_third_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("sundaythird", null, null, null, null, null, null);
    }
    //End
    //////////////////////


//Monday Data Pull
    ////////////////////////////////
    public Cursor monday_first_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("mondayfirst", null, null, null, null, null, null);
    }
      public Cursor monday_second_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("mondaysecond", null, null, null, null, null, null);
    }
      public Cursor monday_third_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("mondaythird", null, null, null, null, null, null);
    }
    //End
    //////////////////////

//Tuesday Data Pull
    ////////////////////////////////
    public Cursor tuesday_first_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("tuesdayfirst", null, null, null, null, null, null);
    }
      public Cursor  tuesday_second_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("tuesdaysecond", null, null, null, null, null, null);
    }
      public Cursor  tuesday_third_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("tuesdaythird", null, null, null, null, null, null);
    }
    public Cursor  tuesday_fourth_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("tuesdayfourth", null, null, null, null, null, null);
    }
    //End
    //////////////////////

//Wednesday Data Pull
    ////////////////////////////////
public Cursor wednesday_first_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
    return myDataBase.query("wednesdayfirst", null, null, null, null, null, null);
}
public Cursor wednesday_second_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
    return myDataBase.query("wednesdaysecond", null, null, null, null, null, null);
}
public Cursor wednesday_third_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
    return myDataBase.query("wednesdaythird", null, null, null, null, null, null);
}
public Cursor wednesday_fourth_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
    return myDataBase.query("wednesdayfourth", null, null, null, null, null, null);
}

    //End
    //////////////////////


    //Thursday Data Pull
    ////////////////////////////////
    public Cursor thursday_first_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("thursdayfirst", null, null, null, null, null, null);
    }

    public Cursor thursday_second_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("thursdaysecond", null, null, null, null, null, null);
    }

    public Cursor thursday_third_cursor(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("thursday_third", null, null, null, null, null, null);
    }

    //End
    //////////////////////



}
