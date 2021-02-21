package kido.sparks.app.Fragments.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.VideoView;

import kido.sparks.app.Fragments.Activities.ViewActivity;
import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;

public class ViewVideo extends AppCompatActivity implements OnPreparedListener {
    TextView txtname,txtdetail;
    //    AndExoPlayerView andExoPlayerView;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_video);
        VideoModel activity_model=(  VideoModel) getIntent().getSerializableExtra("list");
        txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);


            //  andExoPlayerView.setSource(""+activity_model.getUrl());

            setupVideoView(activity_model.getUrl());
            txtname.setText(""+activity_model.getName());
            txtdetail.setText(""+activity_model.getDetail());

    }
    private void setupVideoView(String uurrll) {
        // Make sure to use the correct VideoView import
        videoView = (VideoView)findViewById(R.id.vd);
        videoView.setOnPreparedListener(ViewVideo.this);

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
}