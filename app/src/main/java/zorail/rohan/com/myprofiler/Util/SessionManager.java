package zorail.rohan.com.myprofiler.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zorail on 25-May-17.
 */

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    private static final String PREF_NAME = "MyProfiler_Login";

    public SessionManager(Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void setLogin(boolean isLoggedIn)
    {
        editor.putBoolean(KEY_IS_LOGGEDIN,isLoggedIn);
        editor.commit();
    }
    public boolean isLoggedIn()
    {
        return preferences.getBoolean(KEY_IS_LOGGEDIN,false);
    }
}
