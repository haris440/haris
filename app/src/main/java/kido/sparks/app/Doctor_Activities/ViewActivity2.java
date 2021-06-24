package kido.sparks.app.Doctor_Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import kido.sparks.app.Fragments.Activities.ViewActivity;
import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.R;
import per.wsj.library.AndRatingBar;

public class ViewActivity2 extends AppCompatActivity  implements OnPreparedListener {
    TextView txtname,txtdetail;
    AndRatingBar andRatingBar;
    Button btnrank;
    Bundle bundle;
    //    AndExoPlayerView andExoPlayerView;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view2);
        //      andExoPlayerView = findViewById(R.id.andExoPlayerView);
        bundle = getIntent().getExtras();

        andRatingBar=findViewById(R.id.ratebar);
        btnrank=findViewById(R.id.btnrank);
        andRatingBar.setOnRatingChangeListener(new AndRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(AndRatingBar ratingBar, float rating, boolean fromUser) {
                btnrank.setVisibility(View.INVISIBLE);
                andRatingBar.setVisibility(View.INVISIBLE);
               DatabaseReference refactivity = FirebaseDatabase.getInstance().getReference().child("OurData").child("activities").child("month"+bundle.get("month").toString()).child(""+bundle.get("pos").toString());
                HashMap hashMap=new HashMap();
                hashMap.put("ranking",""+rating);
                refactivity.updateChildren(hashMap);
                Toast.makeText(ViewActivity2.this, "Ranking submitted", Toast.LENGTH_SHORT).show();

            }
        });
        Activity_Model activity_model=( Activity_Model) getIntent().getSerializableExtra("list");
        txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);

        if (activity_model.isUrlstatus())
        {
            //  andExoPlayerView.setSource(""+activity_model.getUrl());

            setupVideoView(activity_model.getUrl());
            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getText());
        }
        else {
            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getText());

        }
    }
    private void setupVideoView(String uurrll) {
        // Make sure to use the correct VideoView import
        videoView = (VideoView)findViewById(R.id.vd);
        videoView.setOnPreparedListener(ViewActivity2.this);

        //For now we just picked an arbitrary item to play
        videoView.setVideoURI(Uri.parse(""+uurrll));
    }

    @Override
    public void onPrepared() {

        //Starts the video playback as soon as it is ready
        //  videoView.start();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onclickrank(View view) {
        btnrank.setVisibility(View.INVISIBLE);
        andRatingBar.setVisibility(View.VISIBLE);
    }
}