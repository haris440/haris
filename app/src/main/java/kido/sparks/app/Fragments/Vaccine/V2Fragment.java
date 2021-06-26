package kido.sparks.app.Fragments.Vaccine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kido.sparks.app.Adapters.Counter_Adapter;
import kido.sparks.app.Adapters.Counter_Adapter2;
import kido.sparks.app.Adapters.Milestone_Adapter;
import kido.sparks.app.Adapters.V2_Adapter;
import kido.sparks.app.Fragments.dashboard.ViewMilestonevideos;
import kido.sparks.app.Model.Milestone;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;


public class V2Fragment extends Fragment implements V2_Adapter.OnrecylerListener, Counter_Adapter2.OnrecylerListenercounter {


    V2_Adapter milestone_adapter;
    Counter_Adapter2 counter_adapter;
    RecyclerView recyclerView, recyclerView2;

    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_v2d, container, false);
        return root;
    }

    Viewchild pp;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pp = (Viewchild) getActivity().getIntent().getSerializableExtra("list");
        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyler);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recylercounter);
        milestone_adapter = new V2_Adapter(this);
        counter_adapter = new Counter_Adapter2(this::OnrecylerListenercounter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(milestone_adapter);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setAdapter(counter_adapter);
        milestone_adapter.setlist(list);

        CalculateBabyAge();


    }

    public void GetMileStones(int which) {
        DatabaseReference refaddmilesstatus = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" + pp.getKey()).child("vaccine2").child("month" + which);

        refaddmilesstatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    SetAdapterdata(which);

//                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
//                    if (map.get("status") != null) {
//                        Toast.makeText(getActivity(), "" + which + "" + map.get("status"), Toast.LENGTH_SHORT).show();
//                        if (map.get("status").equals(false)) {
//                            Toast.makeText(getActivity(), "we have to get data", Toast.LENGTH_SHORT).show();
//                            DatabaseReference FROMData = FirebaseDatabase.getInstance().getReference().child("OurData").child("milestone").child("month1");
//                            DatabaseReference TOData = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" + pp.getKey()).child("milestones").child("month" + which).child("milestoneslist");
//                            CopyPasteDATA(FROMData, TOData, which);
//                        } else {
//                            Toast.makeText(getActivity(), "we already have data", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
                } else {
                    HashMap hash = new HashMap();
                    hash.put("status", true);
                    refaddmilesstatus.updateChildren(hash);
                    DatabaseReference FROMData = FirebaseDatabase.getInstance().getReference().child("OurData").child("vaccine2").child("month"+which);
                    DatabaseReference TOData = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" + pp.getKey()).child("vaccine2").child("month" + which);
                    CopyPasteDATA(FROMData, TOData, which);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public void OnrecylerListener(int position, List<Milestone> milestones) {
        Toast.makeText(getActivity(), "asd" + milestones.get(position).getName(), Toast.LENGTH_SHORT).show();
        Log.e("mm == ", "" + milestones.get(position).getName());
        Log.e("mm == ", "" + milestones.get(position).getExtra());
        Log.e("mm == ", "" + milestones.get(position).getText());
        Log.e("mm == ", "" + milestones.get(position).getUrl());
        Log.e("mm == ", "" + milestones.get(position).isStatus());
        Log.e("mm == ", "" + milestones.get(position).isUrlstatus());
        DatabaseReference refdata = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" + pp.getKey()).child("vaccine2").child("month" + which).child(""+ milestones.get(position).getKey());
        HashMap hashMap = new HashMap();
        if (milestones.get(position).isStatus()) {

            hashMap.put("status", false);
            milestones.get(position).setStatus(false);

        } else {

            hashMap.put("status", true);
            milestones.get(position).setStatus(true);

        }


        refdata.updateChildren(hashMap);


    }

    @Override
    public void OnrecylerListenerUrl(int position, List<Milestone> milestones) {
        Intent intent = new Intent(getActivity(), ViewV2detail.class);

        intent.putExtra("des", "" + milestones.get(position).getExtra());
        intent.putExtra("name", "" + milestones.get(position).getName());
        startActivity(intent);
//        try {
//            String url = ""+milestones.get(position).getUrl();
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(getActivity(), "Error occurs", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void OnrecylerListenercounter(int position) {
//             List<Milestone> list = new ArrayList<>();
        int pos = position + 1;
        if( position==0 )
        {
            counter_adapter.setlist(pos);
            GetMileStones(1);
            counter_adapter.setlist(position);

        }
        if( position==1 )
        {
            counter_adapter.setlist(pos);
            GetMileStones(2);
            counter_adapter.setlist(position);

        }
        if(  position==2)
        {
            counter_adapter.setlist(pos);
            GetMileStones(3);
            counter_adapter.setlist(position);

        }
        else if(position==3)
        {
            GetMileStones(9);
            counter_adapter.setlist(position);
        }
        else if(position==4)
        {
            GetMileStones(15);

            counter_adapter.setlist(position);
        }
        else{

        }

//           milestone_adapter.setlist(list);
//        Toast.makeText(getActivity(), "" + pos + "" + position, Toast.LENGTH_SHORT).show();
//        which = pos;
//        GetMileStones((int) which);
    }

    private void CopyPasteDATA(final DatabaseReference fromPath, final DatabaseReference toPath, int which) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
                            SetAdapterdata(which);


                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Milestone> list = new ArrayList<>();

    public void SetAdapterdata(int which) {

        DatabaseReference refdata = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" + pp.getKey()).child("vaccine2").child("month" + which);


        refdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    //empty.setVisibility(View.INVISIBLE);
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        Milestone childdata = ds1.getValue(Milestone.class);

                        list.add(childdata);
                    }
                    //mProgressBar.setVisibility(View.GONE);
                    milestone_adapter.setlist(list);


                } else {
                    list.clear();
                    //  Toast.makeText(Parent_Home.this, "Add childrens", Toast.LENGTH_SHORT).show();
//                    mProgressBar.setVisibility(View.GONE);
//                    empty.setVisibility(View.VISIBLE);
                    milestone_adapter.setlist(list);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void CalculateBabyAge() {
        long start = 0;
        int yearr = Integer.parseInt(pp.getAgeyear());
        int month = Integer.parseInt(pp.getAgemonth());
        int day = Integer.parseInt(pp.getAgeday());
        Calendar birthDay = new GregorianCalendar(yearr, month, day);
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        int yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int monthsDiff = today.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        int totaldays = today.get(Calendar.DAY_OF_YEAR) - birthDay.get(Calendar.DAY_OF_YEAR);
        long ageInMonths = yearsInBetween * 12 + monthsDiff;
        long age = yearsInBetween;

        if (ageInMonths == 0) {
            if (totaldays == 1)
                start = 1;
            else
                start = 1;


        } else {
            if (monthsDiff == 1) {

                start = 1;
            } else {

                start = ageInMonths;
            }


        }


        milestone_adapter.setlist(list);
        which = (int) start;
        if(which==1 || which==2 || which==3|| which==9 ||  which==15)
        {
            if(which==9)
            {
                counter_adapter.setlist(3);
                GetMileStones(9);
                recyclerView2.scrollToPosition((int) start - 1);
            }
            else if(which==15)
            {
                counter_adapter.setlist(4);
                GetMileStones(15);
                recyclerView2.scrollToPosition((int) start - 1);
            }
            else{
                counter_adapter.setlist((int) start - 1);
                GetMileStones(which);
                recyclerView2.scrollToPosition((int) start - 1);
        }


        }
        else{
            Toast.makeText(getActivity(), "No vaccination for this month", Toast.LENGTH_SHORT).show();

        }


    }

    int which;
    int total = 0;

}