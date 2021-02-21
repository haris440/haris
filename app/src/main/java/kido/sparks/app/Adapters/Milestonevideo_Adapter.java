package kido.sparks.app.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.devbrackets.android.exomedia.ui.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import kido.sparks.app.Model.VaccineHistory;
import kido.sparks.app.Model.VideoModel;
import kido.sparks.app.R;

public class Milestonevideo_Adapter extends RecyclerView.Adapter<Milestonevideo_Adapter.VideoViewHolder> {

   List<VideoModel> vlist=new ArrayList<>();

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_milevideolist, parent, false);
        return new VideoViewHolder(view);

    }
    public Milestonevideo_Adapter() {



    }
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
       holder.txtname.setText(""+vlist.get(position).getName());
       holder.txtdetail.setText(""+ vlist.get(position).getDetail());




    }

    @Override
    public int getItemCount() {
        return vlist.size();
    }

    public void setlist(List<VideoModel> list) {
        this.vlist=list;
        notifyDataSetChanged();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView txtname,txtdetail;
        VideoView vvInfo;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
           txtname=itemView.findViewById(R.id.txtvname);
           txtdetail=itemView.findViewById(R.id.txtvdetail);



        }


    }



}
