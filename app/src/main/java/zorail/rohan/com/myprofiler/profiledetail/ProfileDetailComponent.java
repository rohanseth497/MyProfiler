package zorail.rohan.com.myprofiler.profiledetail;

import dagger.Component;
import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.UserScope;


/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Component(modules = ProfileDetailModule.class,dependencies = NetComponent.class)
public interface ProfileDetailComponent {
    void inject(ProfileDetailFragment fragment);
}
