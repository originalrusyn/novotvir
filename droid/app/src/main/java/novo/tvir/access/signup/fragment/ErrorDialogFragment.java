package novo.tvir.access.signup.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import novo.tvir.access.signup.activity.SignUpActivity;

// @author Titov Mykhaylo (titov) on 28.04.2015.
public class ErrorDialogFragment extends DialogFragment {

    public static final String DIALOG_ERROR = "dialog_error";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get the error code and retrieve the appropriate dialog
        int errorCode = this.getArguments().getInt(DIALOG_ERROR);
        return GooglePlayServicesUtil.getErrorDialog(errorCode,
                this.getActivity(), SignUpActivity.REQUEST_RESOLVE_ERROR);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        ((SignUpActivity) getActivity()).onDialogDismissed();
    }
}
