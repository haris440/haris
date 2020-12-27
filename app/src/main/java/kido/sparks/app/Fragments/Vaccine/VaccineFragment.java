package kido.sparks.app.Fragments.Vaccine;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class VaccineFragment extends Fragment {

    private VaccineViewModel mViewModel;

    public static VaccineFragment newInstance() {
        return new VaccineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vaccine_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VaccineViewModel.class);
        // TODO: Use the ViewModel
        Viewchild pp=(Viewchild) getActivity().getIntent().getSerializableExtra("list");

        Toast.makeText(getActivity(), ""+pp.getBabyname(), Toast.LENGTH_SHORT).show();
    }

}