package kido.sparks.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;

import kido.sparks.app.Parent_Panel.Parent_Home;
import kido.sparks.app.SignIn_Screens.SignIn;
import kido.sparks.app.SignUp_Screens.SignUp;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth mAuth;
//vhnage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen_);
mAuth=FirebaseAuth.getInstance();
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    Intent intent=new Intent(SplashScreen.this, Parent_Home.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent=new Intent(SplashScreen.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }

            }
        },1000);

    }
}