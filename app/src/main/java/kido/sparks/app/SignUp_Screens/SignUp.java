package kido.sparks.app.SignUp_Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kido.sparks.app.Check_Network_Class;
import kido.sparks.app.Parent_Panel.Parent_Home;
import kido.sparks.app.R;
import kido.sparks.app.SignIn_Screens.SignIn;

public class SignUp extends AppCompatActivity {
    EditText ed_name, ed_email, ed_pass;
    Check_Network_Class cn;
    private FirebaseAuth mAuth;
    private DatabaseReference refregisterparent;

    TextView btn;

    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        cn = new Check_Network_Class(this);
        ed_name = findViewById(R.id.edname);
        ed_email = findViewById(R.id.edemail);
        ed_pass = findViewById(R.id.edpass);
        btn = findViewById(R.id.button);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar) ;
        mAuth = FirebaseAuth.getInstance();
    }

    public void fun_signup(View view) {
        checkemptytext();
    }

    private void checkemptytext() {
        String name = ed_name.getText().toString().trim();
        String email = ed_email.getText().toString().trim();
        String pass = ed_pass.getText().toString().trim();
        if (name.isEmpty()) {
            ed_name.setError("Name is required");
            ed_name.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            ed_email.setError("Email is required");
            ed_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ed_email.setError("Please enter a valid email");
            ed_email.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            ed_pass.setError("Pass is required");
            ed_pass.requestFocus();
            return;
        }


        if (pass.length() < 6) {
            ed_pass.setError("Minimum lenght of password should be 6");
            ed_pass.requestFocus();
            return;
        }


        registerparent();
    }
    private void registerparent() {
        if (cn.checkNetworkConnection())
        {

            String email = ed_email.getText().toString().trim();
            String password = ed_pass.getText().toString().trim();
            mProgressBar.setVisibility(View.VISIBLE);

            btn.setVisibility(View.INVISIBLE);
            mAuth = FirebaseAuth.getInstance();


            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser user = task.getResult().getUser();
                        String firebaseid = user.getUid();
                        refregisterparent = FirebaseDatabase.getInstance().getReference().child("Parents").child(firebaseid);

                        saveparentinfo(firebaseid);
                    } else {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), "This email is  already registered", Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);


                            btn.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            mProgressBar.setVisibility(View.INVISIBLE);
                            btn.setVisibility(View.VISIBLE);

                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUp.this, ""+e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });


        }
        else {
            Toast.makeText(SignUp.this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
        }

    }
    private void saveparentinfo(String firebaseid) {
        String  name=ed_name.getText().toString().trim();
        String  email=ed_email.getText().toString().trim();
        String  pass=ed_pass.getText().toString().trim();

        final Map userInfo = new HashMap();
        userInfo.put("firebaseid", firebaseid);
        userInfo.put("name", name);
        userInfo.put("pass",pass);
        userInfo.put("email",email);
        userInfo.put("extra","0");



        refregisterparent.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Intent intent=new Intent(SignUp.this, Parent_Home.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, ""+e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });





    }
    public void funsignin(View view)
    {
        Intent intent=new Intent(SignUp.this, SignIn.class);
        startActivity(intent);
        finish();
    }


}