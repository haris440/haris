package kido.sparks.app.Fragments.dashboard;

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

import kido.sparks.app.Adapters.KitchenList_Adapter;
import kido.sparks.app.Adapters.Milestonevideo_Adapter;
import kido.sparks.app.Fragments.Activities.ViewActivity;
import kido.sparks.app.Model.Kitchen_Model;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;

public class ViewMilestonevideos extends AppCompatActivity implements Milestonevideo_Adapter.OnrecylerListener{
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
        setContentView(R.layout.activity_view_milestonevideos);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            month = bundle.get("month").toString();
            key = bundle.get("key").toString();
            ckey = bundle.get("ckey").toString();
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
        Log.e("ref", "" + ckey + "/" + month + "/" + key);
        refvideo = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" + ckey).child("milestones").child(month).child("milestoneslist").child(key).child("videolist");

        //  refvideo= FirebaseDatabase.getInstance().getReference().child("bachy");
        refvideo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(ViewMilestonevideos.this, "exist", Toast.LENGTH_SHORT).show();
                    empty.setVisibility(View.GONE);
                    videoModels.add(new VideoModel("1", "https://firebasestorage.googleapis.com/v0/b/kidosparkserver.appspot.com/o/activities%2F5-second-video-watch-the-milky-way-rise.mp4?alt=media&token=731cc215-7f8e-4e2c-b512-7784ec00c7fc", "1", "1"));
                    videoModels.add(new VideoModel("2", "https://firebasestorage.googleapis.com/v0/b/kidosparkserver.appspot.com/o/activities%2Fappreciate-nature-5-second-video.mp4?alt=media&token=9f20ffed-f133-4e7e-9977-503f67fc5071", "2", "2"));
                    videoModels.add(new VideoModel("3", "https://firebasestorage.googleapis.com/v0/b/kidosparkserver.appspot.com/o/activities%2Fplay-activities-for-babies-penfield-childrens-center.mp4?alt=media&token=424ec654-d51b-4de0-82a9-325f8f9a0846", "3", "3"));
                    adapter.setlist(videoModels);
                    mProgressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ViewMilestonevideos.this, "no video for this milestone", Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(ViewMilestonevideos.this, ViewVideo.class);
        intent.putExtra("list",vlist.get(position));
        startActivity(intent);
    }
}