package novo.tvir.access.signup.email.service;

import dto.AccountDto;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;

// @author: Mykhaylo Titov on 29.03.15 18:56.
@Rest(converters = { FormHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class })
public interface SignUpByEmailRestService extends RestClientRootUrl {

    @Post("/signup")
    ResponseEntity<AccountDto> signUp(LinkedMultiValueMap<String, String> formData);

    @Post("/auth/google?installedApp=true&signUp=true")
    ResponseEntity<AccountDto> signupByGoogle(LinkedMultiValueMap<String, String> formData);

    @Post("/auth/facebook?installedApp=true&signUp=true")
    ResponseEntity<AccountDto> signupByFacebook(LinkedMultiValueMap<String, String> formData);

}
