package zorail.rohan.com.myprofiler.profiledetail;

import android.support.annotation.StringRes;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;

/**
 * Created by zorail on 17-May-17.
 */

public interface ProfileDetailContract {

    interface View extends BaseView<Presenter> {

        void setBioText(String bio);

        void setInterestsText(String interests);

        String getInterests();

        String getBio();

        void startProfilePageActivity();

        void setPresenter(Presenter presenter);

        void makeToast(@StringRes int message);
    }

    interface Presenter extends BasePresenter {
        void onBackButtonClick();

        void onDoneButtonClick();
    }
}
