package zorail.rohan.com.myprofiler.profilepage;

import zorail.rohan.com.myprofiler.BasePresenter;
import zorail.rohan.com.myprofiler.BaseView;

/**
 * Created by zorail on 16-May-17.
 */

public interface ProfilePageContract {
    interface View extends BaseView<Presenter> {
        void setPresenter(Presenter presenter);

        void setName (String name);

        void setEmail (String email);

        void setBio (String bio);

        void setInterests (String interests);

        void setProfilePhotoURL (String profilePhotoURL);

        void setDefaultProfilePhoto ();

        void startPhotoGalleryActivity();

        void startProfileDetailActivity();

        void startProfileSettingsActivity();

        void showLogoutSnackbar ();

        void startLoginActivity();

        void setThumbnailLoadingIndicator(boolean show);

        void setDetailLoadingIndicators(boolean show);
    }

    interface Presenter extends BasePresenter {
        void onThumbnailClick();

        void onEditProfileClick();

        void onLogoutClick();

        void onLogoutConfirmed();

        void onAccountSettingsClick();

        void onThumbnailLoaded();
    }
}
