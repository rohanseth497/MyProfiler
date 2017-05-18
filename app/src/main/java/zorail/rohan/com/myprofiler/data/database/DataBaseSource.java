package zorail.rohan.com.myprofiler.data.database;

import android.net.Uri;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import zorail.rohan.com.myprofiler.data.User;

/**
 * Created by zorail on 16-May-17.
 */

public interface DataBaseSource {

    Completable createProfile(Profile profile);

    Maybe<Profile> getProfile(String uid);

    Completable deleteProfile(String uid);

    Completable updateProfile(Profile profile);

    Completable uploadImage(Profile profile);

    Single<Uri> downloadUrl(User user);

    void setReturnFail(boolean bool);

    void setReturnEmpty(boolean bool);
}
