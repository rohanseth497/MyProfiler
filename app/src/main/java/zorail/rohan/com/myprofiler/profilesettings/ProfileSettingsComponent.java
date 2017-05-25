package zorail.rohan.com.myprofiler.profilesettings;

import dagger.Subcomponent;
import zorail.rohan.com.myprofiler.di.SubComponentBuilder;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Subcomponent(modules = ProfileSettingsModule.class)
public interface ProfileSettingsComponent {
    void inject(ProfileSettingsFragment fragment);
    @Subcomponent.Builder
    interface Builder extends SubComponentBuilder<ProfileSettingsComponent>{
        Builder profileSettingsModule(ProfileSettingsModule module);
    }
}
