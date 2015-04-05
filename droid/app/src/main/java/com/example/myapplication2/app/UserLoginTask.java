package com.example.myapplication2.app;

import dto.AccountDto;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// @author: m on 29.03.15 17:21.
@EBean
public class UserLoginTask {

    @RestService SignInService signInService;

    @RootContext LoginActivity loginActivity;

    @Background(id = "login")
    void login(String mEmail, String mPassword) {
        mEmail="dev@i.ua";
        mPassword = "password";
        //mPassword = "ddd793fce36d636ef7870e0394455af0";

        String token;
        try {
            String salt ="Роисся вперде";
            String message = salt + mPassword +salt;

            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(message.getBytes(Charset.forName("UTF-8")));
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            token = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            onLoginComplete(false);
            return;
        }

        token = "345fb8b66a212fedc1e5ae7e7ba69442";

        signInService.setRootUrl("http://192.168.56.1:8080/");
        LinkedMultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", mEmail);
        formData.add("token", token);
        ResponseEntity<AccountDto> accountDto = signInService.signin(formData);

        onLoginComplete(false);
    }

    @UiThread
    void onLoginComplete(boolean success) {
        loginActivity.onLoginComplete(success);
    }

}
