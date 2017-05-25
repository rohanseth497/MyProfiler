package zorail.rohan.com.myprofiler.photogallery;


import android.content.ContentResolver;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.data.photos.PhotoService;
import zorail.rohan.com.myprofiler.data.photos.PhotoSource;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class PhotoGalleryModule {
    PhotoGalleryContract.View view;
    ContentResolver resolver;
    public PhotoGalleryModule(PhotoGalleryContract.View view,ContentResolver resolver)
    {
        this.view = view;
        this.resolver = resolver;
    }
    @UserScope
    @Provides
    PhotoGalleryContract.View provideView()
    {return view;}
    @UserScope
    @Provides
    ContentResolver provideResolver()
    {
        return resolver;
    }
    @UserScope
    @Provides
    PhotoSource providePhotoSource()
    {
        return PhotoService.getInstance();
    }

}
