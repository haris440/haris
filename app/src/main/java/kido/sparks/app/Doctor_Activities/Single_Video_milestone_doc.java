package kido.sparks.app.Doctor_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kido.sparks.app.Adapters.Milestonevideo_Adapter;
import kido.sparks.app.Fragments.dashboard.ViewMilestonevideos;
import kido.sparks.app.Fragments.dashboard.ViewVideo;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;

public class Single_Video_milestone_doc extends AppCompatActivity implements  Milestonevideo_Adapter.OnrecylerListener {
    DatabaseReference refvideo;
    FirebaseAuth mAuth;
    String month, key, ckey;
    ImageView imgemp;
    RecyclerView recyclerView;
    Milestonevideo_Adapter adapter;
    private ProgressBar mProgressBar;
    ImageView empty;
    private List<VideoModel> videoModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_video_milestone_doc);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            month = bundle.get("month").toString();

        }
        checkref();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        empty = findViewById(R.id.empty);
        adapter = new Milestonevideo_Adapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    public void checkref() {
        mAuth = FirebaseAuth.getInstance();

        refvideo = FirebaseDatabase.getInstance().getReference().child("OurData").child("milestone").child(month).child("videolist");


        refvideo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                videoModels.clear();
                if (snapshot.exists()) {
                    Toast.makeText(Single_Video_milestone_doc.this, "exist", Toast.LENGTH_SHORT).show();

                    mProgressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.INVISIBLE);
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        VideoModel data = ds1.getValue(VideoModel.class);
                        videoModels.add(data);
                    }
                    mProgressBar.setVisibility(View.GONE);
                    adapter.setlist(videoModels);
                } else {
                    Toast.makeText(Single_Video_milestone_doc.this, "no video for this milestone", Toast.LENGTH_SHORT).show();
                    videoModels.clear();
                    mProgressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    adapter.setlist(videoModels);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void OnrecylerListener(int position, List<VideoModel> vlist) {
        Intent intent=new Intent(Single_Video_milestone_doc.this, ViewVideo.class);
        intent.putExtra("list",vlist.get(position));
        startActivity(intent);
    }



}