package kido.sparks.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kido.sparks.app.Model.Milestone;

public class Firestoretest extends AppCompatActivity {
   Map<String, Object> mental_obj;
   List<String> mental_list= new ArrayList<String>();


    Map<String, Object>     physical_obj;
    List<String>     physical_list= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestoretest);


    }
    public void ff1(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("milestone").document("1").collection("types")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("doc name",""+document.getData()) ;
                                if(document.getId().contains("mental"))
                                {
                                    mental_obj=document.getData();
                                    mental_list= (List<String>) mental_obj.get("milestone");
                                }
                                else{
                                   physical_obj=document.getData();
                                    physical_list= (List<String>) physical_obj.get("milestone");
                                }
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public void ff2(View view) {
        Log.e("mental list",""+mental_list);
        Log.e("mental list",""+mental_list.size());

    }

    public void ff3(View view) {
        Log.e("physical list",""+physical_list);
        Log.e("physical list",""+physical_list.size());
    }

    public void ff4(View view) {

    }
}