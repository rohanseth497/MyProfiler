package zorail.rohan.com.myprofiler.createaccount;


import dagger.Component;
import zorail.rohan.com.myprofiler.di.NetComponent;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@UserScope
@Component(modules = CreateAccountModule.class,dependencies = NetComponent.class)
public interface CreateAccountComponent {
    void inject(CreateAccountFragment fragment);
}
