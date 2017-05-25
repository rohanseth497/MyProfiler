package zorail.rohan.com.myprofiler.photogallery;

import dagger.Subcomponent;

import zorail.rohan.com.myprofiler.di.SubComponentBuilder;
import zorail.rohan.com.myprofiler.di.UserScope;


/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Subcomponent(modules = PhotoGalleryModule.class)
public interface PhotoGalleryComponent {
    void inject(PhotoGalleryFragment fragment);
    @Subcomponent.Builder
    interface Builder extends SubComponentBuilder<PhotoGalleryComponent>{
        Builder photoGalleryModule(PhotoGalleryModule photoGalleryModule);
    }
}
