package zorail.rohan.com.myprofiler.di.Binder;

import dagger.Binds;
import dagger.Module;
import dagger.Subcomponent;
import dagger.multibindings.IntoMap;
import zorail.rohan.com.myprofiler.di.Key.SubComponentKey;
import zorail.rohan.com.myprofiler.di.SubComponentBuilder;
import zorail.rohan.com.myprofiler.photogallery.PhotoGalleryComponent;
import zorail.rohan.com.myprofiler.profilesettings.ProfileSettingsComponent;

/**
 * Created by zorail on 24-May-17.
 */
@Module(subcomponents = {ProfileSettingsComponent.class, PhotoGalleryComponent.class})
public abstract class Binder {
    @Binds
    @IntoMap
    @SubComponentKey(ProfileSettingsComponent.Builder.class)
    public abstract SubComponentBuilder myActivity(ProfileSettingsComponent.Builder impl);

    @Binds
    @IntoMap
    @SubComponentKey(PhotoGalleryComponent.Builder.class)
    public abstract SubComponentBuilder myGallery(PhotoGalleryComponent.Builder impl);

}
