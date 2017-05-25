package zorail.rohan.com.myprofiler.profilepage;

import android.net.Uri;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableSingleObserver;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.User;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;
import zorail.rohan.com.myprofiler.data.database.Profile;

/**
 * Created by zorail on 16-May-17.
 */

public class ProfilePagePresenter implements ProfilePageContract.Presenter {

    AuthSource auth;
    ProfilePageContract.View view;
    SchedulerProvider schedulerProvider;
    CompositeDisposable disposable;
    DataBaseSource database;
    User currentUser;
    @Inject
    public ProfilePagePresenter(AuthSource auth,ProfilePageContract.View view,SchedulerProvider schedulerProvider,DataBaseSource database)
    {
        this.database = database;
        this.auth = auth;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        disposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
     getUserData();
    }

    @Override
    public void unsubscribe() {
     disposable.clear();
    }

    @Override
    public void onThumbnailClick() {
        view.startPhotoGalleryActivity();
    }

    @Override
    public void onEditProfileClick() {
        view.startProfileDetailActivity();
    }

    @Override
    public void onLogoutClick() {
        view.showLogoutSnackbar();
    }

    @Override
    public void onLogoutConfirmed() {

        disposable.add(
                auth.logUserOut()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.startLoginActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }

    @Override
    public void onAccountSettingsClick() {
        view.startProfileSettingsActivity();
    }

    @Override
    public void onThumbnailLoaded() {
        view.setThumbnailLoadingIndicator(false);

    }
    private void getUserData() {
        view.setThumbnailLoadingIndicator(true);
        view.setDetailLoadingIndicators(true);
        disposable.add(
                auth.getUser().subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(
                                new DisposableMaybeObserver<User>() {

                                    @Override
                                    public void onError(Throwable e) {
                                        view.makeToast(R.string.error_retrieving_data);
                                        view.startLoginActivity();
                                    }

                                    @Override
                                    public void onComplete() {
                                        view.makeToast(R.string.error_retrieving_data);
                                        view.startLoginActivity();
                                    }

                                    @Override
                                    public void onSuccess(User user) {
                                        ProfilePagePresenter.this.currentUser = user;
                                        getUserProfileFromDatabase();
                                    }
                                }
                        )
        );
    }

    private void getUserProfileFromDatabase() {
        disposable.add(database.getProfile(currentUser.getUserId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile profile) {
                        view.setBio(profile.getBio());
                        view.setInterests(profile.getInterests());
                        view.setName(profile.getName());
                        view.setEmail(profile.getEmail());

                        view.setDetailLoadingIndicators(false);
                        String sub = profile.getPhotoURL().replace("file://","");
                        if (profile.getPhotoURL().equals("")){
                            view.setDefaultProfilePhoto();
                        }
                        else if(new File(sub).isFile())
                        {
                            view.setProfilePhotoURL(profile.getPhotoURL());}
                        else
                        {
                            provideUrl();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                        view.startLoginActivity();
                    }

                    @Override
                    public void onComplete() {
                        view.startLoginActivity();
                    }
                })
        );

    }
    private void provideUrl()
    {
        disposable.add(database.downloadUrl(currentUser)
                               .subscribeOn(schedulerProvider.io())
                               .observeOn(schedulerProvider.ui())
                               .subscribeWith(new DisposableSingleObserver<Uri>() {
                                   @Override
                                   public void onSuccess(@NonNull Uri uri) {
                                       view.setProfilePhotoURL(uri.toString());
                                   }

                                   @Override
                                   public void onError(@NonNull Throwable e) {
                                       view.makeToast(e.getMessage());
                                   }
                               }));
    }
}
