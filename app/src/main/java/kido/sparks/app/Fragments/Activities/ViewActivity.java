package kido.sparks.app.Fragments.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.potyvideo.library.AndExoPlayerView;

import kido.sparks.app.Fragments.Kitchen.ViewKitchenRecipe;
import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.Model.Kitchen_Model;
import kido.sparks.app.R;

public class ViewActivity extends AppCompatActivity {
    TextView txtname,txtdetail;
    Button btnlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        AndExoPlayerView andExoPlayerView = findViewById(R.id.andExoPlayerView);
        andExoPlayerView.setSource("https://firebasestorage.googleapis.com/v0/b/kidosparkserver.appspot.com/o/activities%2Fappreciate-nature-5-second-video.mp4?alt=media&token=9f20ffed-f133-4e7e-9977-503f67fc5071");
       Activity_Model activity_model=( Activity_Model) getIntent().getSerializableExtra("list");
        txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);
        btnlink=findViewById(R.id.btnlink);
        if (activity_model.isUrlstatus())
        {
            btnlink.setVisibility(View.VISIBLE);
            btnlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String url = ""+activity_model.getUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(ViewActivity.this, "Error occurs", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getText());
        }
        else {
            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getText());
            btnlink.setVisibility(View.GONE);
        }
    }

}