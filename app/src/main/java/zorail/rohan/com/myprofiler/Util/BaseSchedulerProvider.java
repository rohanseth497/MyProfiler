package zorail.rohan.com.myprofiler.Util;

import io.reactivex.Scheduler;

/**
 * Created by Ryan on 04/03/2017.
 */

public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
