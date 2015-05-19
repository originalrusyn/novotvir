package common.security.credential;

import web.persistence.domain.User;

// @author Titov Mykhaylo (titov) on 11.01.14 20:32
public interface SecurityContextDetails {
    long getId();

    String getUsername();

    User getUser();
}
