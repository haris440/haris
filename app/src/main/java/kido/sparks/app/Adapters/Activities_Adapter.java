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

import kido.sparks.app.Model.Activity_Model;
import kido.sparks.app.Model.Kitchen_Model;
import kido.sparks.app.R;

public class Activities_Adapter extends RecyclerView.Adapter<Activities_Adapter.Activities_Viewholder> {
    OnrecylerListener  mOnrecylerListener;
   List<Activity_Model> vlist=new ArrayList<>();

    @NonNull
    @Override
    public Activities_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);
        return new Activities_Viewholder(view,mOnrecylerListener);

    }
    public Activities_Adapter(OnrecylerListener mOnrecylerListener) {

        this.mOnrecylerListener = mOnrecylerListener;

    }
    @Override
    public void onBindViewHolder(@NonNull Activities_Viewholder holder, int position) {
        holder.name.setText("" + vlist.get(position).getName());
        holder.detial.setText(""+ vlist.get(position).getText());
        holder.type.setText("" + vlist.get(position).getType());
        holder.subtype.setText(""+ vlist.get(position).getSubtype());

        holder.imgedit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.mOnrecylerListener1.OnrecylerListenerOpen(position,vlist);
    }
});

            Glide.with(holder.itemView).asDrawable().centerCrop().load(""+vlist.get(position).getImgurl()).into(holder.img);



    }

    @Override
    public int getItemCount() {
        return vlist.size();
    }
    public void setlist( List<Activity_Model> vlist)
    {
        this.vlist=vlist;
        notifyDataSetChanged();

    }


    public class Activities_Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, detial,type,subtype;
        ImageView img,imgedit;
        OnrecylerListener mOnrecylerListener1;
        public Activities_Viewholder(@NonNull View itemView, OnrecylerListener mOnrecylerListener) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            detial =itemView.findViewById(R.id.eddetail);
            type=itemView.findViewById(R.id.txttype);
            subtype=itemView.findViewById(R.id.txtsubtype);
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

        void OnrecylerListener(int position, List<Activity_Model> viewChildren);
        void OnrecylerListenerOpen(int position, List<Activity_Model> viewChildren);
    }


}
