package zorail.rohan.com.myprofiler.profilesettings;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;

/**
 * Created by zorail on 16-May-17.
 */

public interface ProfileSettingsContract {

    interface View extends BaseView<Presenter> {
        void startLogInActivity();

        void showAuthCard(boolean show);

        void showProgressIndicator(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onDeleteAccountPress();

        void onDeleteAccountConfirmed(String password);
    }
}
