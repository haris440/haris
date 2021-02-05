package kido.sparks.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kido.sparks.app.Model.Milestone;
import kido.sparks.app.R;

public class Milestone_Adapter extends RecyclerView.Adapter<Milestone_Adapter.ViewHolder_Milestone_Dashboard> {
    OnrecylerListener  mOnrecylerListener;
   List<Milestone> vlist=new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder_Milestone_Dashboard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.milestone_item, parent, false);
        return new ViewHolder_Milestone_Dashboard(view,mOnrecylerListener);

    }
    public Milestone_Adapter(OnrecylerListener mOnrecylerListener) {

        this.mOnrecylerListener = mOnrecylerListener;

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Milestone_Dashboard holder, int position) {

        holder.checkBox.setText(""+vlist.get(position).getName());
      //  holder.checkBox.setChecked(true);
//
//holder.imgedit.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        holder.mOnrecylerListener1.OnrecylerListenerEdit(position,vlist);
//    }
//}
// );


    }

    @Override
    public int getItemCount() {
        return vlist.size();
    }



    public void setlist( List<Milestone> vlist)
    {

        this.vlist=vlist;
        notifyDataSetChanged();

    }


    public class ViewHolder_Milestone_Dashboard extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox checkBox;
        OnrecylerListener mOnrecylerListener1;
        public ViewHolder_Milestone_Dashboard(@NonNull View itemView, OnrecylerListener mOnrecylerListener) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);

            mOnrecylerListener1 = mOnrecylerListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnrecylerListener1.OnrecylerListener(getAdapterPosition(), vlist);
        }
    }
    public interface OnrecylerListener {
        void OnrecylerListener(int position, List<Milestone> viewChildren);

    }

}
