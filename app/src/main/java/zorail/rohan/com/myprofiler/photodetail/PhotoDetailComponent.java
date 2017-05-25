package zorail.rohan.com.myprofiler.photodetail;

import dagger.Component;
import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Component(modules = PhotoDetailModule.class,dependencies = NetComponent.class)
public interface PhotoDetailComponent {
    void inject(PhotoDetailFragment fragment);
}
