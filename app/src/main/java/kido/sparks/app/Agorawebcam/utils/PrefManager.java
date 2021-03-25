package kido.sparks.app.Agorawebcam.utils;

import android.content.Context;
import android.content.SharedPreferences;

import kido.sparks.app.Agorawebcam.Constants;


public class PrefManager {
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
    }
}
