package kido.sparks.app.Fragments.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import kido.sparks.app.Adapters.Activities_Adapter;
import kido.sparks.app.Fragments.Kitchen.ViewKitchenRecipe;
import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.R;

public class ActivitiesFragment extends Fragment implements Activities_Adapter.OnrecylerListener {

    int whichmonth=1;
    RecyclerView recyclerView;
    ImageView empty;
    private ProgressBar mProgressBar;
    Activities_Adapter adapter;
    private List<Activity_Model> activity_model = new ArrayList<>();
    DatabaseReference refactivity;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        empty = view.findViewById(R.id.empty);
        adapter = new Activities_Adapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        refactivity = FirebaseDatabase.getInstance().getReference().child("OurData").child("activities").child("month"+whichmonth);
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
        Intent intent=new Intent(getActivity(), ViewActivity.class);
        intent.putExtra("list",viewChildren.get(position));
        startActivity(intent);
    }

    @Override
    public void OnrecylerListenerOpen(int position, List<Activity_Model> viewChildren) {
        Intent intent=new Intent(getActivity(), ViewActivity.class);
        intent.putExtra("list",viewChildren.get(position));
        startActivity(intent);
    }
}