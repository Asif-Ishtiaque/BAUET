package com.bauet.bauet;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.database.Cursor;
import android.database.SQLException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import  cz.msebera.android.httpclient.Header;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.bauet.bauet.NotificationBasedClass.CHANNEL_ID_2;

public class MainActivity extends AppCompatActivity {

  final int REQUEST_CODE = 123;
    //f=first
    //s=second
    //t=third


    //Cursurs Here
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


    //Day Variable- (a)
    protected TextView today;
    protected String day;
    private BroadcastReceiver minuteUpdate;


    //Day Area
    public String day_of_Sunday = "Sunday";
    public String day_of_Monday = "Monday";
    public String day_of_Tuesday = "Tuesday";
    public String day_of_Wednesday = "Wednesday";
    public String day_of_Thursday = "Thursday";

    //Start Location Manager and Location Listener
    String LOCATION_PROVIDER = LocationManager.NETWORK_PROVIDER;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    long MIN_TIME = 5000;
    float MIN_DISTANCE = 1000;

    final String  WEATHER_URL ="http://api.openweathermap.org/data/2.5/weather";
    final String API_ID = "3300d64afa791936f04b45ea933922bc";

    TextView temp;
    TextView city;
    ImageView weather_icons;


    //User Name
    private String mDisplayName;

    private NotificationManagerCompat NotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.weather_location);
        weather_icons = findViewById(R.id.WeatherView);
         temp = findViewById(R.id.textView);

       //Pulling from Local Saved Data and Showing in main Menu
        setUpDisplayName();


       //Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        NotificationManager = NotificationManagerCompat.from(this);


        DatabaseLogic();


        //Sceondary Menu Opener
        //This Button Simply open the Class Scedule Activity
        ImageButton class_scedule_button = (ImageButton) findViewById(R.id.class_scedule_button);
        class_scedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassScedule_ActivityOpener();
            }
        });

      //Chat Menu Opener
        //This Button Simply open the Class Scedule Activity
        ImageButton chat_button = (ImageButton) findViewById(R.id.chat_system_button);
        chat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat_ActivityOpener();
            }
        });

        ImageButton exam_button = (ImageButton) findViewById(R.id.exam_system_button);
        exam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exam_activity();
            }
        });



    }

    //Class Scedule Activity Opener
    protected void ClassScedule_ActivityOpener() {
        Intent intent = new Intent(this, class_scedule_back.class);
        startActivity(intent);
    }

   //Class Scedule Activity Opener
    protected void Chat_ActivityOpener() {
        Intent intent = new Intent(this, Chat.class);
        startActivity(intent);
    }

    //Exam Menu Opener

    protected  void exam_activity()
    {
        Intent intent = new Intent(this, exam.class);
        startActivity(intent);
    }



    //get weather for current location
    protected void getWeatherLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.d("BAUET", "Enabled");
                String exLongitude = String.valueOf(location.getLongitude());
                String exLatitude = String.valueOf(location.getLatitude());

                Log.d("BAUET" , "Longitude : "  + exLongitude);
                Log.d("BAUET" , "LAtitide : "  + exLatitude);

                RequestParams params = new RequestParams();
                params.put("lat", exLatitude);
                params.put("lon", exLongitude);
                params.put("appid", API_ID);
                letsdosomenetworking(params);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }


            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Log.d("BAUET", "Disabled");
            }
        };




        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

         //Request Permission
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListener);
       }

