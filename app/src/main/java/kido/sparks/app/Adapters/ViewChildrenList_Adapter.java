package kido.sparks.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
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
        holder.age.setText(""+vlist.get(position).getBabyage());
        holder.weight.setText(""+vlist.get(position).getBabyweight());
        holder.gender.setText(""+vlist.get(position).getBabygender());


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
        ImageView img;
        OnrecylerListener mOnrecylerListener1;
        public Viewholder_Child(@NonNull View itemView, OnrecylerListener mOnrecylerListener) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
            weight=itemView.findViewById(R.id.weight);
            gender=itemView.findViewById(R.id.gender);
            img=itemView.findViewById(R.id.imageView);
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
    }
}
