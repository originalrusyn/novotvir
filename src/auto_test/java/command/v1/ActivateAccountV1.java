package command.v1;

import command.AbstractCommand;
import command.ActivateAccount;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.Locale;

// @author: Mykhaylo Titov on 17.01.15 19:27.
public class ActivateAccountV1 extends AbstractCommand implements ActivateAccount {

    public ActivateAccountV1(HttpEntity<MultiValueMap<String, String>> reqEntity, ResponseEntity<?> respEntity) {
        super(reqEntity, respEntity);
    }

    public static HttpEntity<MultiValueMap<String, String>> getHttpEntity(Locale locale) {
        return new HttpEntity<>(null, getHttpHeaders(locale));
    }

    @Override
    public boolean isRespValid() {
        return false;
    }
}
