package zorail.rohan.com.myprofiler.profilepage;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class ProfilePageModule {
    ProfilePageContract.View view;
    public ProfilePageModule(ProfilePageContract.View view)
    {
        this.view = view;
    }
    @UserScope
    @Provides
    ProfilePageContract.View provideView()
    {
        return view;
    }
}
