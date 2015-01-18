package command;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

// @author: Mykhaylo Titov on 17.01.15 18:40.
public interface Command {

    HttpEntity<MultiValueMap<String, String>> getReqEntity();
    ResponseEntity<?> getRespEntity();

    boolean isRespValid();
}
