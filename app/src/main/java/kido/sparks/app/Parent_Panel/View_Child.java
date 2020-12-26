package kido.sparks.app.Parent_Panel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class View_Child extends AppCompatActivity {
TextView babyname,babyage;
ImageView babyimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       Viewchild pp=(Viewchild) getIntent().getSerializableExtra("list");
       if (pp.getBabygender().toString().equals("Female")){
           setContentView(R.layout.activity_view__child2);
        }
       else{
           setContentView(R.layout.activity_view__child);
       }
        babyname=findViewById(R.id.babyname);
        babyimg=findViewById(R.id.babyimg);
        babyage=findViewById(R.id.babyage);
        babyname.setText(""+pp.getBabyname());
  //      Toast.makeText(this, ""+ pp.getBabygender(), Toast.LENGTH_SHORT).show();
        Glide.with(this).asDrawable().centerCrop().load(""+pp.getBabyimg()).into(babyimg);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//        LocalDate start = LocalDate.parse("2/3/2017",formatter);
//        LocalDate end = LocalDate.parse("3/3/2017",formatter);
//
//        System.out.println(ChronoUnit.DAYS.between(start, end));
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        //Toast.makeText(this, ""+getDaysBetweenDates(""+pp.getBabyage(),""+day+"/"+month+"/"+year), Toast.LENGTH_SHORT).show();
        babyage.setText(""+getDaysBetweenDates("1/1/2019","1/1/2020"));
    }
    public static final String DATE_FORMAT = "d/M/yyyy";

    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
}