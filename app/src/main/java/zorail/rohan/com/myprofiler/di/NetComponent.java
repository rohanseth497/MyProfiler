package zorail.rohan.com.myprofiler.di;

import java.util.Map;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.AuthSource;
import zorail.rohan.com.myprofiler.data.database.DataBaseSource;
import zorail.rohan.com.myprofiler.di.Binder.Binder;

/**
 * Created by zorail on 24-May-17.
 */
@Singleton
@Component(modules = {NetModule.class, Binder.class})
public interface NetComponent {
    CompositeDisposable disposable();
    SchedulerProvider provider();
    AuthSource source();
    DataBaseSource databaseSource();
    Map<Class<?>,Provider<SubComponentBuilder>> subcomponentBuilders();
}
