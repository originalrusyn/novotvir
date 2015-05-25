package novo.tvir.access.signup.social.google.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;

// @author Titov Mykhaylo (titov) on 28.04.2015.
public class ErrorDialogFragment extends DialogFragment {

    public static final String DIALOG_ERROR = "dialog_error";

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int errorCode = this.getArguments().getInt(DIALOG_ERROR);
        return GooglePlayServicesUtil.getErrorDialog(errorCode, this.getActivity(), GoogleSignUpFragment.REQUEST_RESOLVE_ERROR);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {((GoogleSignUpFragment) getTargetFragment()).onDialogDismissed();}
}
