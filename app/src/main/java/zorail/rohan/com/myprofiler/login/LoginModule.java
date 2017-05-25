package zorail.rohan.com.myprofiler.login;


import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class LoginModule {
    LoginAccountContract.View view;

    public LoginModule(LoginAccountContract.View view)
    {
        this.view = view;
    }
    @Provides
    @UserScope
    LoginAccountContract.View provideView()
    {
        return view;
    }

}
