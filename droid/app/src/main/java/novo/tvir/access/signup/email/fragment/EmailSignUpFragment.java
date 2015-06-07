package novo.tvir.access.signup.email.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.PasswordFormatValidator;
import novo.tvir.access.signin.email.task.UserSignInTask;
import org.androidannotations.annotations.*;
import util.EmailFormatValidator;

import java.util.ArrayList;
import java.util.List;

// @author: Mykhaylo Titov on 23.05.15 12:45.
@Slf4j
@EFragment(R.layout.fragment_email_signup)
public class EmailSignUpFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @ViewById(R.id.email) AutoCompleteTextView emailView;
    @ViewById(R.id.password) EditText passwordView;
    @ViewById(R.id.email_sign_up_form) View emailSignUpFormView;

    //@NonConfigurationInstance
    @Bean UserSignInTask userSignInTask;

    @Bean EmailFormatValidator emailFormatValidator;
    @Bean PasswordFormatValidator passwordFormatValidator;

    A a;

    public void onConnectionChanged(boolean connected) {
        emailSignUpFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
    }

    public interface A{
        void setProgressBarVisible(boolean visible);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            a = (A) activity;
        }catch (Exception e){
            log.error("{} must implement {}", activity, A.class, e);
        }
    }

    public void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, ContactsContract.Contacts.Data.CONTENT_DIRECTORY);
        String selection = ContactsContract.Contacts.Data.MIMETYPE + " = ?";
        String[] selectionArgs = {ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE}; // Select only email addresses.
        String sortOrder = ContactsContract.Contacts.Data.IS_PRIMARY + " DESC"; // Show primary email addresses first. Note that there won't be a primary email address if the user hasn't specified one.

        // Retrieve data rows for the device user's 'profile' contact.
        return new CursorLoader(getActivity(), uri, ProfileQuery.PROJECTION, selection, selectionArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        emailView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, emailAddressCollection));
    }

    @EditorAction(R.id.password)
    public boolean onPasswordEditorAction(int id) {
        if (id == R.id.sign_up || id == EditorInfo.IME_NULL) {
            attemptSignUp();
            return true;
        }
        return false;
    }


    @Click(R.id.email_sign_up_button)
    public void attemptSignUp() {
        emailView.setError(null);
        passwordView.setError(null);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !passwordFormatValidator.isValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!emailFormatValidator.isValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            a.setProgressBarVisible(true);
            userSignInTask.signIn(email, password);
        }
    }

    public void onSignUpComplete(boolean success) {
        a.setProgressBarVisible(false);

        if (success) {
            getActivity().finish();
        } else {
            passwordView.setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
        }
    }
}
