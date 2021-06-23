package kido.sparks.app.SharedPrefrenceClasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrence {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public SharedPrefrence(Context context)
    {
        sharedPreferences=context.getSharedPreferences("whichusersp",0);
        editor=sharedPreferences.edit();

    }
    public void setypeofuser(String type)
    {
        editor.putString("type",""+type);
        editor.commit();
    }

    public String getypeofuser()
    {

        return  sharedPreferences.getString("type","0");
    }

    public void setintrostatus(String intro)
    {
        editor.putString("intro",""+intro);
        editor.commit();
    }

    public String getintrostatus()
    {

        return  sharedPreferences.getString("intro","1");
    }
}