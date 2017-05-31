package zorail.rohan.com.myprofiler.profilepage;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import io.reactivex.internal.operators.completable.CompletableOnErrorComplete;
import zorail.rohan.com.myprofiler.di.UserScope;

/**
 * Created by zorail on 24-May-17.
 */
@Module
public class ProfilePageModule {
    ProfilePageContract.View view;
    Context context;
    public ProfilePageModule(ProfilePageContract.View view,Context context)
    {
        this.context = context;
        this.view = view;
    }
    @UserScope
    @Provides
    ProfilePageContract.View provideView()
    {
        return view;
    }
    @UserScope
    @Provides
    Context provideContext()
    {
        return context;
    }

}
