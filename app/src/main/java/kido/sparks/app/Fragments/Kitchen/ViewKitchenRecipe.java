package kido.sparks.app.Fragments.Kitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import kido.sparks.app.Model.Kitchen_Model;
import kido.sparks.app.Model.Viewchild;
import kido.sparks.app.R;

public class ViewKitchenRecipe extends AppCompatActivity {
TextView txtname,txtdetail;
Button btnlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_kitchen_recipe);
       Kitchen_Model kitchen_model=( Kitchen_Model) getIntent().getSerializableExtra("list");
      txtdetail=findViewById(R.id.eddetail);
        txtname=findViewById(R.id.edname);
        btnlink=findViewById(R.id.btnlink);
        if (kitchen_model.isUrlstatus())
        {
         btnlink.setVisibility(View.VISIBLE);
         btnlink.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                         try {
            String url = ""+kitchen_model.getUrl();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (Exception e)
        {
            Toast.makeText(ViewKitchenRecipe.this, "Error occurs", Toast.LENGTH_SHORT).show();
        }
             }
         });
         txtname.setText(""+kitchen_model.getName());
         txtdetail.setText(""+kitchen_model.getText());
        }
           else {
            txtname.setText(""+kitchen_model.getName());
            txtdetail.setText(""+kitchen_model.getText());
            btnlink.setVisibility(View.GONE);
        }
    }

}