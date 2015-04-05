package com.example.myapplication2.app;

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
public interface SignInService extends RestClientRootUrl {

    @Post("/signin")
    ResponseEntity<AccountDto> signin(LinkedMultiValueMap<String, String> formData);

}
