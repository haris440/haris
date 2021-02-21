package kido.sparks.app.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.devbrackets.android.exomedia.ui.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kido.sparks.app.Fragments.Activities.ViewActivity;
import kido.sparks.app.Model.VaccineHistory;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;

public class Milestonevideo_Adapter extends RecyclerView.Adapter<Milestonevideo_Adapter.VideoViewHolder> {
    OnrecylerListener onrecylerListener;
   List<VideoModel> vlist=new ArrayList<>();
public Milestonevideo_Adapter(OnrecylerListener onrecylerListener)
{
    this.onrecylerListener=onrecylerListener;
}

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_milevideolist, parent, false);
        return new VideoViewHolder(view,onrecylerListener);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
       holder.txtname.setText(""+vlist.get(position).getName());
       holder.txtdetail.setText(""+ vlist.get(position).getDetail());

       holder.im.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               holder.onrecylerListener1.OnrecylerListener(position,vlist);
           }
       });


    }

    @Override
    public int getItemCount() {
        return vlist.size();
    }

    public void setlist(List<VideoModel> list) {
        this.vlist=list;
        notifyDataSetChanged();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder {
        OnrecylerListener onrecylerListener1;
        TextView txtname,txtdetail;
        ImageView im;

        public VideoViewHolder(@NonNull View itemView, OnrecylerListener onrecylerListener) {
            super(itemView);
           txtname=itemView.findViewById(R.id.txtvname);
           txtdetail=itemView.findViewById(R.id.txtvdetail);
           im=itemView.findViewById(R.id.imageView5);
            onrecylerListener1 = onrecylerListener;


        }


    }

    public interface OnrecylerListener {
        void OnrecylerListener(int position, List<VideoModel> vlist);

    }

}
