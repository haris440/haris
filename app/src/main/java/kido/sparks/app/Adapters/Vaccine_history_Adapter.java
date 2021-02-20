package kido.sparks.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import kido.sparks.app.Model.VaccineHistory;
import kido.sparks.app.R;

public class Vaccine_history_Adapter extends RecyclerView.Adapter<Vaccine_history_Adapter.VaccineViewHolder> {

   List<VaccineHistory> vlist=new ArrayList<>();

    @NonNull
    @Override
    public VaccineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaccinelist, parent, false);
        return new VaccineViewHolder(view);

    }
    public Vaccine_history_Adapter() {



    }
    @Override
    public void onBindViewHolder(@NonNull VaccineViewHolder holder, int position) {
        holder.name.setText("("+vlist.get(position).getMonth()+")\n" + vlist.get(position).getName());
        holder.detial.setText(""+ vlist.get(position).getDetail());





    }

    @Override
    public int getItemCount() {
        return vlist.size();
    }

    public void setlist(List<VaccineHistory> list) {
        this.vlist=list;
        notifyDataSetChanged();
    }


    public class VaccineViewHolder extends RecyclerView.ViewHolder{
        TextView name, detial;

        public VaccineViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            detial =itemView.findViewById(R.id.eddetail);



        }


    }



}
