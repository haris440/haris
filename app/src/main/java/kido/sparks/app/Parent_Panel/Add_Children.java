package kido.sparks.app.Parent_Panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import kido.sparks.app.Check_Network_Class;
import kido.sparks.app.R;

public class Add_Children extends AppCompatActivity {
    EditText cname, cweight;
    TextView c_age;
    Spinner genderspinner;
    private FirebaseAuth mAuth;
    Check_Network_Class cn;
    String[] gender = {"Male", "Female"};
 //   String[] age = {"Newly Born", "0 to 3 months", "3 to 6 months", "1 year", "2 years", "3 years", "4 years",};
    TextView btn;
    private DatabaseReference refaddchild;
ImageView babyimage;
    DatePickerDialog picker;


    private StorageReference image_path;
    private Uri resultUri;
    private String strDateOfBirth;
    private String strNewDay;
    private String strNewMonth;

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
            hashMap.put("agemonth", "" + babymonth);
            hashMap.put("ageyear", "" + babyyear);
            hashMap.put("ageday", "" + babyday);
            hashMap.put("babyweight", "" + weight);
            hashMap.put("babygender", "" + genderspinner.getSelectedItem().toString());
            hashMap.put("babyimg", ""+ genderspinner.getSelectedItem().toString());
           refaddchild.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
               @Override
               public void onComplete(@NonNull Task task) {

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


void getlast3yeardate()
{

    final Calendar calendar = Calendar.getInstance();
    int   mYear = calendar.get(Calendar.YEAR);
 int   mDay = calendar.get(Calendar.DATE);
int    mMonth = calendar.get(Calendar.MONTH);

    DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        @SuppressLint("LongLogTag")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        strDateOfBirth = (month + 1) + "-" + dayOfMonth + "-" + year;
            babyday= String.valueOf(dayOfMonth);
            babymonth= String.valueOf(month);
            babyyear= String.valueOf(year);
            //********************** check and set date with append 0 at starting***************************
            if (dayOfMonth < 10) {
                strNewDay = "0" + dayOfMonth;
            } else {
                strNewDay = dayOfMonth + "";
            }
            if (month + 1 < 10) {
                strNewMonth = "0" + (month + 1);
            } else {
                strNewMonth = (month + 1) + "";
            }

            Log.e("strnewDay *****************", strNewDay + "");
            Log.e("strNewMonth *****************", strNewMonth + "");

            //    etDateOfBirth.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
            c_age.setText(strNewDay + " / " + strNewMonth + " / " + year);

            Log.e("strDateOfBirth *******************", strDateOfBirth + "");

        }
    }, mYear, mMonth, mDay);

    datePickerDialog.show();

    //*************** input date of birth must be greater than or equal to 18 ************************************

    Calendar mindate = Calendar.getInstance();
    mindate.set(Calendar.DAY_OF_MONTH, mDay);
    mindate.set(Calendar.MONTH, mMonth);
    mindate.set(Calendar.YEAR, mYear -3);

    Calendar maxDate2 = Calendar.getInstance();
    maxDate2.set(Calendar.DAY_OF_MONTH, mDay);
    maxDate2.set(Calendar.MONTH, mMonth);
    maxDate2.set(Calendar.YEAR, mYear );
    datePickerDialog.getDatePicker().setMinDate(mindate.getTimeInMillis());
    datePickerDialog.getDatePicker().setMaxDate(maxDate2.getTimeInMillis());
    //*************** input date of birth must be less than today date ************************************
    //   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

}


    public void opendatedailog(View view) {
      //  getdate();
     getlast3yeardate();
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
finish();

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
    public  void CalculateBabyAge(int year, int monthOfYear, int dayOfMonth)
    {

        int yearr= year;
        int month= monthOfYear;
        int day= dayOfMonth;
        Calendar birthDay = new GregorianCalendar(yearr, month, day);
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        int yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int monthsDiff = today.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        int totaldays = today.get(Calendar.DAY_OF_YEAR) - birthDay.get(Calendar.DAY_OF_YEAR);
        long ageInMonths = yearsInBetween*12 + monthsDiff;
        long age = yearsInBetween;

            Log.e("dsad","Number of months since James gosling born : " + monthsDiff);
            Log.e("dsad","Sir James Gosling's age : "+ totaldays);
            Log.e("dsad","Sir James Gosling's age : "+ ageInMonths);




    }
}