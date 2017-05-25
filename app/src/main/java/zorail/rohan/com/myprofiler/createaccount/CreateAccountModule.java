package zorail.rohan.com.myprofiler.createaccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class CreateAccountModule {
    CreateAccountContract.View view;
    public CreateAccountModule(CreateAccountContract.View view)
    {
        this.view = view;
    }
    @UserScope
    @Provides
    CreateAccountContract.View provideView()
    {return  view;}
}
