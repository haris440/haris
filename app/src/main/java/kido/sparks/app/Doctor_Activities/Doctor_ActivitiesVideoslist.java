package kido.sparks.app.Doctor_Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import kido.sparks.app.Adapters.Counter_Adapter;
import kido.sparks.app.Adapters.Counter_Adapter_doc;
import kido.sparks.app.R;

public class Doctor_ActivitiesVideoslist extends AppCompatActivity implements Counter_Adapter_doc.OnrecylerListenercounter  {
    Counter_Adapter_doc counter_adapter;
    RecyclerView  recyclerView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(
                R.layout.activity_doctor_activities_videoslist);
        counter_adapter = new Counter_Adapter_doc(this::OnrecylerListenercounter);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setAdapter(counter_adapter);

    }

    @Override
    public void OnrecylerListenercounter(int position) {
        Intent intent = new Intent(Doctor_ActivitiesVideoslist.this, Single_Video_Activity_doc.class);
        int pos=position+1;
        intent.putExtra("month",""+pos);
        startActivity(intent);
    }
}