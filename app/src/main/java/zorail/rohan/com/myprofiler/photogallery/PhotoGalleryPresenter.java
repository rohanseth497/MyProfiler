package zorail.rohan.com.myprofiler.photogallery;

import android.content.ContentResolver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableMaybeObserver;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.photos.Photo;
import zorail.rohan.com.myprofiler.data.photos.PhotoSource;

/**
 * Created by zorail on 17-May-17.
 */

public class PhotoGalleryPresenter implements PhotoGalleryContract.Presenter {

    private SchedulerProvider schedulerProvider;
    private PhotoSource photoSource;
    private PhotoGalleryContract.View view;
    private List<Photo> photos;
    private CompositeDisposable disposable;
    private ContentResolver resolver;
    @Inject
    public PhotoGalleryPresenter(ContentResolver resolver,PhotoSource photoSource,PhotoGalleryContract.View view,SchedulerProvider schedulerProvider,CompositeDisposable disposable)
    {
        this.schedulerProvider = schedulerProvider;
        this.photoSource = photoSource;
        this.view = view;
        this.resolver = resolver;
        this.disposable = disposable;
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadImageURLsIfAvailable();
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void onPhotoItemClick(int itemPosition) {
        Photo photo = photos.get(itemPosition);
        view.startPhotoDetailActivity(photo.getPhotoUri());
    }
    private void loadImageURLsIfAvailable(){
        view.showProgressIndicator(true);
        disposable.add(photoSource.getThumbnails(resolver)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<List<Photo>>() {
                    @Override
                    public void onSuccess(List<Photo> photos) {
                        view.showProgressIndicator(false);
                        PhotoGalleryPresenter.this.photos = photos;
                        view.setAdapterData(photos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.setNoListDataFound();
                    }

                })
        );
    }
}
