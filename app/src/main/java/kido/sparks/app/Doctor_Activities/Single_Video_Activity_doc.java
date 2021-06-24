package kido.sparks.app.Doctor_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import kido.sparks.app.Adapters.Activities_Adapter;
import kido.sparks.app.Fragments.Activities.ViewActivity;
import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class Single_Video_Activity_doc extends AppCompatActivity implements Activities_Adapter.OnrecylerListener {
    int whichmonth=1;
    RecyclerView recyclerView;
    ImageView empty;
    private ProgressBar mProgressBar;
    Activities_Adapter adapter;
    private List<Activity_Model> activity_model = new ArrayList<>();
    DatabaseReference refactivity;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_video_doc);

        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        empty = findViewById(R.id.empty);
        adapter = new Activities_Adapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        bundle = getIntent().getExtras();
     Calculatemonthe(bundle.get("month").toString());
    }

    public void Calculatemonthe(String month) {
        Toast.makeText(this, ""+month, Toast.LENGTH_SHORT).show();
        refactivity = FirebaseDatabase.getInstance().getReference().child("OurData").child("activities").child("month"+month);
        GetActivitiesItemList();
    }
    public void GetActivitiesItemList() {

        refactivity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activity_model.clear();
                if (snapshot.exists())
                {
                    empty.setVisibility(View.INVISIBLE);
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        Activity_Model data = ds1.getValue(Activity_Model.class);
                        activity_model.add(data);
                    }
                    mProgressBar.setVisibility(View.GONE);
                    adapter.setlist(activity_model);


                }
                else {
                    activity_model.clear();
                    mProgressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    adapter.setlist(activity_model);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void OnrecylerListener(int position, List<Activity_Model> viewChildren) {
        Intent intent=new Intent(Single_Video_Activity_doc.this, ViewActivity2.class);
        intent.putExtra("list",viewChildren.get(position));
        int pos=position+1;
        intent.putExtra("pos",""+pos);
        intent.putExtra("month",""+bundle.get("month").toString());
        startActivity(intent);
    }

    @Override
    public void OnrecylerListenerOpen(int position, List<Activity_Model> viewChildren) {
        Intent intent=new Intent(Single_Video_Activity_doc.this, ViewActivity2.class);
        intent.putExtra("list",viewChildren.get(position));
        int pos=position+1;
        intent.putExtra("pos",""+pos);
        intent.putExtra("month",""+bundle.get("month").toString());
        startActivity(intent);
    }
}