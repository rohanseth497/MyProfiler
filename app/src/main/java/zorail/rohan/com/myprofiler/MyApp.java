package zorail.rohan.com.myprofiler;

import android.app.Application;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import zorail.rohan.com.myprofiler.di.DaggerNetComponent;
import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.NetModule;

/**
 * Created by zorail on 24-May-17.
 */

public class MyApp extends Application {
    NetComponent component;
    RealmConfiguration realmConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
         realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        component = DaggerNetComponent.builder().netModule(new NetModule()).build();
    }
    public NetComponent getComponent()
    {
        return component;
    }

    public RealmConfiguration getCOnfig()
    {
        return realmConfiguration;
    }
}
