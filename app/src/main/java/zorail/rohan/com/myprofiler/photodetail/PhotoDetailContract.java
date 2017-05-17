package zorail.rohan.com.myprofiler.photodetail;

import android.support.annotation.StringRes;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;


/**
 * Created by Ryan on 04/01/2017.
 */

public interface PhotoDetailContract {

    //You must specify Type of Presenter
        interface View extends BaseView<Presenter> {

            void setBitmap();

            void startProfilePageActivity();

            void startPhotoGalleryActivity();

            void makeToast(@StringRes int message);

            void setPresenter(Presenter presenter);

            void showProgressIndicator(boolean show);

            String getPhotoURL();
        }

    interface Presenter extends BasePresenter {
            void onBackButtonPress();

            void onDoneButtonPress();

            void onImageLoaded();

            void onImageLoadFailure();
    }
}
