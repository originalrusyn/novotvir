package novo.tvir.access.signup.service;

import dto.AccountDto;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;

// @author: m on 29.03.15 18:56.
@Rest(converters = { FormHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class })
public interface SignUpRestService extends RestClientRootUrl {

    @Post("/signup")
    ResponseEntity<AccountDto> signup(LinkedMultiValueMap<String, String> formData);

}
