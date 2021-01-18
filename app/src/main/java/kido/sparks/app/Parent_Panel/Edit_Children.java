package kido.sparks.app.Parent_Panel;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.Map;

import kido.sparks.app.Check_Network_Class;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class Edit_Children extends AppCompatActivity {
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

   LinearLayout linearLayout;
    private StorageReference image_path;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewchild = (Viewchild) getIntent().getSerializableExtra("list");

        if (viewchild.getBabygender().toString().equals("Male")) {
            setContentView(R.layout.activity_edit__children);
        } else {
            setContentView(R.layout.activity_edit__children2);
        }

        linearLayout=findViewById(R.id.linearLayout);



        cn = new Check_Network_Class(this);
        mAuth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.textView3);
        genderspinner = (Spinner) findViewById(R.id.spinner);
        cweight = findViewById(R.id.c_weight);
        cname = findViewById(R.id.c_name);
        babyimage = findViewById(R.id.babyimage);
        c_age = findViewById(R.id.c_age);


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(aa);

        setuserinfo();

    }
    Viewchild viewchild;
    public  void setuserinfo()
{
     viewchild = (Viewchild) getIntent().getSerializableExtra("list");
    cname.setText(viewchild.getBabyname());
    cweight.setText(viewchild.getBabyweight());
    c_age.setText(viewchild.getBabyage());
    Glide.with(this).asDrawable().centerCrop().load(""+viewchild.getBabyimg()).into(babyimage);
      babyyear = ""+viewchild.getAgeyear();
      babymonth = ""+viewchild.getAgemonth();
      babyday = ""+viewchild.getAgeday();
    if (viewchild.getBabygender().toString().equals("Male")) {


        genderspinner.setSelection(0);


    } else {
        genderspinner.setSelection(1);




    }
}
    public void fun_edit(View view) {
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
        if (babyyear == "" && babyday == "" && babymonth == "") {
            Toast.makeText(this, "Select Baby BirthDate", Toast.LENGTH_LONG).show();
            return;
        }

        if (cn.checkNetworkConnection()) {
            btn.setVisibility(View.INVISIBLE);
            Toast.makeText(Edit_Children.this, "processing please wait ...", Toast.LENGTH_LONG).show();
            refaddchild = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child(""+viewchild.getKey());
            HashMap hashMap = new HashMap();
            hashMap.put("babyname", "" + name);
            hashMap.put("babyage", "" + c_age.getText());
            hashMap.put("agemonth", "" + babymonth);
            hashMap.put("ageyear", "" + babyyear);
            hashMap.put("ageday", "" + babyday);
            hashMap.put("babyweight", "" + weight );
            hashMap.put("babygender", "" + genderspinner.getSelectedItem().toString());
            refaddchild.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                        Toast.makeText(Edit_Children.this, "Updated Successfully ", Toast.LENGTH_LONG).show();
                    btn.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Edit_Children.this, "Failed", Toast.LENGTH_SHORT).show();

                    btn.setVisibility(View.VISIBLE);
                }
            });

        } else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
        }


    }

    String babyyear = "";
    String babymonth = "";
    String babyday = "";

    public void getdate() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(Edit_Children.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        babyday = String.valueOf(dayOfMonth);
                        babymonth = String.valueOf(monthOfYear);
                        babyyear = String.valueOf(year);

                        c_age.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    public void opendatedailog(View view) {
        getlast3yeardate();
    }  private String strDateOfBirth;
    private String strNewDay;
    private String strNewMonth;

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

        Calendar maxDate = Calendar.getInstance();
        maxDate.set(Calendar.DAY_OF_MONTH, mDay);
        maxDate.set(Calendar.MONTH, mMonth);
        maxDate.set(Calendar.YEAR, mYear -3);

        Calendar maxDate2 = Calendar.getInstance();
        maxDate2.set(Calendar.DAY_OF_MONTH, mDay);
        maxDate2.set(Calendar.MONTH, mMonth);
        maxDate2.set(Calendar.YEAR, mYear );
        datePickerDialog.getDatePicker().setMinDate(maxDate.getTimeInMillis());
        datePickerDialog.getDatePicker().setMaxDate(maxDate2.getTimeInMillis());
        //*************** input date of birth must be less than today date ************************************
        //   datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public void funpickimage
            (View view) {
        if (cn.checkNetworkConnection()) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(Edit_Children.this, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show();
        }
    }

    public void uplaodimg() {
        Bitmap finalBitmap = null;
        try {
            finalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis();

        image_path = FirebaseStorage.getInstance().getReference().child("" + mAuth.getCurrentUser().getUid()).child("" + cname.getText().toString());
        ;


        if (resultUri != null) {


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
                            final String stringUrl = uri.toString();

                            Map newImage = new HashMap();
                            newImage.put("babyimg", "" + stringUrl);
                            refaddchild.updateChildren(newImage).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    Toast.makeText(Edit_Children.this, "Image Updated ", Toast.LENGTH_SHORT).show();

                                    babyimage.setImageResource(0);
                                    babyimage.setImageDrawable(getResources().getDrawable(R.drawable.babyblue));
                                    cname.setText("");
                                    cweight.setText("");
                                    c_age.setText("Select Baby Birth Date");




                                }
                            });


                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Edit_Children.this, e.getMessage(), Toast.LENGTH_SHORT).show();


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
            Toast.makeText(Edit_Children.this, "No file selected", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultUri == null) {
            Toast.makeText(getApplicationContext(), "please upload image by selecting image icon ", Toast.LENGTH_LONG).show();
            return;
        }
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            babyimage.setImageURI(resultUri);

            Drawable dd = babyimage.getDrawable();
            babyimage.invalidate();
            babyimage.setBackground(null);
            babyimage.setBackgroundResource(0);
            babyimage.setImageResource(0);
            babyimage.setImageBitmap(null);
            Glide.with(Edit_Children.this).asDrawable().centerCrop().load(dd).into(babyimage);
            uplaodimg();
        }

    }

    public void fun_delete(View view) {
        DatabaseReference refdel = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child(""+viewchild.getKey());
        refdel.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Edit_Children.this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Edit_Children.this,Parent_Home.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Edit_Children.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void fun_cancel(View view) {
        Intent intent=new Intent(Edit_Children.this,Parent_Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent(Edit_Children.this,Parent_Home.class);

        startActivity(intent);
        finish();
    }
}