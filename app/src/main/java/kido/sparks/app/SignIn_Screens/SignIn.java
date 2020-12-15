package kido.sparks.app.SignIn_Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import kido.sparks.app.Check_Network_Class;
import kido.sparks.app.Parent_Panel.Parent_Home;
import kido.sparks.app.R;
import kido.sparks.app.SignUp_Screens.SignUp;
import kido.sparks.app.SplashScreen;

public class SignIn extends AppCompatActivity {
    EditText ed_email, ed_pass;
    Check_Network_Class cn;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    TextView btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        cn = new Check_Network_Class(this);
        ed_email = findViewById(R.id.edemail);
        ed_pass = findViewById(R.id.edpass);
        btn = findViewById(R.id.button);
        mProgressBar=(ProgressBar)findViewById(R.id.progressBar) ;

        mAuth = FirebaseAuth.getInstance();
    }

    public void fun_Open_signup(View view) {
        Intent intent=new Intent(SignIn.this, SignUp.class);
        startActivity(intent);
        finish();
    }
    public void fun_signin(View view) {
        checkemptytext() ;
    }
    private void checkemptytext() {

        String email = ed_email.getText().toString().trim();
        String pass = ed_pass.getText().toString().trim();

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


        loginparent();
    }


      private void loginparent() {
        if (cn.checkNetworkConnection()) {

            String email = ed_email.getText().toString().trim();
            String password = ed_pass.getText().toString().trim();
            mProgressBar.setVisibility(View.VISIBLE);

            btn.setVisibility(View.INVISIBLE);
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        Intent intent=new Intent(SignIn.this, Parent_Home.class);
                        startActivity(intent);
                        finish();

                    } else {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

                }
            });


        } else {
            Toast.makeText(SignIn.this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
        }

    }
}