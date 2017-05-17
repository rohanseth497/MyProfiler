package zorail.rohan.com.myprofiler;

import android.support.annotation.StringRes;

/**
 * Created by zorail on 16-May-17.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void makeToast(@StringRes int stringId);

    void makeToast(String message);
}
