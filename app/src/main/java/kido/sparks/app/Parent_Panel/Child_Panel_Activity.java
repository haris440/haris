  package kido.sparks.app.Parent_Panel;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kido.sparks.app.Agorawebcam.activities.MainActivityagora;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class Child_Panel_Activity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    TextView babyname,babyage;
    ImageView babyimg,webcam;
    Viewchild pp;
    ConstraintLayout constraintLayout;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nav);
        constraintLayout=findViewById(R.id.constraintLayout);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view1);


        navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);

//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//           R.id.navigation_dashboard,     R.id.navigation_activities,R.id.navigation_vaccine,R.id.navigation_kitchen, R.id.navigation_reminder)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        pp=(Viewchild) getIntent().getSerializableExtra("list");
        babyname=findViewById(R.id.babyname);
        babyimg= findViewById(R.id.babyimg);
        webcam=findViewById(R.id.webcam);
        babyname.setText(""+pp.getBabyname());
        webcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Child_Panel_Activity.this, MainActivityagora.class));
            }
        });
        Glide.with(this).asDrawable().centerCrop().load(""+pp.getBabyimg()).into(babyimg);
        try {
            if (pp.getBabygender().contains("Male"))
            {
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
                navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            }
            else{
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.pink));
                navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
                navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.pink)));
            }
        }
        catch (Exception e)
        {

        }

//        CalculateBabyAge();
    }
    public static final String DATE_FORMAT = "dd/mm/yyyy";
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_viewchilds) {
finish();


        }
        else if (id == R.id.nav_rate) {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
        else if (id == R.id.nav_exit) {
            finish();

        }
        else if (id == R.id.nav_webcam) {
            startActivity(new Intent(Child_Panel_Activity.this, MainActivityagora.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void nvabtn(View view)
    {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);


    }

}