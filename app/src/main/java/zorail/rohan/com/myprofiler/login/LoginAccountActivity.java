package zorail.rohan.com.myprofiler.login;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zorail.rohan.com.myprofiler.ActivityUtils;
import zorail.rohan.com.myprofiler.R;

/**
 * Created by zorail on 16-May-17.
 */

public class LoginAccountActivity extends AppCompatActivity {

    private static final String LOGIN_FRAGMENT = "LOGIN_FRAGMENT";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getFragmentManager();

        LoginAccountFragment fragment = (LoginAccountFragment)
                fragmentManager.findFragmentByTag(LOGIN_FRAGMENT);

        if (fragment == null){
            fragment = LoginAccountFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(fragmentManager,
                fragment,
                R.id.root_login_activity,
                LOGIN_FRAGMENT
        );
    }
}
