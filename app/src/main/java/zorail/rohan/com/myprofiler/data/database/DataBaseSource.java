package zorail.rohan.com.myprofiler.data.database;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Created by zorail on 16-May-17.
 */

public interface DataBaseSource {

    Completable createProfile(Profile profile);

    /**
     *
     */
    Maybe<Profile> getProfile(String uid);

    /**
     *
     */
    Completable deleteProfile(String uid);

    /**
     *
     */
    Completable updateProfile(Profile profile);

    Completable uploadImage(Profile profile);


    void setReturnFail(boolean bool);

    void setReturnEmpty(boolean bool);
}
