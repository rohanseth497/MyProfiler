package zorail.rohan.com.myprofiler.data.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

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

    Maybe<String> downloadUrl(User user);

    Maybe<String> storeAndGet(Uri uri,String name);

    void setReturnFail(boolean bool);

    void setReturnEmpty(boolean bool);
}
