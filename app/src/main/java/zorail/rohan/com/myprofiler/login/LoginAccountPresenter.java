package zorail.rohan.com.myprofiler.login;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.Credentials;

/**
 * Created by zorail on 16-May-17.
 */

public class LoginAccountPresenter implements LoginAccountContract.Presenter {

    LoginAccountContract.View view;
    CompositeDisposable disposable;
    AuthSource auth;
    SchedulerProvider schedulerProvider;
    @Inject
    public LoginAccountPresenter(LoginAccountContract.View view,AuthSource auth,SchedulerProvider schedulerProvider,CompositeDisposable disposable){
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.auth = auth;
        this.disposable = disposable;
        view.setPresenter(this);
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void onLogInClick() {

        validateCredetials(view.getEmail(),view.getPassword());
    }

    @Override
    public void onCreateClick() {
        view.startCreateAccountActivity();
    }


    private void validateCredetials(String email,String password){
        if (email.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
        } else if (password.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
        } else if (!email.contains("@")) {
            view.makeToast(R.string.error_invalid_email);
        } else if (password.length() > 19) {
            view.makeToast(R.string.error_password_too_long);
        } else {
            attemptLogIn(new Credentials(password, "", email));
        }
    }
    public void attemptLogIn(Credentials cred) {
        view.showProgressIndicator(true);
        disposable.add(
                auth.attemptLogin(cred)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.startProfileActivity();
                                view.showProgressIndicator(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.toString());
                            }
                        })
        );
}
}
