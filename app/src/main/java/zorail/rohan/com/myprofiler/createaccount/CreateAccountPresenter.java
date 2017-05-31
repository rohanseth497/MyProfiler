package zorail.rohan.com.myprofiler.createaccount;


import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.realm.Realm;
import io.realm.RealmResults;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.Util.SessionManager;
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
    User currentUser;
    SchedulerProvider schedulerProvider;
    DataBaseSource database;
    SessionManager sessionManager;
    Realm realm;

    @Inject
    public CreateAccountPresenter(CreateAccountContract.View view, DataBaseSource database, AuthSource auth, SchedulerProvider schedulerProvider, CompositeDisposable disposable, Context context)
    {
        this.schedulerProvider = schedulerProvider;
        this.auth = auth;
        this.view = view;
        this.database = database;
        this.disposable = disposable;
        sessionManager = new SessionManager(context);
        view.setPresenter(this);
    }

    @Override
    public void onCreateAccountClick() {

        if(validateAccountCredentials(view.getName(),view.getEmail(),view.getPassword(),view.getPasswordConfirmation())) {
          attemptAccountCreation(new Credentials(view.getPassword(), view.getName(), view.getEmail()));
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        realm.close();
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
    private void attemptAccountCreation(final Credentials cred) {

           view.showProgressIndicator(true);
          disposable.add(auth.createAndGet(cred)
          .subscribeOn(schedulerProvider.io())
          .observeOn(schedulerProvider.ui())
          .subscribeWith(new DisposableMaybeObserver<User>(){
              @Override
              public void onSuccess(@NonNull User user) {
                  sessionManager.setLogin(true);
                 CreateAccountPresenter.this.currentUser = user;
                  addUserProfileToDatabase();
              }

              @Override
              public void onError(@NonNull Throwable e) {
                  view.showProgressIndicator(false);
                  view.makeToast(e.getMessage());
              }

              @Override
              public void onComplete() {
                  view.showProgressIndicator(false);
              }
          })
          );
    }
    private void addUserProfileToDatabase() {
        final Profile profile = new Profile("","",currentUser.getUserId(),currentUser.getEmail(),"",view.getName());
        disposable.add(database.createProfile(profile)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        realm.beginTransaction();
                        realm.copyToRealm(profile);
                        realm.commitTransaction();
                        view.showProgressIndicator(false);
                        view.startProfilePageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.makeToast(e.getMessage());
                        view.showProgressIndicator(false);
                    }
                })
        );
    }
    public void initializeRealm(Realm realm)
    {
        CreateAccountPresenter.this.realm  =realm;
    }
}
