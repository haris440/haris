package kido.sparks.app.Fragments.Vaccine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import kido.sparks.app.Adapters.Vaccine_history_Adapter;
import kido.sparks.app.Adapters.ViewChildrenList_Adapter;
import kido.sparks.app.Model.VaccineHistory;
import kido.sparks.app.R;

public class VaccineHistory_Activity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    private ProgressBar mProgressBar;
    Vaccine_history_Adapter adapter;
    RecyclerView recyclerView;
    private List<VaccineHistory> list = new ArrayList<>();
    ImageView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_history);
        firebaseAuth =FirebaseAuth.getInstance();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new    Vaccine_history_Adapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        empty = findViewById(R.id.empty);
        Bundle bundle=getIntent().getExtras();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Parents").child(""+firebaseAuth.getCurrentUser().getUid()).child("Childs").child(""+bundle.get("child").toString()).child("vaccinehistory");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.exists())
              {
                  if (snapshot.exists())
                  {
                     empty.setVisibility(View.INVISIBLE);
                      for (DataSnapshot ds1 : snapshot.getChildren()) {
                         VaccineHistory childdata = ds1.getValue(VaccineHistory.class);
                          list.add(childdata);
                      }
                      mProgressBar.setVisibility(View.GONE);
                      adapter.setlist(list);


                  }
              }
              else
                  {
                      empty.setVisibility(View.VISIBLE);
                      mProgressBar.setVisibility(View.GONE);
                      Toast.makeText(VaccineHistory_Activity.this, "No Vaccine History Exists For This Child", Toast.LENGTH_LONG).show();
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}