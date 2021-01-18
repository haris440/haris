package kido.sparks.app.Fragments.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import kido.sparks.app.Adapters.Counter_Adapter;
import kido.sparks.app.Adapters.Milestone_Adapter;
import kido.sparks.app.Model.Milestone;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;


public class DashboardFragment extends Fragment implements Milestone_Adapter.OnrecylerListener,Counter_Adapter.OnrecylerListenercounter{


    TextView babyname,babyage;
    Milestone_Adapter milestone_adapter;
    Counter_Adapter  counter_adapter;
    RecyclerView recyclerView,recyclerView2;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_view__child, container, false);
        return root;
    }
    Viewchild pp;


    public  void CalculateBabyAge()
     {

        int yearr= Integer.parseInt(pp.getAgeyear());
        int month= Integer.parseInt(pp.getAgemonth());
        int day= Integer.parseInt(pp.getAgeday());
        Calendar birthDay = new GregorianCalendar(yearr, month, day);
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        int yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int monthsDiff = today.get(Calendar.MONTH) - birthDay.get(Calendar.MONTH);
        int totaldays = today.get(Calendar.DAY_OF_YEAR) - birthDay.get(Calendar.DAY_OF_YEAR);
        long ageInMonths = yearsInBetween*12 + monthsDiff;
        long age = yearsInBetween;

        if(ageInMonths==0)
        { if (totaldays==1)
            babyage.setText(""+pp.getBabyname()+"is " +totaldays+" day old");
        else
            babyage.setText(""+pp.getBabyname()+"is " +totaldays+" days old");
            Log.e("dsad","Number of months since James gosling born : " + monthsDiff);
            Log.e("dsad","Sir James Gosling's age : "+ totaldays);
            Log.e("dsad","Sir James Gosling's age : "+ ageInMonths);

        }else {
            if(monthsDiff==1)
                babyage.setText(""+pp.getBabyname()+" is 1"+" month" );
            else
                babyage.setText(""+pp.getBabyname()+" is "+ ageInMonths+" months" );
            Log.e("dsad","Number of months since James gosling born : " + monthsDiff);
            Log.e("dsad","Sir James Gosling's age : "+ totaldays);
            Log.e("dsad","Sir James Gosling's age : "+ ageInMonths);

        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pp=(Viewchild) getActivity().getIntent().getSerializableExtra("list");
        babyage=view.findViewById(R.id.babyage);
        List<Milestone> list = new ArrayList<>();
        list.add(new Milestone("data from month 1","a","a"));
        list.add(new Milestone("data from month1","a","a"));
        list.add(new Milestone("data from month1","a","a"));
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recylercounter);

        milestone_adapter = new Milestone_Adapter(this::OnrecylerListener);
        counter_adapter=new Counter_Adapter(this::OnrecylerListenercounter);
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

    @Override
    public void OnrecylerListener(int position, List<Milestone> viewChildren) {

    }

    @Override
    public void OnrecylerListenercounter(int position) {
        List<Milestone> list = new ArrayList<>();
        int pos=position+1;
        list.add(new Milestone("data from month "+pos,"a","a"));
        list.add(new Milestone("data from month "+pos,"a","a"));
        list.add(new Milestone("data from month "+pos,"a","a"));
        counter_adapter.setlist(position);
        milestone_adapter.setlist(list);
    }
}