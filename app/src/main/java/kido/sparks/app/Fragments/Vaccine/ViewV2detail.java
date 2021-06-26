package kido.sparks.app.Fragments.Vaccine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import kido.sparks.app.R;

public class ViewV2detail extends AppCompatActivity {
    TextView txtname,txtdetail;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_v2detail);
        txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);
        bundle=getIntent().getExtras();
        txtname.setText(""+bundle.get("name").toString());
        txtdetail.setText(""+bundle.get("des").toString());
    }
}