package kido.sparks.app.Fragments.Vaccine;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class VaccineFragment extends Fragment {

    private FirebaseAuth mAuth;
    Viewchild pp;
CardView history,pending;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vaccine_fragment, container, false);
    }
    TextView test,status;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pp=(Viewchild) getActivity().getIntent().getSerializableExtra("list");
        test=view.findViewById(R.id.test);
        status=view.findViewById(R.id.status);
        mAuth=FirebaseAuth.getInstance();
        history=view.findViewById(R.id.history);
       pending=view.findViewById(R.id.pending);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),VaccineHistory_Activity.class);
                intent.putExtra("child",""+pp.getKey());
                startActivity(intent);

            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refvaccine = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" +pp.getKey()).child("vaccine").child("month" + which);
                HashMap hashMap=new HashMap();
                hashMap.put("status",true);
                refvaccine.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Parents").child(""+mAuth.getCurrentUser().getUid()).child("Childs").child(""+pp.getKey()).child("vaccinehistory").child("month" + which);
                        HashMap hashMap2=new HashMap();
                        hashMap2.put("name","vaccine");
                        hashMap2.put("detail",""+test.getText().toString());
                        hashMap2.put("month","month"+which);
                        hashMap2.put("key","month"+which);
                        hashMap2.put("extra","extra");
                    databaseReference.updateChildren(hashMap2);
                    }
                });

            }
        });
        GetVaccineData(pp.getKey());
//CalculateBabyAge();
    }
    public  void GetVaccineData(String key)
    {
        DatabaseReference refvaccine = FirebaseDatabase.getInstance().getReference().child("Parents").child("" + mAuth.getCurrentUser().getUid().toString()).child("Childs").child("" +key).child("vaccine").child("month" + which);
     refvaccine.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {
             if (snapshot.exists())
             {
                 Toast.makeText(getActivity(), "exists", Toast.LENGTH_SHORT).show();
            Map<String,Object> map=(Map<String, Object>) snapshot.getValue();
test.setText(""+map.get("detail").toString());
if (map.get("status").equals(true))
{
    status.setText("Vaccinated");
}
else {
    status.setText("Pending");
}

             }
             else{
                 Toast.makeText(getActivity(), "not extist", Toast.LENGTH_SHORT).show();
                 DatabaseReference from = FirebaseDatabase.getInstance().getReference().child("OurData").child("vaccine").child("month" + which);
                 CopyPasteDATA(from,refvaccine,which);
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
            {}
            else
            {}



        } else {
            if (monthsDiff == 1) {

                start = 1;
            } else {

                start = ageInMonths;
            }


        }


       which = (int)start;
        GetVaccineData(pp.getKey());
    }
    int which=1;
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

                            System.out.println("Success"+firebase.getKey());
                            System.out.println("Success");
                          //  SetAdapterdata(which);
                         //   GetRoadMap(which);

                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}