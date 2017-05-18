package zorail.rohan.com.myprofiler.createaccount;


import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zorail.rohan.com.myprofiler.ActivityUtils;
import zorail.rohan.com.myprofiler.R;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String CREATE_FRAGMENT = "CREATE_FRAGMENT";

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        fragmentManager = getFragmentManager();

        CreateAccountFragment fragment = (CreateAccountFragment)
                fragmentManager.findFragmentByTag(CREATE_FRAGMENT);

        if (fragment == null){
            fragment = CreateAccountFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(fragmentManager,
                fragment,
                R.id.cont_create_fragment,
                CREATE_FRAGMENT
        );
    }

}
