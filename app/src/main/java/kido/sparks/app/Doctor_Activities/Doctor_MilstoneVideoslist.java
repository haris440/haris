package kido.sparks.app.Doctor_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import kido.sparks.app.Adapters.Counter_Adapter_doc;
import kido.sparks.app.Adapters.Milestonevideo_Adapter;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;

public class Doctor_MilstoneVideoslist extends AppCompatActivity implements Counter_Adapter_doc.OnrecylerListenercounter {
    Counter_Adapter_doc counter_adapter;
    RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_milstone_videoslist);

        counter_adapter = new Counter_Adapter_doc(this::OnrecylerListenercounter);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setAdapter(counter_adapter);

    }

    @Override
    public void OnrecylerListenercounter(int position) {
        Intent intent = new Intent(Doctor_MilstoneVideoslist.this, Single_Video_milestone_doc.class);
        int pos=position+1;
        intent.putExtra("month","month"+pos);
        startActivity(intent);
    }

}