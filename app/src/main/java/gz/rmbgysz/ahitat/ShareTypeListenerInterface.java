package gz.rmbgysz.ahitat;

import android.support.v4.app.DialogFragment;

/**
 * Created by gz on 2017. 04. 17..
 */

public interface ShareTypeListenerInterface {
    void gotPositiveResultFromChoiceDialog(DialogFragment dialogFragment, int choosedId);
}