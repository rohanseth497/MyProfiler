package zorail.rohan.com.myprofiler.createaccount;

import android.support.annotation.StringRes;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;

/**
 * Created by zorail on 16-May-17.
 */

public interface CreateAccountContract {

    interface View extends BaseView<Presenter> {
        void makeToast(@StringRes int stringId);

        String getEmail();

        String getPassword();

        String getPasswordConfirmation();

        String getName();

        //TODO: add name input to this component

        void startLoginActivity();

        void startProfilePageActivity();

        void setPresenter(CreateAccountContract.Presenter presenter);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onCreateAccountClick();

        void subscribe();

        void unsubscribe();
    }

}
