package zorail.rohan.com.myprofiler.profiledetail;

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

public class ProfileDetailPresenter implements ProfileDetailContract.Presenter {

    private AuthSource auth;
    private DataBaseSource database;
    private ProfileDetailContract.View view;
    private CompositeDisposable disposable;
    private SchedulerProvider schedulerProvider;
    private Profile currentProfile;
    @Inject
    public ProfileDetailPresenter(AuthSource auth,DataBaseSource database,ProfileDetailContract.View view,SchedulerProvider schedulerProvider){

        this.auth = auth;
        this.database = database;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        disposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getActiveUser();
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void onBackButtonClick() {
        view.startProfilePageActivity();
    }

    @Override
    public void onDoneButtonClick() {

        currentProfile.setBio(view.getBio());
        currentProfile.setInterests(view.getInterests());

        disposable.add(
                database.updateProfile(currentProfile)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.startProfilePageActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }
    private void getActiveUser(){
        disposable.add(auth.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        getUserProfile(user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_no_data_found);
                    }

                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }
                })

        );
    }

    private void getUserProfile(String uid) {
        disposable.add(database.getProfile(uid)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<Profile>() {
                    @Override
                    public void onSuccess(Profile profile) {
                        ProfileDetailPresenter.this.currentProfile = profile;
                        view.setBioText(profile.getBio());
                        view.setInterestsText(profile.getInterests());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(R.string.error_no_data_found);
                    }

                    @Override
                    public void onComplete() {
                        view.startProfilePageActivity();
                    }
                })

        );
    }

}
