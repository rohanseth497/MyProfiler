package zorail.rohan.com.myprofiler.profilepage;

import dagger.Component;
import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Component(modules = ProfilePageModule.class,dependencies = NetComponent.class)
public interface ProfilePageComponent {
    void inject(ProfilePageFragment fragment);
}
