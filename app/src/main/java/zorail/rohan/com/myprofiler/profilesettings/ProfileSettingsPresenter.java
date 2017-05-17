package zorail.rohan.com.myprofiler.profilesettings;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.User;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;

/**
 * Created by zorail on 16-May-17.
 */

public class ProfileSettingsPresenter implements ProfileSettingsContract.Presenter {

    ProfileSettingsContract.View view;
    CompositeDisposable disposable;
    AuthSource auth;
    DataBaseSource database;
    SchedulerProvider schedulerProvider;
    private String uid;


    public ProfileSettingsPresenter(AuthSource auth, DataBaseSource database, ProfileSettingsContract.View view, SchedulerProvider schedulerProvider)
    {
        this.auth = auth;
        this.database = database;
        this.schedulerProvider = schedulerProvider;
        this.view = view;
        disposable = new CompositeDisposable();
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
    public void onDeleteAccountPress() {
    view.showAuthCard(true);
    }

    @Override
    public void onDeleteAccountConfirmed(String password) {

        view.showAuthCard(false);
        view.showProgressIndicator(true);
        disposable.add(
                auth.reauthenticateUser(password)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {

                                deleteProfileFromDatabase();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(R.string.error_authenticating_credentails);
                            }
                        })
        );
    }
    private void deleteUser() {
        disposable.add(
                auth.deleteUser()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.showProgressIndicator(false);
                                view.startLogInActivity();
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
        disposable.add(
                auth.getUser()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(
                                new DisposableMaybeObserver<User>() {

                                    @Override
                                    public void onError(Throwable e) {
                                        view.makeToast(R.string.error_retrieving_data);
                                        view.startLogInActivity();
                                    }

                                    @Override
                                    public void onComplete() {
                                        view.showProgressIndicator(false);
                                    }

                                    @Override
                                    public void onSuccess(User user) {
                                        view.showProgressIndicator(false);
                                        ProfileSettingsPresenter.this.uid = user.getUserId();
                                    }
                                }
                        )
        );
    }

    private void deleteProfileFromDatabase() {
        disposable.add(
                database.deleteProfile(uid)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                deleteUser();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }
}
