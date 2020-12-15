package kido.sparks.app.Parent_Panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import kido.sparks.app.R;

public class ViewChild extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_child);
    }
}