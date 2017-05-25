package zorail.rohan.com.myprofiler.login;

import android.support.annotation.StringRes;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;

/**
 * Created by zorail on 16-May-17.
 */

public interface LoginAccountContract {

    interface View extends BaseView<Presenter> {
        void makeToast(@StringRes int stringId);

        void makeToast(String message);

        String getEmail();

        String getPassword();

        void startProfileActivity();

        void startCreateAccountActivity();

        void setPresenter(Presenter presenter);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onLogInClick();

        void onCreateClick();


    }
}
