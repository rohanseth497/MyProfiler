package zorail.rohan.com.myprofiler.profilesettings;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class ProfileSettingsModule {
    ProfileSettingsContract.View view;
    public ProfileSettingsModule(ProfileSettingsContract.View view)
    {
        this.view = view;
    }
    @UserScope
    @Provides
    ProfileSettingsContract.View provideView()
    {
        return view;
    }
}
