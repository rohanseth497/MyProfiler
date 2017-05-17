package zorail.rohan.com.myprofiler.photogallery;

import android.app.Activity;
import android.support.annotation.StringRes;

import java.util.List;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;
import zorail.rohan.com.myprofiler.data.photos.Photo;

/**
 * Created by zorail on 17-May-17.
 */

public interface PhotoGalleryContract {

    interface View extends BaseView<Presenter> {
        void setAdapterData(List<Photo> photos);

        void setNoListDataFound();

        Activity getActivityContext();

        void makeToast(@StringRes int message);

        void setPresenter(Presenter presenter);

        void startPhotoDetailActivity(String photoURL);

        void showProgressIndicator(boolean show);

    }

    interface Presenter extends BasePresenter {
        void onPhotoItemClick(int itemPosition);
    }
}
