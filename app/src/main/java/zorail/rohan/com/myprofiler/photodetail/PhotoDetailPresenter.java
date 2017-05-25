package zorail.rohan.com.myprofiler.photodetail;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.User;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;
import zorail.rohan.com.myprofiler.data.database.Profile;

/**
 * Created by zorail on 17-May-17.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private SchedulerProvider schedulerProvider;
    private AuthSource auth;
    private PhotoDetailContract.View view;
    private DataBaseSource database;
    private CompositeDisposable disposable;
    private Profile currentProfile;
    @Inject
    public PhotoDetailPresenter(AuthSource auth, DataBaseSource database, PhotoDetailContract.View view, SchedulerProvider schedulerProvider,CompositeDisposable disposable)
    {
        this.auth = auth;
        this.view = view;
        this.database = database;
        this.disposable = disposable;
        this.schedulerProvider = schedulerProvider;
        view.setPresenter(this);
    }
    @Override
    public void subscribe() {
        getCurrentUser();
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void onBackButtonPress() {
        view.startPhotoGalleryActivity();
    }

    @Override
    public void onDoneButtonPress() {
        view.showProgressIndicator(true);
        currentProfile.setPhotoURL(view.getPhotoURL());

        disposable.add(database.uploadImage(currentProfile)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        updateProfile();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgressIndicator(false);
                        view.makeToast(e.getMessage());
                    }

                })
        );
    }
    private void updateProfile()
    {
        disposable.add(database.updateProfile(currentProfile)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgressIndicator(false);
                        view.makeToast(e.getMessage());
                    }

                })
        );
    }

    @Override
    public void onImageLoaded() {
        view.showProgressIndicator(false);
    }

    @Override
    public void onImageLoadFailure() {
        view.makeToast(R.string.error_loading_image);
        view.startPhotoGalleryActivity();
    }
    private void getCurrentProfile(String uid) {
        disposable.add(database.getProfile(uid)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onSuccess(Profile profile) {
                        PhotoDetailPresenter.this.currentProfile = profile;
                        view.setBitmap();
                        view.showProgressIndicator(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgressIndicator(false);
                        view.makeToast(e.getMessage());
                    }

                })

        );
    }
    private void getCurrentUser() {
        view.showProgressIndicator(true);
        disposable.add(auth.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onComplete() {
                        view.startPhotoGalleryActivity();
                    }

                    @Override
                    public void onSuccess(User user) {
                        getCurrentProfile(user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.startPhotoGalleryActivity();
                        view.makeToast(e.getMessage());
                    }
                })

        );
    }
}
