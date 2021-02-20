package kido.sparks.app.Fragments.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    AndExoPlayerView andExoPlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view);
        andExoPlayerView = findViewById(R.id.andExoPlayerView);

        Activity_Model activity_model=( Activity_Model) getIntent().getSerializableExtra("list");
        txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);

        if (activity_model.isUrlstatus())
        {
            andExoPlayerView.setSource(""+activity_model.getUrl());


            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getText());
        }
        else {
            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getText());

        }
    }

}