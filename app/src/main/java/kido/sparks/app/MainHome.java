package kido.sparks.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import kido.sparks.app.Doctor_Activities.Doctor_Signin;
import kido.sparks.app.SignIn_Screens.SignIn;

public class MainHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
    }

    public void doclogin(View view) {
        Intent intent = new Intent(MainHome.this, Doctor_Signin.class);
        startActivity(intent);
        finish();
    }

    public void parentlogin(View view) {
        Intent intent = new Intent(MainHome.this, SignIn.class);
        startActivity(intent);
        finish();
    }

}