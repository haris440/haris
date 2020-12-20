package kido.sparks.app.Parent_Panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kido.sparks.app.Check_Network_Class;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class Add_Children extends AppCompatActivity {
    EditText cname, cweight;
    TextView c_age;
    Spinner genderspinner;
    private FirebaseAuth mAuth;
    Check_Network_Class cn;
    String[] gender = {"Male", "Female", "Other"};
 //   String[] age = {"Newly Born", "0 to 3 months", "3 to 6 months", "1 year", "2 years", "3 years", "4 years",};
    TextView btn;
    private DatabaseReference refaddchild;
ImageView babyimage;
    DatePickerDialog picker;


    private StorageReference image_path;
    private Uri resultUri;
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
//        agespinner = (Spinner) findViewById(R.id.spinner2);
        cweight = findViewById(R.id.c_weight);
        cname = findViewById(R.id.c_name);
        babyimage=findViewById(R.id.babyimage);
        c_age=findViewById(R.id.c_age);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(aa);

//        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, age);
//        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        agespinner.setAdapter(aa2);

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
        if (babyyear==""&&babyday==""&&babymonth=="")
        {
            Toast.makeText(this, "Select Baby BirthDate", Toast.LENGTH_LONG).show();
            return;
        }
        if (resultUri == null) {
            Toast.makeText(getApplicationContext(), "please upload image by selecting image icon ", Toast.LENGTH_LONG).show();
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
            hashMap.put("babyage", "" + c_age.getText());
            hashMap.put("agemonth", "" + c_age.getText());
            hashMap.put("ageyear", "" + c_age.getText());
            hashMap.put("ageday", "" + c_age.getText());
            hashMap.put("babyweight", "" + weight);
            hashMap.put("babygender", "" + genderspinner.getSelectedItem().toString());
            hashMap.put("babyimg", ""+ genderspinner.getSelectedItem().toString());
           refaddchild.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {
                   btn.setVisibility(View.VISIBLE);
         //          Toast.makeText(Add_Children.this, "Added Successfully , Want to Add More?", Toast.LENGTH_LONG).show();
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
uplaodimg();

    }
    String babyyear="";
    String babymonth="";
    String babyday="";
    public void getdate()
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(Add_Children.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        babyday= String.valueOf(dayOfMonth);
                        babymonth= String.valueOf(monthOfYear);
                        babyyear= String.valueOf(year);

                        c_age.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void opendatedailog(View view) {
        getdate();
    }
    public void funpickimage
            (View view) {
        if (cn.checkNetworkConnection())
        {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        }
        else
        {
            Toast.makeText(Add_Children.this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
        }
    }
    public void uplaodimg()
    {
        Bitmap finalBitmap = null;
        try {
            finalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time= System.currentTimeMillis();

        image_path = FirebaseStorage.getInstance().getReference().child(""+mAuth.getCurrentUser().getUid()).child(""+cname.getText().toString());;




        if ( resultUri != null) {


            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
            byte[] data2 = baos.toByteArray();

            UploadTask uploadTask = image_path.putBytes(data2);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        //    mProgressBar.setProgress(0);
                        }
                    }, 500);


                    image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String stringUrl =uri.toString();

                            Map newImage = new HashMap();
                            newImage.put("babyimg", ""+stringUrl);
                            refaddchild.updateChildren(newImage).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    Toast.makeText(Add_Children.this, "Baby Added", Toast.LENGTH_SHORT).show();

                               babyimage.setImageResource(0);
                               babyimage.setImageDrawable(getResources().getDrawable(R.drawable.babyblue));
                                  cname.setText("");
                                  cweight.setText("");
                                  c_age.setText("Select Baby Birth Date");

                                    btn.setVisibility(View.VISIBLE);


                                }
                            });




                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Add_Children.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    btn.setVisibility(View.VISIBLE);


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                //    mProgressBar.setProgress((int) progress);
                  //  mProgressBar.setVisibility(View.VISIBLE);

                }
            });








        } else {
            Toast.makeText(Add_Children.this, "No file selected", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
           babyimage.setImageURI(resultUri);

            Drawable dd=  babyimage.getDrawable();
            babyimage.invalidate();
            babyimage.setBackground(null);
            babyimage.setBackgroundResource(0);
            babyimage.setImageResource(0);
            babyimage.setImageBitmap(null);
            Glide.with(Add_Children.this).asDrawable().centerCrop().load(dd).into(babyimage);

        }

    }
}