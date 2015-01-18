package command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Locale;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

// @author: Mykhaylo Titov on 17.01.15 17:08.
@Getter
@Accessors(chain = true)
@RequiredArgsConstructor
public abstract class AbstractCommand {

    protected static final String EMAIL = "email";
    protected static final String TOKEN = "token";
    protected static final String ACCEPT_LANGUAGE = "Accept-Language";
    protected static final String SET_COOKIE = "Set-Cookie";

    protected final HttpEntity<MultiValueMap<String, String>> reqEntity;
    protected final ResponseEntity<?> respEntity;

    protected static HttpHeaders getHttpHeaders(Locale locale) {
        HttpHeaders headers = new HttpHeaders ();
        headers.setAccept(asList(APPLICATION_JSON));
        headers.add(ACCEPT_LANGUAGE, locale.toLanguageTag());
        return headers;
    }
}
