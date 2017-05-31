package zorail.rohan.com.myprofiler.data;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zorail on 16-May-17.
 */

public interface AuthSource {

    Completable createAccount(Credentials cred);
    Completable attemptLogin(Credentials cred);
    Maybe<User> getUser();
    Completable logUserOut();
    Maybe<User> createAndGet(Credentials cred);

    Completable deleteUser();
    Completable reauthenticateUser(String password);

    void setReturnFail(boolean bool);
}
