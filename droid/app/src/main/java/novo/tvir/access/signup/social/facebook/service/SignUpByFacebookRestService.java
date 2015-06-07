package novo.tvir.access.signup.social.facebook.service;

import dto.AccountDto;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;

// @author: Mykhaylo Titov on 12.04.15 19:50.
@Rest(converters = { FormHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class })
public interface SignUpByFacebookRestService extends RestClientRootUrl{

    @Post("/auth/facebook?installedApp=true&signUp=true")
    ResponseEntity<AccountDto> signUp(LinkedMultiValueMap<String, String> formData);
}
