package kido.sparks.app.SignIn_Screens;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import kido.sparks.app.R;

public class Forgot_Password_Dialog extends Dialog {
    EditText ed_email;
    TextView btn;
    public Forgot_Password_Dialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_forgotpassword);
   ed_email =findViewById(R.id.edrecoveryemail);
   btn=findViewById(R.id.btnrecovery);
   btn.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           String email = ed_email.getText().toString().trim();


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
           resetpassword(email);
       }
   });
    }
    private void resetpassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Email sent.");
                            Toast.makeText(getContext(), "Check Your Email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
