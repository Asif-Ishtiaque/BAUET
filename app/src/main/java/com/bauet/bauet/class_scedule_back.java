package com.bauet.bauet;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.database.Cursor;
import android.database.SQLException;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class class_scedule_back extends AppCompatActivity {


    //Day Area
    protected String day;
    public String day_of_Sunday = "Sunday";
    public String day_of_Monday = "Monday";
    public String day_of_Tuesday = "Tuesday";
    public String day_of_Wednesday = "Wednesday";
    public String day_of_Thursday = "Thursday";

    //f=first
    //s=second
    //t=third

    //Sunday Cursor Variable
    Cursor sun_f = null;
    Cursor sun_s = null;
    Cursor sun_t = null;

    //Monday Cursor Variable
    Cursor mon_f = null;
    Cursor mon_s = null;
    Cursor mon_t = null;

    //Tuesday Cursor Variable
    Cursor Tues_f = null;
    Cursor Tues_s = null;
    Cursor Tues_t = null;
    Cursor Tues_fourth = null;


    //Wednesday Cursor Variable
    Cursor w_f = null;
    Cursor w_s = null;
    Cursor w_t = null;
    Cursor w_fourth = null;

    //Thursday Cursor Variable
    Cursor Th_f = null;
    Cursor Th_s = null;
    Cursor Th_t = null;

    private   BroadcastReceiver minuteUpdate;


    String first_class_up = "06:00 AM";
    String second_class_up = "08:45 AM";
    String third_class_up = "09:45 AM";
    String fourth_class_up = "11:30 AM";
    String fifth_class_up = "12:30 PM";









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_scedule_back);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        A_A_A();

    }


    //Auto Update of Scedule
    public void startMinuteUpdate()
    {


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        minuteUpdate = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //Diagonistic Toast to Test that minute is actually Updating or not
                Toast.makeText(class_scedule_back.this, "Minute is Changing!", Toast.LENGTH_SHORT).show();

             A_A_A();

            }
        };
        registerReceiver(minuteUpdate,intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMinuteUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(minuteUpdate);
    }



  private void A_A_A()
  {
      Logic();
      Sunday_Class_Blue();
      Monday_Class_Blue();
      Tuesday_Class_Blue();
      Wenesday_Class_Blue();
      Thusday_Class_Blue();
  }








    public void Logic() {
        TimePull set_get_time = new TimePull();
        day = set_get_time.DayPull();

        //Database Initialization
        DatabaseHelper myDbHelper = new DatabaseHelper(class_scedule_back.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
        Toast.makeText(class_scedule_back.this, "Successfully Imported", Toast.LENGTH_SHORT).show();





        TextView today_name = (TextView) findViewById(R.id.today_name);
        TextView am_pm_text = (TextView) findViewById(R.id.AM_PM_IT);
        TextView class_name = (TextView) findViewById(R.id.class_name);
        TextView teacher_name = (TextView) findViewById(R.id.teacher_name);
        TextView am_pm = (TextView) findViewById(R.id.am_pm);
        TextView clockText = (TextView) findViewById(R.id.class_time);
        ImageView teacher_gender = (ImageView) findViewById(R.id.teacher_gender);


        //Time Pull
        Calendar cal = Calendar.getInstance();
        String cur_fullTime;
        TimePull tpull = new TimePull();
        cur_fullTime = tpull.TimePull();


        ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******

        //BlueField_FirstCLass
        TextView MCLOCK_FA_BLUE = (TextView)findViewById(R.id.blue_first_clock_time);
        TextView MAMPM_FA_BLUE = (TextView)findViewById(R.id.blue_first_clock_am_pm);
        ImageView MTEACHERGENDER_FA_BLUE=(ImageView)findViewById(R.id.blue_teacher_gender);
        TextView MTEACHER_FA_BLUE = (TextView)findViewById(R.id.blue_teacher_name);
        TextView MCLASSNAME_FA_BLUE = (TextView)findViewById(R.id.blue_class_is);


        //greenField_Second
        TextView MCLOCK_SEC_green = (TextView)findViewById(R.id.green_first_clock_time);
        TextView MAMPM_SEC_green = (TextView)findViewById(R.id.green_first_clock_am_pm);
        ImageView MTEACHERGENDER_SEC_green=(ImageView)findViewById(R.id.green_teacher_gender);
        TextView MTEACHER_SEC_green = (TextView)findViewById(R.id.green_teacher_name);
        TextView MCLASSNAME_SEC_green = (TextView)findViewById(R.id.green_class_is);

        //orangeField_Third
        TextView MCLOCK_THI_orange = (TextView)findViewById(R.id.orange_first_clock_time);
        TextView MAMPM_THI_orange = (TextView)findViewById(R.id.orange_first_clock_am_pm);
        ImageView MTEACHERGENDER_THI_orange=(ImageView)findViewById(R.id.orange_teacher_gender);
        TextView MTEACHER_THI_orange = (TextView)findViewById(R.id.orange_teacher_name);
        TextView MCLASSNAME_THI_orange = (TextView)findViewById(R.id.orange_class_is);

        //yellowField_Fourth
        TextView MCLOCK_FO_yellow = (TextView)findViewById(R.id.yellow_first_clock_time);
        TextView MAMPM_FO_yellow = (TextView)findViewById(R.id.yellow_first_clock_am_pm);
        ImageView MTEACHERGENDER_FO_yellow=(ImageView)findViewById(R.id.yellow_teacher_gender);
        TextView MTEACHER_FO_yellow = (TextView)findViewById(R.id.yellow_teacher_name);
        TextView MCLASSNAME_FO_yellow = (TextView)findViewById(R.id.yellow_class_is);







        ///////Sunday Class Time Update//////////
        ///////////////////////////////////////
        if (reallyDy.equals(day_of_Sunday)) {
            //Sunday First Class

            sun_f = myDbHelper.sunday_first_cursor("sundayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Sunday First Class", Toast.LENGTH_SHORT).show();
            if (sun_f.moveToFirst()) {
                teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                today_name.setText(reallyDy);
                clockText.setText(sun_f.getString(0));
                am_pm.setText(sun_f.getString(1));
                class_name.setText(sun_f.getString(2));
                teacher_name.setText(sun_f.getString(3));

            }
            //Sunday Second Class
            if (cur_fullTime.equals(fourth_class_up)) {
                sun_s = myDbHelper.sunday_second_cursor("sundaysecond", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Sunday Second Class", Toast.LENGTH_SHORT).show();
                if (sun_s.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(sun_s.getString(0));
                    am_pm.setText(sun_s.getString(1));
                    class_name.setText(sun_s.getString(2));
                    teacher_name.setText(sun_s.getString(3));
                }
            }

            //Sunday Third Class
            if (cur_fullTime.equals(fifth_class_up)) {
                sun_t = myDbHelper.sunday_third_cursor("sundaythird", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Sunday Third Class", Toast.LENGTH_SHORT).show();
                if (sun_t.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(sun_t.getString(0));
                    am_pm.setText(sun_t.getString(1));
                    class_name.setText(sun_t.getString(2));
                    teacher_name.setText(sun_t.getString(3));
                }
            }


        }

        ///////Monday Class Time Update//////////
        ///////////////////////////////////////

        if (reallyDy.equals(day_of_Monday)) {
            //Monday First Class

            mon_f = myDbHelper.monday_first_cursor("mondayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Monday First Class", Toast.LENGTH_SHORT).show();

            if (mon_f.moveToFirst()) {
                teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                today_name.setText(reallyDy);
                clockText.setText(mon_f.getString(0));
                am_pm.setText(mon_f.getString(1));
                class_name.setText(mon_f.getString(2));
                teacher_name.setText(mon_f.getString(3));
            }

            //Monday Second Class
            if (cur_fullTime.equals(fourth_class_up)) {
                mon_s = myDbHelper.monday_second_cursor("mondaysecond", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Monday Second Class", Toast.LENGTH_SHORT).show();
                if (mon_s.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_mam);
                    today_name.setText(reallyDy);
                    clockText.setText(mon_s.getString(0));
                    am_pm.setText(mon_s.getString(1));
                    class_name.setText(mon_s.getString(2));
                    teacher_name.setText(mon_s.getString(3));
                }
            }

            //Monday Third Class
            if (cur_fullTime.equals(fifth_class_up)) {
                mon_t = myDbHelper.monday_third_cursor("mondaythird", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Monday Third Class", Toast.LENGTH_SHORT).show();
                if (mon_t.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(mon_t.getString(0));
                    am_pm.setText(mon_t.getString(1));
                    class_name.setText(mon_t.getString(2));
                    teacher_name.setText(mon_t.getString(3));
                }
            }


        }


        //////Tuesday Class Time Update//////////
        ///////////////////////////////////////

        if (reallyDy.equals(day_of_Tuesday)) {

            Tues_f = myDbHelper.tuesday_first_cursor("tuesdayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday First Class", Toast.LENGTH_SHORT).show();
            if (Tues_f.moveToFirst()) {
                teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                today_name.setText(reallyDy);
                clockText.setText(Tues_f.getString(0));
                am_pm.setText(Tues_f.getString(1));
                class_name.setText(Tues_f.getString(2));
                teacher_name.setText(Tues_f.getString(3));
            }

            //Tuesday Second Class
            if (cur_fullTime.equals(second_class_up)) {
                Tues_s = myDbHelper.tuesday_second_cursor("tuesdaysecond", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Tuesday Second Class", Toast.LENGTH_SHORT).show();
                if (Tues_s.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_mam);
                    today_name.setText(reallyDy);
                    clockText.setText(Tues_s.getString(0));
                    am_pm.setText(Tues_s.getString(1));
                    class_name.setText(Tues_s.getString(2));
                    teacher_name.setText(Tues_s.getString(3));
                }
            }
            //Tuesday Third Class
            if (cur_fullTime.equals(third_class_up)) {
                Tues_t = myDbHelper.tuesday_third_cursor("tuesdaythird", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Tuesday Third Class", Toast.LENGTH_SHORT).show();
                if (Tues_t.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_mam);
                    today_name.setText(reallyDy);
                    clockText.setText(Tues_t.getString(0));
                    am_pm.setText(Tues_t.getString(1));
                    class_name.setText(Tues_t.getString(2));
                    teacher_name.setText(Tues_t.getString(3));
                }
            }
            //Tuesday Fourth Class
            if (cur_fullTime.equals(fourth_class_up)) {
                Tues_fourth = myDbHelper.tuesday_fourth_cursor("tuesdayfourth", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Tuesday Fourth Class", Toast.LENGTH_SHORT).show();
                if (Tues_fourth.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(Tues_fourth.getString(0));
                    am_pm.setText(Tues_fourth.getString(1));
                    class_name.setText(Tues_fourth.getString(2));
                    teacher_name.setText(Tues_fourth.getString(3));
                }
            }


        }

        //////Wednesday Class Time Update//////////
        ///////////////////////////////////////
        if (reallyDy.equals(day_of_Wednesday)) {
            //Wednesday First Class

            w_f = myDbHelper.wednesday_first_cursor("wednesdayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Wednesday First Class", Toast.LENGTH_SHORT).show();
            if ( w_f.moveToFirst()) {
                teacher_gender.setImageResource(R.drawable.ic_teacher_mam);
                today_name.setText(reallyDy);
                clockText.setText(w_f.getString(0));
                am_pm.setText(w_f.getString(1));
                class_name.setText(w_f.getString(2));
                teacher_name.setText(w_f.getString(3));

            }
            //Wednesday Second Class
            if (cur_fullTime.equals(third_class_up)) {
                w_s = myDbHelper.wednesday_second_cursor("wednesdaysecond", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Wednesday Second Class ", Toast.LENGTH_SHORT).show();
                if (w_s.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(w_s.getString(0));
                    am_pm.setText(w_s.getString(1));
                    class_name.setText(w_s.getString(2));
                    teacher_name.setText(w_s.getString(3));
                }
            }

            //Wednesday Third Class
            if (cur_fullTime.equals(fourth_class_up)) {
                w_t = myDbHelper.wednesday_third_cursor("wednesdaythird", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Wednesday Third Class ", Toast.LENGTH_SHORT).show();
                if (w_t.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_mam);
                    today_name.setText(reallyDy);
                    clockText.setText(w_t.getString(0));
                    am_pm.setText(w_t.getString(1));
                    class_name.setText(w_t.getString(2));
                    teacher_name.setText(w_t.getString(3));
                }
            }

            //Wednesday Fourth Class
            if (cur_fullTime.equals(fourth_class_up)) {
                w_fourth = myDbHelper.wednesday_fourth_cursor("wednesdayfourth", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Wednesday Fourth Class ", Toast.LENGTH_SHORT).show();
                if (w_fourth.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(w_fourth.getString(0));
                    am_pm.setText(w_fourth.getString(1));
                    class_name.setText(w_fourth.getString(2));
                    teacher_name.setText(w_fourth.getString(3));
                }
            }


        }

        //////////Thursday Class////////////
        ////////////////////////////////////
        if (reallyDy.equals(day_of_Thursday)) {
            //Thursday First Class

            Th_f = myDbHelper.thursday_first_cursor("thursdayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Thursday First Class", Toast.LENGTH_SHORT).show();
            if (Th_f.moveToFirst()) {
                teacher_gender.setImageResource(R.drawable.ic_teacher_mam);
                today_name.setText(reallyDy);
                clockText.setText(Th_f.getString(0));
                am_pm.setText(Th_f.getString(1));
                class_name.setText(Th_f.getString(2));
                teacher_name.setText(Th_f.getString(3));

            }

            //Thursday Second Class
            if (cur_fullTime.equals(fourth_class_up)) {
                Th_s = myDbHelper.thursday_second_cursor("thursdaysecond", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Thursday Second Class", Toast.LENGTH_SHORT).show();
                if (Th_s.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(Th_s.getString(0));
                    am_pm.setText(Th_s.getString(1));
                    class_name.setText(Th_s.getString(2));
                    teacher_name.setText(Th_s.getString(3));
                }
            }

            //ThursdayThird Class
            if (cur_fullTime.equals(fifth_class_up)) {
                Th_t = myDbHelper.thursday_third_cursor("thursday_third", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Thursday Third Class", Toast.LENGTH_SHORT).show();
                if (Th_t.moveToFirst()) {
                    teacher_gender.setImageResource(R.drawable.ic_teacher_male);
                    today_name.setText(reallyDy);
                    clockText.setText(Th_t.getString(0));
                    am_pm.setText(Th_t.getString(1));
                    class_name.setText(Th_t.getString(2));
                    teacher_name.setText(Th_t.getString(3));
                }
            }


        }

    }
    //ROUTINE DATA BINDING
    public  void Sunday_Class_Blue () {

     //Sunday Cursor Variable
     Cursor M_sun_f = null;
     Cursor M_sun_s = null;
     Cursor M_sun_t = null;




     //BlueField_FirstCLass
     TextView MCLOCK_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_time);
     TextView MAMPM_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_am_pm);
     ImageView MTEACHERGENDER_FA_BLUE = (ImageView) findViewById(R.id.blue_teacher_gender);
     TextView MTEACHER_FA_BLUE = (TextView) findViewById(R.id.blue_teacher_name);
     TextView MCLASSNAME_FA_BLUE = (TextView) findViewById(R.id.blue_class_is);
     //greenField_Second
     TextView MCLOCK_SEC_green = (TextView) findViewById(R.id.green_first_clock_time);
     TextView MAMPM_SEC_green = (TextView) findViewById(R.id.green_first_clock_am_pm);
     ImageView MTEACHERGENDER_SEC_green = (ImageView) findViewById(R.id.green_teacher_gender);
     TextView MTEACHER_SEC_green = (TextView) findViewById(R.id.green_teacher_name);
     TextView MCLASSNAME_SEC_green = (TextView) findViewById(R.id.green_class_is);

     //orangeField_Third
     TextView MCLOCK_THI_orange = (TextView) findViewById(R.id.orange_first_clock_time);
     TextView MAMPM_THI_orange = (TextView) findViewById(R.id.orange_first_clock_am_pm);
     ImageView MTEACHERGENDER_THI_orange = (ImageView) findViewById(R.id.orange_teacher_gender);
     TextView MTEACHER_THI_orange = (TextView) findViewById(R.id.orange_teacher_name);
     TextView MCLASSNAME_THI_orange = (TextView) findViewById(R.id.orange_class_is);

     //yellowField_Fourth
     TextView MCLOCK_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_time);
     TextView MAMPM_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_am_pm);
     ImageView MTEACHERGENDER_FO_yellow = (ImageView) findViewById(R.id.yellow_teacher_gender);
     TextView MTEACHER_FO_yellow = (TextView) findViewById(R.id.yellow_teacher_name);
     TextView MCLASSNAME_FO_yellow = (TextView) findViewById(R.id.yellow_class_is);


     Calendar cal = Calendar.getInstance();
     String cur_fullTime;
     TimePull tpull = new TimePull();
     cur_fullTime = tpull.TimePull();


     ///////******
     //Current Day Pulling
     TimePull day_of_day = new TimePull();
     String reallyDy = day_of_day.DayPull();
     //Current Day Pulling
     //////******


     DatabaseHelper myDbHelper = new DatabaseHelper(class_scedule_back.this);
     try {
         myDbHelper.createDataBase();
     } catch (IOException ioe) {
         throw new Error("Unable to create database");
     }
     try {
         myDbHelper.openDataBase();
     } catch (SQLException sqle) {
         throw sqle;
     }


     if (reallyDy.equals(day_of_Sunday)) {
         //Sunday First Class

         M_sun_f = myDbHelper.sunday_first_cursor("sundayfirst", null, null, null, null, null, null);
         Toast.makeText(class_scedule_back.this, "Its Sunday First Class", Toast.LENGTH_SHORT).show();
         if (M_sun_f.moveToFirst()) {
             //FIRST CLASS_SUNDAY
             MCLOCK_FA_BLUE.setText(M_sun_f.getString(0));
             MAMPM_FA_BLUE.setText(M_sun_f.getString(1));
             MTEACHERGENDER_FA_BLUE.setImageResource(R.drawable.ic_teacher_male);
             MCLASSNAME_FA_BLUE.setText(M_sun_f.getString(2));
             MTEACHER_FA_BLUE.setText(M_sun_f.getString(3));

         }
         //Second Class

         M_sun_s = myDbHelper.sunday_second_cursor("sundaysecond", null, null, null, null, null, null);
         Toast.makeText(class_scedule_back.this, "Its Sunday First Class", Toast.LENGTH_SHORT).show();
         if (M_sun_s.moveToFirst()) {
             //FIRST CLASS_SUNDAY
             MCLOCK_SEC_green.setText(M_sun_s.getString(0));
             MAMPM_SEC_green.setText(M_sun_s.getString(1));
             MTEACHERGENDER_SEC_green.setImageResource(R.drawable.ic_teacher_male);
             MCLASSNAME_SEC_green.setText(M_sun_s.getString(2));
             MTEACHER_SEC_green.setText(M_sun_s.getString(3));

         }

         M_sun_t = myDbHelper.sunday_second_cursor("sundaysecond", null, null, null, null, null, null);
         Toast.makeText(class_scedule_back.this, "Its Sunday First Class", Toast.LENGTH_SHORT).show();
         if (M_sun_t.moveToFirst()) {
             //FIRST CLASS_SUNDAY
             MCLOCK_THI_orange.setText(M_sun_t.getString(0));
             MAMPM_THI_orange.setText(M_sun_t.getString(1));
             MTEACHERGENDER_THI_orange.setImageResource(R.drawable.ic_teacher_male);
             MCLASSNAME_THI_orange.setText(M_sun_t.getString(2));
             MTEACHER_THI_orange.setText(M_sun_t.getString(3));

         }


     }


 }
    public  void Monday_Class_Blue () {
        //Monday Cursor Variable
        Cursor M_mon_f = null;
        Cursor M_mon_s = null;
        Cursor M_mon_t = null;


        //BlueField_FirstCLass
        TextView MCLOCK_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_time);
        TextView MAMPM_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_am_pm);
        ImageView MTEACHERGENDER_FA_BLUE = (ImageView) findViewById(R.id.blue_teacher_gender);
        TextView MTEACHER_FA_BLUE = (TextView) findViewById(R.id.blue_teacher_name);
        TextView MCLASSNAME_FA_BLUE = (TextView) findViewById(R.id.blue_class_is);
        //greenField_Second
        TextView MCLOCK_SEC_green = (TextView) findViewById(R.id.green_first_clock_time);
        TextView MAMPM_SEC_green = (TextView) findViewById(R.id.green_first_clock_am_pm);
        ImageView MTEACHERGENDER_SEC_green = (ImageView) findViewById(R.id.green_teacher_gender);
        TextView MTEACHER_SEC_green = (TextView) findViewById(R.id.green_teacher_name);
        TextView MCLASSNAME_SEC_green = (TextView) findViewById(R.id.green_class_is);

        //orangeField_Third
        TextView MCLOCK_THI_orange = (TextView) findViewById(R.id.orange_first_clock_time);
        TextView MAMPM_THI_orange = (TextView) findViewById(R.id.orange_first_clock_am_pm);
        ImageView MTEACHERGENDER_THI_orange = (ImageView) findViewById(R.id.orange_teacher_gender);
        TextView MTEACHER_THI_orange = (TextView) findViewById(R.id.orange_teacher_name);
        TextView MCLASSNAME_THI_orange = (TextView) findViewById(R.id.orange_class_is);

        //yellowField_Fourth
        TextView MCLOCK_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_time);
        TextView MAMPM_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_am_pm);
        ImageView MTEACHERGENDER_FO_yellow = (ImageView) findViewById(R.id.yellow_teacher_gender);
        TextView MTEACHER_FO_yellow = (TextView) findViewById(R.id.yellow_teacher_name);
        TextView MCLASSNAME_FO_yellow = (TextView) findViewById(R.id.yellow_class_is);


        Calendar cal = Calendar.getInstance();
        String cur_fullTime;
        TimePull tpull = new TimePull();
        cur_fullTime = tpull.TimePull();


        ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******


        DatabaseHelper myDbHelper = new DatabaseHelper(class_scedule_back.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }


        if (reallyDy.equals(day_of_Monday)) {

            M_mon_f = myDbHelper.monday_first_cursor("mondayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Monday First Class", Toast.LENGTH_SHORT).show();

            if (M_mon_f.moveToFirst()) {

                MCLOCK_FA_BLUE.setText(M_mon_f.getString(0));
                MAMPM_FA_BLUE.setText(M_mon_f.getString(1));
                MTEACHERGENDER_FA_BLUE.setImageResource(R.drawable.ic_teacher_male);
                MCLASSNAME_FA_BLUE.setText(M_mon_f.getString(2));
                MTEACHER_FA_BLUE.setText(M_mon_f.getString(3));


            }

            //Monday Second Class
                M_mon_s = myDbHelper.monday_second_cursor("mondaysecond", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Monday Second Class", Toast.LENGTH_SHORT).show();
                if (M_mon_s.moveToFirst()) {
                    MCLOCK_SEC_green.setText(M_mon_s.getString(0));
                    MAMPM_SEC_green.setText(M_mon_s.getString(1));
                    MTEACHERGENDER_SEC_green.setImageResource(R.drawable.ic_teacher_mam);
                    MCLASSNAME_SEC_green.setText(M_mon_s.getString(2));
                    MTEACHER_SEC_green.setText(M_mon_s.getString(3));

                }

            //Monday Third Class

                M_mon_t = myDbHelper.monday_third_cursor("mondaythird", null, null, null, null, null, null);
                Toast.makeText(class_scedule_back.this, "Its Monday Third Class", Toast.LENGTH_SHORT).show();
                if (M_mon_t.moveToFirst()) {

                    MCLOCK_THI_orange.setText(M_mon_t.getString(0));
                    MAMPM_THI_orange.setText(M_mon_t.getString(1));
                    MTEACHERGENDER_THI_orange.setImageResource(R.drawable.ic_teacher_mam);
                    MCLASSNAME_THI_orange.setText(M_mon_t.getString(2));
                    MTEACHER_THI_orange.setText(M_mon_t.getString(3));


                }


        }


    }
    public  void Tuesday_Class_Blue () {
        //Monday Cursor Variable
        //Tuesday Cursor Variable
        Cursor M_Tues_f = null;
        Cursor M_Tues_s = null;
        Cursor M_Tues_t = null;
        Cursor M_Tues_fourth = null;


        //BlueField_FirstCLass
        TextView MCLOCK_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_time);
        TextView MAMPM_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_am_pm);
        ImageView MTEACHERGENDER_FA_BLUE = (ImageView) findViewById(R.id.blue_teacher_gender);
        TextView MTEACHER_FA_BLUE = (TextView) findViewById(R.id.blue_teacher_name);
        TextView MCLASSNAME_FA_BLUE = (TextView) findViewById(R.id.blue_class_is);
        //greenField_Second
        TextView MCLOCK_SEC_green = (TextView) findViewById(R.id.green_first_clock_time);
        TextView MAMPM_SEC_green = (TextView) findViewById(R.id.green_first_clock_am_pm);
        ImageView MTEACHERGENDER_SEC_green = (ImageView) findViewById(R.id.green_teacher_gender);
        TextView MTEACHER_SEC_green = (TextView) findViewById(R.id.green_teacher_name);
        TextView MCLASSNAME_SEC_green = (TextView) findViewById(R.id.green_class_is);

        //orangeField_Third
        TextView MCLOCK_THI_orange = (TextView) findViewById(R.id.orange_first_clock_time);
        TextView MAMPM_THI_orange = (TextView) findViewById(R.id.orange_first_clock_am_pm);
        ImageView MTEACHERGENDER_THI_orange = (ImageView) findViewById(R.id.orange_teacher_gender);
        TextView MTEACHER_THI_orange = (TextView) findViewById(R.id.orange_teacher_name);
        TextView MCLASSNAME_THI_orange = (TextView) findViewById(R.id.orange_class_is);

        //yellowField_Fourth
        TextView MCLOCK_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_time);
        TextView MAMPM_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_am_pm);
        ImageView MTEACHERGENDER_FO_yellow = (ImageView) findViewById(R.id.yellow_teacher_gender);
        TextView MTEACHER_FO_yellow = (TextView) findViewById(R.id.yellow_teacher_name);
        TextView MCLASSNAME_FO_yellow = (TextView) findViewById(R.id.yellow_class_is);


        Calendar cal = Calendar.getInstance();
        String cur_fullTime;
        TimePull tpull = new TimePull();
        cur_fullTime = tpull.TimePull();


        ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******


        DatabaseHelper myDbHelper = new DatabaseHelper(class_scedule_back.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }


        if (reallyDy.equals(day_of_Tuesday)) {

            M_Tues_f = myDbHelper.tuesday_first_cursor("tuesdayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday First Class", Toast.LENGTH_SHORT).show();

            if (M_Tues_f.moveToFirst()) {

                MCLOCK_FA_BLUE.setText(M_Tues_f.getString(0));
                MAMPM_FA_BLUE.setText(M_Tues_f.getString(1));
                MTEACHERGENDER_FA_BLUE.setImageResource(R.drawable.ic_teacher_male);
                MCLASSNAME_FA_BLUE.setText(M_Tues_f.getString(2));
                MTEACHER_FA_BLUE.setText(M_Tues_f.getString(3));


            }

            //Tuesday Second Class
            M_Tues_s = myDbHelper.tuesday_second_cursor("tuesdaysecond", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Second Class", Toast.LENGTH_SHORT).show();
            if (M_Tues_s.moveToFirst()) {
                MCLOCK_SEC_green.setText(M_Tues_s.getString(0));
                MAMPM_SEC_green.setText(M_Tues_s.getString(1));
                MTEACHERGENDER_SEC_green.setImageResource(R.drawable.ic_teacher_mam);
                MCLASSNAME_SEC_green.setText(M_Tues_s.getString(2));
                MTEACHER_SEC_green.setText(M_Tues_s.getString(3));

            }

            //Tuesday Third Class

            M_Tues_t = myDbHelper.tuesday_third_cursor("teusdaythird", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Third Class", Toast.LENGTH_SHORT).show();
            if (M_Tues_t.moveToFirst()) {

                MCLOCK_THI_orange.setText(M_Tues_t.getString(0));
                MAMPM_THI_orange.setText(M_Tues_t.getString(1));
                MTEACHERGENDER_THI_orange.setImageResource(R.drawable.ic_teacher_mam);
                MCLASSNAME_THI_orange.setText(M_Tues_t.getString(2));
                MTEACHER_THI_orange.setText(M_Tues_t.getString(3));


            }

            //Tuesday Fourth Class
            M_Tues_fourth = myDbHelper.tuesday_fourth_cursor("tuesdayfourth", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Fourth Class", Toast.LENGTH_SHORT).show();
            if (M_Tues_fourth.moveToFirst()) {

                MCLOCK_FO_yellow.setText(M_Tues_fourth.getString(0));
                MAMPM_FO_yellow.setText(M_Tues_fourth.getString(1));
                MTEACHERGENDER_FO_yellow.setImageResource(R.drawable.ic_teacher_male);
                MCLASSNAME_FO_yellow.setText(M_Tues_fourth.getString(2));
                MTEACHER_FO_yellow.setText(M_Tues_fourth.getString(3));


            }


        }


    }
    public void Wenesday_Class_Blue () {


        //Wednesday Cursor Variable

        Cursor M_w_f = null;
        Cursor M_w_s = null;
        Cursor M_w_t = null;
        Cursor M_w_fourth = null;


        //BlueField_FirstCLass
        TextView MCLOCK_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_time);
        TextView MAMPM_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_am_pm);
        ImageView MTEACHERGENDER_FA_BLUE = (ImageView) findViewById(R.id.blue_teacher_gender);
        TextView MTEACHER_FA_BLUE = (TextView) findViewById(R.id.blue_teacher_name);
        TextView MCLASSNAME_FA_BLUE = (TextView) findViewById(R.id.blue_class_is);
        //greenField_Second
        TextView MCLOCK_SEC_green = (TextView) findViewById(R.id.green_first_clock_time);
        TextView MAMPM_SEC_green = (TextView) findViewById(R.id.green_first_clock_am_pm);
        ImageView MTEACHERGENDER_SEC_green = (ImageView) findViewById(R.id.green_teacher_gender);
        TextView MTEACHER_SEC_green = (TextView) findViewById(R.id.green_teacher_name);
        TextView MCLASSNAME_SEC_green = (TextView) findViewById(R.id.green_class_is);

        //orangeField_Third
        TextView MCLOCK_THI_orange = (TextView) findViewById(R.id.orange_first_clock_time);
        TextView MAMPM_THI_orange = (TextView) findViewById(R.id.orange_first_clock_am_pm);
        ImageView MTEACHERGENDER_THI_orange = (ImageView) findViewById(R.id.orange_teacher_gender);
        TextView MTEACHER_THI_orange = (TextView) findViewById(R.id.orange_teacher_name);
        TextView MCLASSNAME_THI_orange = (TextView) findViewById(R.id.orange_class_is);

        //yellowField_Fourth
        TextView MCLOCK_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_time);
        TextView MAMPM_FO_yellow = (TextView) findViewById(R.id.yellow_first_clock_am_pm);
        ImageView MTEACHERGENDER_FO_yellow = (ImageView) findViewById(R.id.yellow_teacher_gender);
        TextView MTEACHER_FO_yellow = (TextView) findViewById(R.id.yellow_teacher_name);
        TextView MCLASSNAME_FO_yellow = (TextView) findViewById(R.id.yellow_class_is);


        Calendar cal = Calendar.getInstance();
        String cur_fullTime;
        TimePull tpull = new TimePull();
        cur_fullTime = tpull.TimePull();


        ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******


        DatabaseHelper myDbHelper = new DatabaseHelper(class_scedule_back.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }


        if (reallyDy.equals(day_of_Wednesday)) {

            M_w_f = myDbHelper.wednesday_first_cursor("wednesdayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday First Class", Toast.LENGTH_SHORT).show();

            if (M_w_f.moveToFirst()) {

                MCLOCK_FA_BLUE.setText(M_w_f.getString(0));
                MAMPM_FA_BLUE.setText(M_w_f.getString(1));
                MTEACHERGENDER_FA_BLUE.setImageResource(R.drawable.ic_teacher_mam);
                MCLASSNAME_FA_BLUE.setText(M_w_f.getString(2));
                MTEACHER_FA_BLUE.setText(M_w_f.getString(3));


            }

            //Tuesday Second Class
            M_w_s = myDbHelper.wednesday_second_cursor("wednesdaysecond", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Second Class", Toast.LENGTH_SHORT).show();
            if (M_w_s.moveToFirst()) {
                MCLOCK_SEC_green.setText(M_w_s.getString(0));
                MAMPM_SEC_green.setText(M_w_s.getString(1));
                MTEACHERGENDER_SEC_green.setImageResource(R.drawable.ic_teacher_male);
                MCLASSNAME_SEC_green.setText(M_w_s.getString(2));
                MTEACHER_SEC_green.setText(M_w_s.getString(3));

            }

            //Tuesday Third Class

            M_w_t = myDbHelper.wednesday_third_cursor("wednesdaythird", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Third Class", Toast.LENGTH_SHORT).show();
            if (M_w_t.moveToFirst()) {

                MCLOCK_THI_orange.setText(M_w_t.getString(0));
                MAMPM_THI_orange.setText(M_w_t.getString(1));
                MTEACHERGENDER_THI_orange.setImageResource(R.drawable.ic_teacher_mam);
                MCLASSNAME_THI_orange.setText(M_w_t.getString(2));
                MTEACHER_THI_orange.setText(M_w_t.getString(3));


            }

            //Tuesday Fourth Class
            M_w_fourth = myDbHelper.wednesday_fourth_cursor("wednesdayfourth", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Fourth Class", Toast.LENGTH_SHORT).show();
            if (M_w_fourth.moveToFirst()) {

                MCLOCK_FO_yellow.setText(M_w_fourth.getString(0));
                MAMPM_FO_yellow.setText(M_w_fourth.getString(1));
                MTEACHERGENDER_FO_yellow.setImageResource(R.drawable.ic_teacher_male);
                MCLASSNAME_FO_yellow.setText(M_w_fourth.getString(2));
                MTEACHER_FO_yellow.setText(M_w_fourth.getString(3));


            }


        }


    }
    public  void Thusday_Class_Blue () {


        Cursor M_Th_f = null;
        Cursor M_Th_s = null;
        Cursor M_Th_t = null;


        //BlueField_FirstCLass
        TextView MCLOCK_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_time);
        TextView MAMPM_FA_BLUE = (TextView) findViewById(R.id.blue_first_clock_am_pm);
        ImageView MTEACHERGENDER_FA_BLUE = (ImageView) findViewById(R.id.blue_teacher_gender);
        TextView MTEACHER_FA_BLUE = (TextView) findViewById(R.id.blue_teacher_name);
        TextView MCLASSNAME_FA_BLUE = (TextView) findViewById(R.id.blue_class_is);
        //greenField_Second
        TextView MCLOCK_SEC_green = (TextView) findViewById(R.id.green_first_clock_time);
        TextView MAMPM_SEC_green = (TextView) findViewById(R.id.green_first_clock_am_pm);
        ImageView MTEACHERGENDER_SEC_green = (ImageView) findViewById(R.id.green_teacher_gender);
        TextView MTEACHER_SEC_green = (TextView) findViewById(R.id.green_teacher_name);
        TextView MCLASSNAME_SEC_green = (TextView) findViewById(R.id.green_class_is);

        //orangeField_Third
        TextView MCLOCK_THI_orange = (TextView) findViewById(R.id.orange_first_clock_time);
        TextView MAMPM_THI_orange = (TextView) findViewById(R.id.orange_first_clock_am_pm);
        ImageView MTEACHERGENDER_THI_orange = (ImageView) findViewById(R.id.orange_teacher_gender);
        TextView MTEACHER_THI_orange = (TextView) findViewById(R.id.orange_teacher_name);
        TextView MCLASSNAME_THI_orange = (TextView) findViewById(R.id.orange_class_is);




        Calendar cal = Calendar.getInstance();
        String cur_fullTime;
        TimePull tpull = new TimePull();
        cur_fullTime = tpull.TimePull();


        ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******


        DatabaseHelper myDbHelper = new DatabaseHelper(class_scedule_back.this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }


        if (reallyDy.equals(day_of_Thursday)) {

            M_Th_f = myDbHelper.thursday_first_cursor("thursdayfirst", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday First Class", Toast.LENGTH_SHORT).show();

            if (M_Th_f.moveToFirst()) {

                MCLOCK_FA_BLUE.setText(M_Th_f.getString(0));
                MAMPM_FA_BLUE.setText(M_Th_f.getString(1));
                MTEACHERGENDER_FA_BLUE.setImageResource(R.drawable.ic_teacher_mam);
                MCLASSNAME_FA_BLUE.setText(M_Th_f.getString(2));
                MTEACHER_FA_BLUE.setText(M_Th_f.getString(3));


            }

            //Tuesday Second Class
            M_Th_s = myDbHelper.thursday_second_cursor("thursdaysecond", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Second Class", Toast.LENGTH_SHORT).show();
            if (M_Th_s.moveToFirst()) {
                MCLOCK_SEC_green.setText(M_Th_s.getString(0));
                MAMPM_SEC_green.setText(M_Th_s.getString(1));
                MTEACHERGENDER_SEC_green.setImageResource(R.drawable.ic_teacher_male);
                MCLASSNAME_SEC_green.setText(M_Th_s.getString(2));
                MTEACHER_SEC_green.setText(M_Th_s.getString(3));

            }

            //Tuesday Third Class

            M_Th_t = myDbHelper.thursday_third_cursor("thursday_third", null, null, null, null, null, null);
            Toast.makeText(class_scedule_back.this, "Its Tuesday Third Class", Toast.LENGTH_SHORT).show();
            if ( M_Th_t.moveToFirst()) {

                MCLOCK_THI_orange.setText( M_Th_t.getString(0));
                MAMPM_THI_orange.setText( M_Th_t.getString(1));
                MTEACHERGENDER_THI_orange.setImageResource(R.drawable.ic_teacher_mam);
                MCLASSNAME_THI_orange.setText( M_Th_t.getString(2));
                MTEACHER_THI_orange.setText( M_Th_t.getString(3));


            }



        }


    }






















}