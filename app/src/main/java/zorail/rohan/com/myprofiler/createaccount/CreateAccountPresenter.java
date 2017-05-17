package zorail.rohan.com.myprofiler.createaccount;


import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.Credentials;
import zorail.rohan.com.myprofiler.data.User;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;
import zorail.rohan.com.myprofiler.data.database.Profile;

/**
 * Created by zorail on 16-May-17.
 */

public class CreateAccountPresenter implements CreateAccountContract.Presenter {

    CreateAccountContract.View view;
    CompositeDisposable disposable;
    AuthSource auth;
    SchedulerProvider schedulerProvider;
    private DataBaseSource database;

    public CreateAccountPresenter(CreateAccountContract.View view,DataBaseSource database,AuthSource auth,SchedulerProvider schedulerProvider)
    {
        this.schedulerProvider = schedulerProvider;
        this.auth = auth;
        this.view = view;
        this.database = database;
        disposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void onCreateAccountClick() {

        if(validateAccountCredentials(view.getName(),view.getEmail(),view.getPassword(),view.getPasswordConfirmation()))
            attemptAccountCreation(new Credentials(view.getPassword(),view.getName(),view.getEmail()));
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }
    public boolean validateAccountCredentials(String name, String email,
                                              String password, String passwordConfirmation) {
        if (email.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (name.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (password.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (passwordConfirmation.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
            return false;
        } else if (!email.contains("@")) {
            view.makeToast(R.string.error_invalid_email);
            return false;
        } else if (password.length() > 19) {
            view.makeToast(R.string.error_password_too_long);
            return false;
        } else if (!passwordConfirmation.equals(password)) {
            view.makeToast(R.string.error_password_mismatch);
            return false;
        } else {
            return true;
        }
    }
    private void attemptAccountCreation(Credentials cred) {

            view.showProgressIndicator(true);
            disposable.add(
                    auth.createAccount(cred)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .subscribeWith(new DisposableCompletableObserver() {
                                @Override
                                public void onComplete() {
                                    getUser();
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                    view.showProgressIndicator(false);
                                }
                            })
            );
    }
    public void getUser() {
        disposable.add(auth.getUser()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableMaybeObserver<User>() {
                    @Override
                    public void onComplete() {
                        //TODO handle this as an issue
                        view.showProgressIndicator(false);
                    }

                    @Override
                    public void onSuccess(User user) {

                        addUserProfileToDatabase(user.getEmail(), user.getUserId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showProgressIndicator(false);
                        view.makeToast(e.getMessage());
                    }
                })

        );
    }
    private void addUserProfileToDatabase(String email, String uid) {
        final Profile profile = new Profile(
                "",
                "",
                uid,
                email,
                "",
                view.getName());

                 disposable.add(database.createProfile(profile)
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
}
