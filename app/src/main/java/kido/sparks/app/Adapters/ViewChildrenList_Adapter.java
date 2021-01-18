package kido.sparks.app.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class ViewChildrenList_Adapter extends RecyclerView.Adapter<ViewChildrenList_Adapter.Viewholder_Child> {
    OnrecylerListener  mOnrecylerListener;
   List<Viewchild> vlist=new ArrayList<>();

    @NonNull
    @Override
    public Viewholder_Child onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewchild, parent, false);
        return new ViewChildrenList_Adapter.Viewholder_Child(view,mOnrecylerListener);

    }
    public ViewChildrenList_Adapter( OnrecylerListener mOnrecylerListener) {

        this.mOnrecylerListener = mOnrecylerListener;

    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder_Child holder, int position) {
        holder.name.setText("" + vlist.get(position).getBabyname());
        holder.age.setText(""+CalculateBabyAge(vlist.get(position).getAgeyear(),vlist.get(position).getAgemonth(),vlist.get(position).getAgemonth()));
        holder.weight.setText(""+vlist.get(position).getBabyweight()+" kg");
        holder.gender.setText(""+vlist.get(position).getBabygender());



        holder.imgedit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.mOnrecylerListener1.OnrecylerListenerEdit(position,vlist);
    }
});

            Glide.with(holder.itemView).asDrawable().centerCrop().load(""+vlist.get(position).getBabyimg()).into(holder.img);

        if (vlist.get(position).getBabygender().toString().equals("Male"))
        {

        }
        else
        {
            holder.name.setTextColor(holder.itemView.getResources().getColor(R.color.pink));
            holder.age.setTextColor(holder.itemView.getResources().getColor(R.color.pink));
            holder.weight.setTextColor(holder.itemView.getResources().getColor(R.color.pink));
            holder.gender .setTextColor(holder.itemView.getResources().getColor(R.color.pink));
            holder.imgedit.setColorFilter(holder.itemView.getResources().getColor(R.color.pink));
        }

    }

    @Override
    public int getItemCount() {
        return vlist.size();
    }
    public void setlist( List<Viewchild> vlist)
    {
        this.vlist=vlist;
        notifyDataSetChanged();

    }


    public class Viewholder_Child extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,age,weight,gender;
        ImageView img,imgedit;
        OnrecylerListener mOnrecylerListener1;
        public Viewholder_Child(@NonNull View itemView, OnrecylerListener mOnrecylerListener) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
            weight=itemView.findViewById(R.id.weight);
            gender=itemView.findViewById(R.id.gender);
            img=itemView.findViewById(R.id.imageView);
            imgedit=itemView.findViewById(R.id.imgedit);

            mOnrecylerListener1 = mOnrecylerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnrecylerListener1.OnrecylerListener(getAdapterPosition(), vlist);
        }
    }
    public interface OnrecylerListener {

        void OnrecylerListener(int position, List<Viewchild> viewChildren);
        void OnrecylerListenerEdit(int position, List<Viewchild> viewChildren);
    }


    public  String CalculateBabyAge(String yearrr,String monthh ,String dayy)
    {
        int yearr= Integer.parseInt(yearrr);
        int month= Integer.parseInt(monthh);
        int day= Integer.parseInt(dayy);
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
           return  ""+totaldays+" day old";
        else
            return  ""+totaldays+" days old";



        }
        else {
            if(monthsDiff==1)
               return  ""+"1 "+" month old" ;
            else
               return  ""+ ageInMonths+" months old" ;


        }
//        Log.e("dsad","Number of months since James gosling born : " + monthsDiff);
//        Log.e("dsad","Sir James Gosling's age : "+ totaldays);
//        Log.e("dsad","Sir James Gosling's age : "+ ageInMonths);
    }
}
