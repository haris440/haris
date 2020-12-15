package kido.sparks.app.Parent_Panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import kido.sparks.app.Check_Network_Class;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class Add_Children extends AppCompatActivity {
    EditText cname, cweight;
    Spinner genderspinner, agespinner;
    private FirebaseAuth mAuth;
    Check_Network_Class cn;
    String[] gender = {"Male", "Female", "Other"};
    String[] age = {"Newly Born", "0 to 3 months", "3 to 6 months", "1 year", "2 years", "3 years", "4 years",};
    TextView btn;
    private DatabaseReference refaddchild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add__children);
        cn = new Check_Network_Class(this);
        mAuth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.textView3);
        genderspinner = (Spinner) findViewById(R.id.spinner);
        agespinner = (Spinner) findViewById(R.id.spinner2);
        cweight = findViewById(R.id.c_weight);
        cname = findViewById(R.id.c_name);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(aa);

        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, age);
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agespinner.setAdapter(aa2);
        getdata();
    }

    public void fun_add(View view) {
        String name = cname.getText().toString().trim();
        String weight = cweight.getText().toString().trim();

        if (name.isEmpty()) {
            cname.setError("Name is required");
            cname.requestFocus();
            return;
        }
        if (weight.isEmpty()) {
            cweight.setError("Weight is required");
            cweight.requestFocus();
            return;
        }
        if (cn.checkNetworkConnection()) {
            btn.setVisibility(View.INVISIBLE);
            Toast.makeText(Add_Children.this, "processing please wait ...", Toast.LENGTH_LONG).show();
            refaddchild= FirebaseDatabase.getInstance().getReference().child("Parents").child(""+mAuth.getCurrentUser().getUid().toString()).child("Childs").push();
            HashMap hashMap = new HashMap();
            hashMap.put("key", "" + refaddchild.getKey().toString());
            hashMap.put("status", "0");
            hashMap.put("babyname", "" + name);
            hashMap.put("babyage", "" + agespinner.getSelectedItem().toString());
            hashMap.put("babyweight", "" + weight);
            hashMap.put("babygender", "" + genderspinner.getSelectedItem().toString());
           refaddchild.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {
                   btn.setVisibility(View.VISIBLE);
                   Toast.makeText(Add_Children.this, "Added Successfully , Want to Add More?", Toast.LENGTH_LONG).show();
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(Add_Children.this, "Failed", Toast.LENGTH_SHORT).show();
                   btn.setVisibility(View.VISIBLE);
               }
           });

        }
        else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
        }

    }
    public void getdata() {
        DatabaseReference refdata;
        refdata = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs");
        refdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {


                //    mProgressBar.setVisibility(View.GONE);


                    Viewchild newComment = snapshot.getValue(Viewchild.class);
                    String commentKey = snapshot.getKey();
                    Log.e("hjgk",""+newComment.getBabygender());
                 //   list.add(newComment);



                } else {
                    //Toast.makeText(Parent_Home.this, "Add childens", Toast.LENGTH_SHORT).show();
                  //  mProgressBar.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}