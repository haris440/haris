package kido.sparks.app.Parent_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class View_Child extends AppCompatActivity {
TextView babyname;
ImageView babyimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__child);
     //   List<Viewchild> myList = (ArrayList<Viewchild>) getIntent().getSerializableExtra("list");
        babyname=findViewById(R.id.babyname);
        babyimg=findViewById(R.id.babyimg);
       Viewchild pp=(Viewchild) getIntent().getSerializableExtra("list");
        babyname.setText(""+pp.getBabyname());
  //      Toast.makeText(this, ""+ pp.getBabygender(), Toast.LENGTH_SHORT).show();
        Glide.with(this).asDrawable().centerCrop().load(""+pp.getBabyimg()).into(babyimg);

    }
}