package kido.sparks.app.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExtraApter extends RecyclerView.Adapter<ExtraApter.ViewHolderExtra> {


    @NonNull
    @Override
    public ExtraApter.ViewHolderExtra onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraApter.ViewHolderExtra holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderExtra extends RecyclerView.ViewHolder{
        public ViewHolderExtra(@NonNull View itemView) {
            super(itemView);

        }
    }
}
