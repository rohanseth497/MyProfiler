package zorail.rohan.com.myprofiler;

import android.app.Application;


import zorail.rohan.com.myprofiler.di.DaggerNetComponent;
import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.NetModule;

/**
 * Created by zorail on 24-May-17.
 */

public class MyApp extends Application {
    NetComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerNetComponent.builder().netModule(new NetModule()).build();
    }
    public NetComponent getComponent()
    {
        return component;
    }
}
