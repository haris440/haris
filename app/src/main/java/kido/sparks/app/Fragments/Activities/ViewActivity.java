package kido.sparks.app.Fragments.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;


import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.R;

public class ViewActivity extends AppCompatActivity implements OnPreparedListener {
    TextView txtname,txtdetail;
//    AndExoPlayerView andExoPlayerView;
private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view);
  //      andExoPlayerView = findViewById(R.id.andExoPlayerView);

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
        videoView.setOnPreparedListener(ViewActivity.this);

        //For now we just picked an arbitrary item to play
        videoView.setVideoURI(Uri.parse(""+uurrll));
    }

    @Override
    public void onPrepared() {

        //Starts the video playback as soon as it is ready
      //  videoView.start();
        Log.e("dd",""+videoView.getDuration());

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}