///Display Name Setup
    private void setUpDisplayName()
    {
        TextView username_text_field =(TextView) findViewById(R.id.Welcome_user_name);

        SharedPreferences perfs =  getSharedPreferences(Register.CHAT_PREFS, MODE_PRIVATE);
        mDisplayName = perfs.getString(Register.DISPLAY_NAME_KEY,null);
        if(mDisplayName == null)
        {
            mDisplayName ="Anonymous";
        }
        else
        {
              username_text_field.setText(mDisplayName);
        }
    }






     //What happen after user accept or delclained permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE)
        {
            if(grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d("BAUET","Location  PERMISSION GRANTED");
                getWeatherLocation();
            }
            else
            { Log.d("BAUET","Location PERMISSION Decleined");

            }
        }
    }


  ///Request Params her
    private void letsdosomenetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode,Header[] headers,JSONObject response)
            {
                Log.d("BAUET","Sucess" +  response.toString());
                WeatherDataProcess weatherData = WeatherDataProcess.fromJSONFARDIN(response);
                updateWaatherUI(weatherData);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable fardinasif, JSONObject errorResponse) {
             Log.d("BAUET","Fail" +fardinasif.toString() );
             Log.d("BAUET","StatusCOde" +  statusCode);
            }
        });
    }


    private  void updateWaatherUI(WeatherDataProcess weather)
    {
     city.setText(weather.getCity());
     temp.setText(weather.getTemperature());
     int resourceID = getResources().getIdentifier(weather.getIconName(),"drawable",getPackageName());
     weather_icons.setImageResource(resourceID);

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
                Toast.makeText(MainActivity.this, "Minute is Changing!", Toast.LENGTH_SHORT).show();
                DatabaseLogic();

            }
        };
        registerReceiver(minuteUpdate,intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMinuteUpdate();
        getWeatherLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(minuteUpdate);
    }






    public  void notttt()
    {
        String title_noti = "Class Time Alarm";
        String dis_noti = "Next Class is : CSE-2215 ! ";
        Notification notification = new NotificationCompat.Builder(this, NotificationBasedClass.CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle(dis_noti)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        NotificationManager.notify(1,notification);
    }



     //Retrive from Databas and Showing Logic
    protected  void DatabaseLogic()
    {
        //Day Mechanics-(a)
        TimePull set_get_time = new TimePull();
        day = set_get_time.DayPull();
        today = (TextView) findViewById(R.id.Today_is);
        today.setText(day);
        //Day Showing Mechanics Done-(a)

        TextView clock_text =(TextView) findViewById(R.id.class_time);
        TextView class_text =(TextView) findViewById(R.id.textView8);
        TextView am_pm_text =(TextView) findViewById(R.id.AM_PM_IT);


        DatabaseHelper myDbHelper = new DatabaseHelper(MainActivity.this);
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
        Toast.makeText(MainActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
        //String Pull from String XML







      //Class Time Ato Update Scedule
      String first_class_up = "06:00 AM";
      String second_class_up = "08:45 AM";
      String third_class_up = "09:45 AM";
      String fourth_class_up = "11:30 AM";
      String fifth_class_up = "12:30 PM";

      // Eaxt Class
        String first_class_time = "08:15 AM";
        String second_class_time = "09:15 AM";
        String third_class_time = "10:15 AM";
        String fourth_class_time = "11:40 AM";
        String fifth_class_time = "12:40 PM";






        //Time Pull
        Calendar cal = Calendar.getInstance();

        String cur_fullTime;
        TimePull tpull  = new TimePull();
        cur_fullTime = tpull.TimePull();


         ///////******
        //Current Day Pulling
        TimePull day_of_day = new TimePull();
        String reallyDy = day_of_day.DayPull();
        //Current Day Pulling
        //////******






        ///////Sunday Class Time Update//////////
        ///////////////////////////////////////
        if(reallyDy.equals(day_of_Sunday))
        {
            //Sunday First Class
            if(cur_fullTime.equals(first_class_up))
            {

                sun_f = myDbHelper.sunday_first_cursor("sundayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Sunday First Class", Toast.LENGTH_SHORT).show();
                if (sun_f.moveToFirst()) {
                    clock_text.setText(sun_f.getString(0));
                    am_pm_text.setText(sun_f.getString(1));
                    class_text.setText(sun_f.getString(2));
                }
                notttt();
            }
             //Sunday Second Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                sun_s = myDbHelper.sunday_second_cursor("sundaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Sunday Second Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(sun_s.getString(0));
                    am_pm_text.setText(sun_s.getString(1));
                    class_text.setText(sun_s.getString(2));
                }
            }

             //Sunday Third Class
            if(cur_fullTime.equals(fifth_class_up))
            {
                sun_t = myDbHelper.sunday_third_cursor("sundaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Sunday Third Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(sun_t.getString(0));
                    am_pm_text.setText(sun_t.getString(1));
                    class_text.setText(sun_t.getString(2));
                }
            }



        }

        ///////Monday Class Time Update//////////
        ///////////////////////////////////////

        if(reallyDy.equals(day_of_Monday))
        {
            //Monday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                mon_f = myDbHelper.monday_first_cursor("mondayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Monday First Class", Toast.LENGTH_SHORT).show();
                if (mon_f.moveToFirst()) {
                    clock_text.setText(mon_f.getString(0));
                    am_pm_text.setText(mon_f.getString(1));
                    class_text.setText(mon_f.getString(2));
                }
            }

             //Monday Second Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                mon_s = myDbHelper.monday_second_cursor("mondaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Monday Second Class", Toast.LENGTH_SHORT).show();
                if (mon_s.moveToFirst()) {
                    clock_text.setText(mon_s.getString(0));
                    am_pm_text.setText(mon_s.getString(1));
                    class_text.setText(mon_s.getString(2));
                }
            }

            //Monday Third Class
            if(cur_fullTime.equals(fifth_class_up))
            {
                mon_t = myDbHelper.monday_third_cursor("mondaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Monday Third Class", Toast.LENGTH_SHORT).show();
                if (mon_t.moveToFirst()) {
                    clock_text.setText(mon_t.getString(0));
                    am_pm_text.setText(mon_t.getString(1));
                    class_text.setText(mon_t.getString(2));
                }
            }




        }






         //////Tuesday Class Time Update//////////
        ///////////////////////////////////////

        if(reallyDy.equals(day_of_Tuesday))
        {
            //Tuesday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                Tues_f = myDbHelper.tuesday_first_cursor("tuesdayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday First Class", Toast.LENGTH_SHORT).show();
                if (Tues_f.moveToFirst()) {
                    clock_text.setText(Tues_f.getString(0));
                    am_pm_text.setText(Tues_f.getString(1));
                    class_text.setText(Tues_f.getString(2));
                }
            }
           //Tuesday Second Class
            if(cur_fullTime.equals(second_class_up))
            {
                Tues_s = myDbHelper.tuesday_second_cursor("tuesdaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday Second Class", Toast.LENGTH_SHORT).show();
                if (Tues_s.moveToFirst()) {
                    clock_text.setText(Tues_s.getString(0));
                    am_pm_text.setText(Tues_s.getString(1));
                    class_text.setText(Tues_s.getString(2));
                }
            }
            //Tuesday Third Class
            if(cur_fullTime.equals(third_class_up))
            {
                Tues_t = myDbHelper.tuesday_third_cursor("tuesdaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday Third Class", Toast.LENGTH_SHORT).show();
                if (Tues_t.moveToFirst()) {
                    clock_text.setText(Tues_t.getString(0));
                    am_pm_text.setText(Tues_t.getString(1));
                    class_text.setText(Tues_t.getString(2));
                }
            }
            //Tuesday Fourth Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                Tues_fourth = myDbHelper.tuesday_fourth_cursor("tuesdayfourth", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Tuesday Fourth Class", Toast.LENGTH_SHORT).show();
                if (Tues_fourth.moveToFirst()) {
                    clock_text.setText(Tues_fourth.getString(0));
                    am_pm_text.setText(Tues_fourth.getString(1));
                    class_text.setText(Tues_fourth.getString(2));
                }
            }






        }

        //////Wednesday Class Time Update//////////
        ///////////////////////////////////////
        if(reallyDy.equals(day_of_Wednesday))
        {
            //Wednesday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                w_f = myDbHelper.wednesday_first_cursor("wednesdayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday First Class", Toast.LENGTH_SHORT).show();
                if (w_f.moveToFirst()) {
                    clock_text.setText(w_f.getString(0));
                    am_pm_text.setText(w_f.getString(1));
                    class_text.setText(w_f.getString(2));
                }
            }
             //Wednesday Second Class
            if(cur_fullTime.equals(third_class_up))
            {
                w_s = myDbHelper.wednesday_second_cursor("wednesdaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday Second Class ", Toast.LENGTH_SHORT).show();
                if (w_s.moveToFirst()) {
                    clock_text.setText(w_s.getString(0));
                    am_pm_text.setText(w_s.getString(1));
                    class_text.setText(w_s.getString(2));
                }
            }

            //Wednesday Third Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                w_t = myDbHelper.wednesday_third_cursor("wednesdaythird", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday Third Class ", Toast.LENGTH_SHORT).show();
                if (w_t.moveToFirst()) {
                    clock_text.setText(w_t.getString(0));
                    am_pm_text.setText(w_t.getString(1));
                    class_text.setText(w_t.getString(2));
                }
            }

            //Wednesday Fourth Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                w_fourth = myDbHelper.wednesday_fourth_cursor("wednesdayfourth", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Wednesday Fourth Class ", Toast.LENGTH_SHORT).show();
                if (w_fourth.moveToFirst()) {
                    clock_text.setText(w_fourth.getString(0));
                    am_pm_text.setText(w_fourth.getString(1));
                    class_text.setText(w_fourth.getString(2));
                }
            }



        }






















         //////////Thursday Class////////////
        ////////////////////////////////////
        if(reallyDy.equals(day_of_Thursday))
        {
            //Thursday First Class
            if(cur_fullTime.equals(first_class_up))
            {
                Th_f = myDbHelper.thursday_first_cursor("thursdayfirst", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Thursday First Class", Toast.LENGTH_SHORT).show();
                if (Th_f.moveToFirst()) {
                    clock_text.setText(Th_f.getString(0));
                    am_pm_text.setText(Th_f.getString(1));
                    class_text.setText(Th_f.getString(2));
                }
            }

            //Thursday Second Class
            if(cur_fullTime.equals(fourth_class_up))
            {
                Th_s = myDbHelper.thursday_second_cursor("thursdaysecond", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Thursday Second Class", Toast.LENGTH_SHORT).show();
                if (Th_s.moveToFirst()) {
                    clock_text.setText(Th_s.getString(0));
                    am_pm_text.setText(Th_s.getString(1));
                    class_text.setText(Th_s.getString(2));
                }
            }

             //ThursdayThird Class
            if(cur_fullTime.equals(fifth_class_up))
            {
                Th_t = myDbHelper.thursday_third_cursor("thursday_third", null, null, null, null, null, null);
                Toast.makeText(MainActivity.this, "Its Thursday Third Class", Toast.LENGTH_SHORT).show();
                if (Th_t.moveToFirst()) {
                    clock_text.setText(Th_t.getString(0));
                    am_pm_text.setText(Th_t.getString(1));
                    class_text.setText(Th_t.getString(2));
                }
            }



        }



    }



}