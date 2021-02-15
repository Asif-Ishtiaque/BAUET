package com.bauet.bauet;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    //Return Day
    public String DayPull()
    {
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        String[] splitDate = formattedTime.split(",");
      return splitDate[0].trim();
    }

    public String TimePull()
    {
        Calendar currentTime = Calendar.getInstance();
        SimpleDateFormat timepull = new SimpleDateFormat("hh:mm a");
        String time = timepull.format(currentTime.getTime());
        return  time;
    }


}
