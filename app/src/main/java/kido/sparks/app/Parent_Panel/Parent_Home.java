package kido.sparks.app.Parent_Panel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.util.Map;

import kido.sparks.app.Adapters.ViewChildrenList_Adapter;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;
import kido.sparks.app.SignIn_Screens.SignIn;

public class Parent_Home extends AppCompatActivity implements ViewChildrenList_Adapter.OnrecylerListener {
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    DatabaseReference refdata;
    private ProgressBar mProgressBar;
    ViewChildrenList_Adapter adapter;
    private List<Viewchild> list = new ArrayList<>();
    ImageView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_parent__home);


        mAuth = FirebaseAuth.getInstance();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        adapter = new ViewChildrenList_Adapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        empty = findViewById(R.id.empty);

        refdata = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs");
        refdata.keepSynced(true);
        GetChildList();

    }

    public void GetChildList() {


        refdata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    empty.setVisibility(View.INVISIBLE);
                    for (DataSnapshot ds1 : snapshot.getChildren()) {
                        Log.e("asasa", "" + ds1.getValue());
                        Viewchild newComment = ds1.getValue(Viewchild.class);
                        Log.e("sss", "" + newComment.getBabygender() + newComment.getBabyweight());
                        list.add(newComment);
                    }
                    mProgressBar.setVisibility(View.GONE);
                    adapter.setlist(list);


                } else {
                    list.clear();
                    Toast.makeText(Parent_Home.this, "Add childens", Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    adapter.setlist(list);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void fun_logout(View view) {
        mAuth.signOut();
        Intent intent = new Intent(Parent_Home.this, SignIn.class);
        startActivity(intent);
        finish();
    }

    public void fun_add_children(View view) {
        Intent intent = new Intent(Parent_Home.this, Add_Children.class);
        startActivity(intent);
    }

    public void userinfo() {
        DatabaseReference refinfo = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid());
        refinfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                //   pharmacy_dashboard.setText(  "Welcome \n"+ map.get("shopname").toString()+"\nto pharmacy dashboard");
                //   pharmacy_info_sp.setname(""+map.get("shopname").toString());
                if (map.get("name") != null) {
                    //Glide.with(Pharmacy_Dashboard_Activity.this).asDrawable().centerCrop().load(map.get("imageurl").toString()).into(           pharmacyPerson);
                    Log.e("name", "" + map.get("name").toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void OnrecylerListener(int position, List<Viewchild> viewChildren) {
        Intent intent=new Intent(Parent_Home.this, Child_Panel_Activity.class);
        intent.putExtra("list", viewChildren.get(position));
        startActivity(intent);

    }

    @Override
    public void OnrecylerListenerEdit(int position, List<Viewchild> viewChildren) {
        Intent intent=new Intent(Parent_Home.this,Edit_Children.class);
        intent.putExtra("list", viewChildren.get(position));
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}