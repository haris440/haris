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

import kido.sparks.app.Fragments.dashboard.ViewVideo;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;
import per.wsj.library.AndRatingBar;

public class ViewVideo2 extends AppCompatActivity implements OnPreparedListener {
    TextView txtname,txtdetail;
    //    AndExoPlayerView andExoPlayerView;
    AndRatingBar andRatingBar;
    Button btnrank;
    private VideoView videoView;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_video2);
        VideoModel activity_model=(  VideoModel) getIntent().getSerializableExtra("list");
        txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);
        bundle = getIntent().getExtras();
        andRatingBar=findViewById(R.id.ratebar);
        btnrank=findViewById(R.id.btnrank);
        andRatingBar.setOnRatingChangeListener(new AndRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(AndRatingBar ratingBar, float rating, boolean fromUser) {
                btnrank.setVisibility(View.INVISIBLE);
                andRatingBar.setVisibility(View.INVISIBLE);

//
                try {
                    DatabaseReference refactivity = FirebaseDatabase.getInstance().getReference().child("OurData").child("milestone").child("month"+bundle.get("month").toString()).child(""+bundle.get("pos").toString()).child("videolist").child(""+bundle.get("pos").toString());
                    HashMap hashMap=new HashMap();
                    hashMap.put("ranking",""+rating);
                    refactivity.updateChildren(hashMap);
                    Toast.makeText(ViewVideo2.this, "Ranking submitted", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {

                }

            }
        });
        //  andExoPlayerView.setSource(""+activity_model.getUrl());

        setupVideoView(activity_model.getUrl());
        txtname.setText(""+activity_model.getName());
        txtdetail.setText(""+activity_model.getDetail());

    }
    private void setupVideoView(String uurrll) {
        // Make sure to use the correct VideoView import
        videoView = (VideoView)findViewById(R.id.vd);
        videoView.setOnPreparedListener(ViewVideo2.this);

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