package zorail.rohan.com.myprofiler.login;



import dagger.Component;

import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Component(modules = LoginModule.class,dependencies = NetComponent.class)
public interface LoginComponent {
    void inject(LoginAccountFragment fragment);
}
