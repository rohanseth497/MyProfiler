package zorail.rohan.com.myprofiler.login;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class LoginModule {
    LoginAccountContract.View view;
    Context context;

    public LoginModule(LoginAccountContract.View view,Context context)
    {
        this.context = context;
        this.view = view;
    }
    @Provides
    @UserScope
    LoginAccountContract.View provideView()
    {
        return view;
    }

    @Provides
    @UserScope
    Context provideContext()
    {
        return context;
    }
}
