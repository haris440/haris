package kido.sparks.app.Fragments.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    TextView babyname,babyage;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_view__child, container, false);
        return root;
    }
    Viewchild pp;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pp=(Viewchild) getActivity().getIntent().getSerializableExtra("list");
        babyage=getActivity().findViewById(R.id.babyage);
//
//        if (pp.getBabygender().toString().equals("Female")){
//            getActivity().setContentView(R.layout.activity_view__child2);
//        }
//        else{
//            getActivity().setContentView(R.layout.activity_view__child);
//        }

//        final Calendar cldr = Calendar.getInstance();
//        int day = cldr.get(Calendar.DAY_OF_MONTH);
//        int month = cldr.get(Calendar.MONTH);
//        int year = cldr.get(Calendar.YEAR);
//        babyage.setText(""+getDaysBetweenDates(""+pp.getBabyage().trim(),"27/12/2020")+" Days");
CalculateBabyAge();
    }

    public  void CalculateBabyAge()
    {
        babyage=getActivity().findViewById(R.id.babyage);
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


        }else {
            if(monthsDiff==1)
                babyage.setText(""+pp.getBabyname()+" is 1"+" month" );
            else
                babyage.setText(""+pp.getBabyname()+" is "+ ageInMonths+" months" );


        }
        Log.e("dsad","Number of months since James gosling born : " + monthsDiff);
        Log.e("dsad","Sir James Gosling's age : "+ totaldays);
        Log.e("dsad","Sir James Gosling's age : "+ ageInMonths);
    }
}