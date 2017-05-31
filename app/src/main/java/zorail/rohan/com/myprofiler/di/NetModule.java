package zorail.rohan.com.myprofiler.di;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.FireBaseAuthService;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;
import zorail.rohan.com.myprofiler.data.database.FirebaseDatabaseService;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class NetModule {
    @Singleton
    @Provides
    CompositeDisposable provideDisposable()
    {
        return new CompositeDisposable();
    }
    @Singleton
    @Provides
    AuthSource provideAuthSource()
    {
        return FireBaseAuthService.getInstance();
    }
    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider()
    {
        return SchedulerProvider.getInstance();
    }
    @Singleton
    @Provides
    DataBaseSource provideDataBaseSource(){return FirebaseDatabaseService.getInstance();}
}
