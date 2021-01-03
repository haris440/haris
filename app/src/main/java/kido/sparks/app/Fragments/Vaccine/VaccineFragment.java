package kido.sparks.app.Fragments.Vaccine;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class VaccineFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vaccine_fragment, container, false);
    }
    TextView test;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Viewchild pp=(Viewchild) getActivity().getIntent().getSerializableExtra("list");
        test =view.findViewById(R.id.test);
        Toast.makeText(getActivity(), ""+pp.getBabyname(), Toast.LENGTH_SHORT).show();
        test.setText(""+pp.getBabyname());
    }
}