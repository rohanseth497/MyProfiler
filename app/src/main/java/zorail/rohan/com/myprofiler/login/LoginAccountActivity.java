package zorail.rohan.com.myprofiler.login;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.inject.Inject;

import zorail.rohan.com.myprofiler.ActivityUtils;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SessionManager;
import zorail.rohan.com.myprofiler.profilepage.ProfilePageActivity;

/**
 * Created by zorail on 16-May-17.
 */

public class LoginAccountActivity extends AppCompatActivity {

    private static final String LOGIN_FRAGMENT = "LOGIN_FRAGMENT";

    FragmentManager fragmentManager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getFragmentManager();
        sessionManager = new SessionManager(getApplicationContext());
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
        {
            setUpFragment();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    setUpFragment();
                }
                else
                {
                    Toast.makeText(this,
                            R.string.error_permission_denied,
                            Toast.LENGTH_SHORT).show();
                    LoginAccountActivity.this.finish();
                }
        }
    }
    public void setUpFragment()
    {
        if(sessionManager.isLoggedIn())
        {
            Intent i = new Intent(this, ProfilePageActivity.class);
            startActivity(i);
            finish();
        }


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
