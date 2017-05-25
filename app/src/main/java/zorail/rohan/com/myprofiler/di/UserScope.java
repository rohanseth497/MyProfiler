package zorail.rohan.com.myprofiler.di;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by zorail on 24-May-17.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
