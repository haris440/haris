package kido.sparks.app.Fragments.Kitchen;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kido.sparks.app.Adapters.KitchenList_Adapter;
import kido.sparks.app.Adapters.ViewChildrenList_Adapter;
import kido.sparks.app.Model.Kitchen_Model;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.Parent_Panel.Parent_Home;
import kido.sparks.app.R;

public class KitchenFragment extends Fragment implements KitchenList_Adapter.OnrecylerListener{


    int whichmonth=1;
    RecyclerView recyclerView;
    ImageView empty;
    private ProgressBar mProgressBar;
    KitchenList_Adapter adapter;
    private List<Kitchen_Model> kitchen_model = new ArrayList<>();
    public static KitchenFragment newInstance() {
        return new KitchenFragment();
    }
    DatabaseReference refkitchen;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.kitchen_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        empty = view.findViewById(R.id.empty);
        adapter = new   KitchenList_Adapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        refkitchen= FirebaseDatabase.getInstance().getReference().child("OurData").child("kitchen").child("month"+whichmonth);
   GetKitchenItemList();
    }
    public void GetKitchenItemList() {

        refkitchen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               kitchen_model.clear();
                if (snapshot.exists())
                {
                empty.setVisibility(View.INVISIBLE);
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                       Kitchen_Model data = ds1.getValue(Kitchen_Model.class);
                        kitchen_model.add(data);
                    }
                   mProgressBar.setVisibility(View.GONE);
                   adapter.setlist(kitchen_model);


                }
                else {
                    kitchen_model.clear();
                    //Toast.makeText(getActivity(), "Add childrens", Toast.LENGTH_SHORT).show();
                  mProgressBar.setVisibility(View.GONE);
                 empty.setVisibility(View.VISIBLE);
                  adapter.setlist( kitchen_model);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void OnrecylerListener(int position, List<Kitchen_Model> viewChildren) {
        Intent intent=new Intent(getActivity(),ViewKitchenRecipe.class);
        intent.putExtra("list", kitchen_model.get(position));
        startActivity(intent);
//       try {
//           String url = ""+viewChildren.get(position).getUrl();
//           Intent i = new Intent(Intent.ACTION_VIEW);
//           i.setData(Uri.parse(url));
//           startActivity(i);
//       }
//       catch (Exception e)
//       {
//           Toast.makeText(getActivity(), "Error occurs", Toast.LENGTH_SHORT).show();
//       }
    }

    @Override
    public void OnrecylerListenerOpen(int position, List<Kitchen_Model> kitchen_model) {
        Intent intent=new Intent(getActivity(),ViewKitchenRecipe.class);
        intent.putExtra("list", kitchen_model.get(position));
        startActivity(intent);
//        try {
//            String url = ""+viewChildren.get(position).getUrl();
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(getActivity(), "Error occurs", Toast.LENGTH_SHORT).show();
//        }
    }
}