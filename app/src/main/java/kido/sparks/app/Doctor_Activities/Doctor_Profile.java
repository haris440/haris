package kido.sparks.app.Doctor_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import kido.sparks.app.MainHome;
import kido.sparks.app.R;
import kido.sparks.app.SharedPrefrenceClasses.SharedPrefrence;
import kido.sparks.app.SplashScreen;

public class Doctor_Profile extends AppCompatActivity {
    SharedPrefrence sharedPrefrence;
    FirebaseAuth mAuth;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        mAuth = FirebaseAuth.getInstance();
        sharedPrefrence=new SharedPrefrence(this);
        textView=findViewById(R.id.textView5);
        Checkuser();
    }

    private void Checkuser() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Doctors").child(""+mAuth.getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Map<String,Object> map=(Map<String, Object>) snapshot.getValue();
textView.setText("Welcome\n"+map.get("name").toString());
                    Toast.makeText(Doctor_Profile.this, "founf", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Doctor_Profile.this, "no user found", Toast.LENGTH_SHORT).show();
                    sharedPrefrence.setypeofuser("0");
                    mAuth.signOut();
                    Intent intent = new Intent(Doctor_Profile.this, MainHome.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void activiyvideos(View view) {
        Intent intent = new Intent(Doctor_Profile.this,  Doctor_ActivitiesVideoslist.class);
        startActivity(intent);
    }

    public void milestonevideos(View view) {
        Intent intent = new Intent(Doctor_Profile.this, Doctor_MilstoneVideoslist.class);
        startActivity(intent);
    }

    public void fun_logout(View view) {
        sharedPrefrence.setypeofuser("0");
        mAuth.signOut();
        Intent intent = new Intent(Doctor_Profile.this, MainHome.class);
        startActivity(intent);
        finish();
    }
}