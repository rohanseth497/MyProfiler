package zorail.rohan.com.myprofiler.profiledetail;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class ProfileDetailModule {
    ProfileDetailContract.View view;
    public ProfileDetailModule(ProfileDetailContract.View view)
    {
        this.view = view;
    }
    @UserScope
    @Provides
    ProfileDetailContract.View provideView()
    {return  view;}
}
