package zorail.rohan.com.myprofiler.photodetail;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class PhotoDetailModule {
    PhotoDetailContract.View view;
    public PhotoDetailModule(PhotoDetailContract.View view)
    {this.view = view;}
    @UserScope
    @Provides
    PhotoDetailContract.View provideView()
    {
        return view;
    }
}
