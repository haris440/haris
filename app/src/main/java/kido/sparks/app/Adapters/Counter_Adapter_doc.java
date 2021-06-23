package kido.sparks.app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kido.sparks.app.R;

public class Counter_Adapter_doc extends RecyclerView.Adapter<Counter_Adapter_doc.ViewHolder_Counter> {
    OnrecylerListenercounter mOnrecylerListener;
//
    @NonNull
    @Override
    public ViewHolder_Counter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthcounter_item, parent, false);
        return new ViewHolder_Counter(view,mOnrecylerListener);

    }
    public Counter_Adapter_doc(OnrecylerListenercounter mOnrecylerListener) {

        this.mOnrecylerListener = mOnrecylerListener;

    }
 int row_index=0;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder_Counter holder, int position) {
        int pos=position+1;
        holder.txtcounter.setText("Month "+pos);
        holder.textView14.setText("month"+pos);
        holder.textView14.setVisibility(View.INVISIBLE);




    }

    @Override
    public int getItemCount() {
        return 36;
    }
    public void setlist( int current)
    {

        row_index=current;
         notifyDataSetChanged();

    }


    public class ViewHolder_Counter extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txtcounter,textView14;
        OnrecylerListenercounter mOnrecylerListener1;
        public ViewHolder_Counter(@NonNull View itemView, OnrecylerListenercounter mOnrecylerListener) {
            super(itemView);
            txtcounter=itemView.findViewById(R.id.txtcounter);

            textView14=itemView.findViewById(R.id.textView14);
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
