package zorail.rohan.com.myprofiler.profiledetail;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.User;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;
import zorail.rohan.com.myprofiler.data.database.Profile;

/**
 * Created by zorail on 17-May-17.
 */

public class ProfileDetailPresenter implements ProfileDetailContract.Presenter {

    private DataBaseSource database;
    private ProfileDetailContract.View view;
    private CompositeDisposable disposable;
    private SchedulerProvider schedulerProvider;
    private Profile currentProfile;
    Realm realm;
    @Inject
    public ProfileDetailPresenter(DataBaseSource database,ProfileDetailContract.View view,SchedulerProvider schedulerProvider){

        this.database = database;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        disposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        setTexts();
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void onBackButtonClick() {
        view.startProfilePageActivity();
    }

    @Override
    public void onDoneButtonClick() {
        currentProfile.setBio(view.getBio());
        currentProfile.setInterests(view.getInterests());
        disposable.add(
                database.updateProfile(currentProfile)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                realm.beginTransaction();
                                realm.copyToRealmOrUpdate(currentProfile);
                                realm.commitTransaction();
                                view.startProfilePageActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.makeToast(e.getMessage());
                            }
                        })
        );
    }

    public void initializeRealm(Realm realm)
    {
        ProfileDetailPresenter.this.realm = realm;
    }
    private void setTexts()
    {
        if(!realm.isEmpty())
        {
            Profile profile = realm.where(Profile.class).findAll().last();
            ProfileDetailPresenter.this.currentProfile = realm.copyFromRealm(profile);
            view.setBioText(profile.getBio());
            view.setInterestsText(profile.getInterests());

        }
    }

}
