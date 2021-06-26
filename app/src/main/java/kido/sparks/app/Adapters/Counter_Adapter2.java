package kido.sparks.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kido.sparks.app.R;

public class Counter_Adapter2 extends RecyclerView.Adapter<Counter_Adapter2.ViewHolder_Counter> {
    OnrecylerListenercounter mOnrecylerListener;
//
    @NonNull
    @Override
    public ViewHolder_Counter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_item, parent, false);
        return new ViewHolder_Counter(view,mOnrecylerListener);

    }
    public Counter_Adapter2(OnrecylerListenercounter mOnrecylerListener) {

        this.mOnrecylerListener = mOnrecylerListener;

    }
 int row_index=0;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Counter holder, int position) {
        int pos=position+1;

       if (pos==1)
        {
            holder.txtcounter.setText(""+pos);
        }
        else if (pos==2)
        {
            holder.txtcounter.setText(""+pos);
        }
        else if (pos==3)
        {
            holder.txtcounter.setText(""+pos);
        }
        else if (pos==4)
        {
            holder.txtcounter.setText("9");
        }
       else if (pos==5)
       {
           holder.txtcounter.setText("15");
       }

        if(row_index==position){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                holder.linearLayout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.grey));
                holder.txtcounter.setTextColor(holder.itemView.getResources().getColor(R.color.white));
            };

        }
        else
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                holder.linearLayout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white));
                holder.txtcounter.setTextColor(holder.itemView.getResources().getColor(R.color.black));
            };

        }
//holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        row_index=position;
//        notifyDataSetChanged();
//    }
//});

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    public void setlist( int current)
    {

        row_index=current;
         notifyDataSetChanged();

    }


    public class ViewHolder_Counter extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txtcounter;
        LinearLayout linearLayout;
        OnrecylerListenercounter mOnrecylerListener1;
        public ViewHolder_Counter(@NonNull View itemView, OnrecylerListenercounter mOnrecylerListener) {
            super(itemView);
            txtcounter=itemView.findViewById(R.id.txtcounter);
            linearLayout=itemView.findViewById(R.id.lyt);
            mOnrecylerListener1 = mOnrecylerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnrecylerListener1.OnrecylerListenercounter(getAdapterPosition());
        }
    }
    public interface OnrecylerListenercounter {
        void OnrecylerListenercounter(int position);

    }

}
