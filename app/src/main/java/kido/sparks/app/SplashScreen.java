package kido.sparks.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import kido.sparks.app.Doctor_Activities.Doctor_Profile;
import kido.sparks.app.Parent_Panel.Parent_Home;
import kido.sparks.app.SharedPrefrenceClasses.SharedPrefrence;
import kido.sparks.app.SignIn_Screens.SignIn;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
    SharedPrefrence sharedPrefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_);
        //offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        sharedPrefrence=new SharedPrefrence(this);
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    if(sharedPrefrence.getypeofuser().toString().equals("1"))
                    {
                        Intent intent = new Intent(SplashScreen.this, Parent_Home.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(sharedPrefrence.getypeofuser().toString().equals("2"))
                    {
                        Intent intent = new Intent(SplashScreen.this, Doctor_Profile.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        sharedPrefrence.setypeofuser("0");
                        mAuth.signOut();
                        Intent intent = new Intent(SplashScreen.this, MainHome.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    sharedPrefrence.setypeofuser("0");
                    mAuth.signOut();
                    Intent intent = new Intent(SplashScreen.this, MainHome.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1000);

    }
}