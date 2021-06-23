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

import org.jetbrains.annotations.NotNull;

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
        Toast.makeText(this, "month", Toast.LENGTH_SHORT).show();
       DatabaseReference refvideo2 = FirebaseDatabase.getInstance().getReference().child("OurData").child("milestone").child("month"+month);
       refvideo2.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               if(snapshot.exists())
               {
                   videoModels.clear();
                   Toast.makeText(Single_Video_milestone_doc.this, "ccc="+""+snapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
                   Log.e("count",""+snapshot.getChildrenCount());
                   Log.e("count",""+month);
                   for (int i=1; i<=snapshot.getChildrenCount(); i++)
                   {
                       DatabaseReference     refvideo3 = FirebaseDatabase.getInstance().getReference().child("OurData").child("milestone").child("month"+month).child(""+i).child("videolist");
//
//
     refvideo3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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
                videoModels.clear();
            }
        });
                   }
               }
               else{
                   videoModels.clear();
               }
           }

           @Override
           public void onCancelled(@NonNull @NotNull DatabaseError error) {

           }
       });
//        refvideo = FirebaseDatabase.getInstance().getReference().child("OurData").child("milestone").child("month"+month).child(""+month).child("videolist");
//
//
//        refvideo.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                videoModels.clear();
//                if (snapshot.exists()) {
//                    Toast.makeText(Single_Video_milestone_doc.this, "exist", Toast.LENGTH_SHORT).show();
//
//                    mProgressBar.setVisibility(View.GONE);
//                    empty.setVisibility(View.INVISIBLE);
//                    for (DataSnapshot ds1 : snapshot.getChildren()) {
//                        VideoModel data = ds1.getValue(VideoModel.class);
//                        videoModels.add(data);
//                    }
//                    mProgressBar.setVisibility(View.GONE);
//                    adapter.setlist(videoModels);
//                } else {
//                    Toast.makeText(Single_Video_milestone_doc.this, "no video for this milestone", Toast.LENGTH_SHORT).show();
//                    videoModels.clear();
//                    mProgressBar.setVisibility(View.GONE);
//                    empty.setVisibility(View.VISIBLE);
//                    adapter.setlist(videoModels);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    @Override
    public void OnrecylerListener(int position, List<VideoModel> vlist) {
        Intent intent=new Intent(Single_Video_milestone_doc.this, ViewVideo.class);
        intent.putExtra("list",vlist.get(position));
        startActivity(intent);
    }



}