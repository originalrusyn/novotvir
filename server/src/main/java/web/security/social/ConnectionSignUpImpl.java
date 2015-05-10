package web.security.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

// @author: Mykhaylo Titov on 09.05.15 15:40.
public class ConnectionSignUpImpl implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        return connection.fetchUserProfile().getEmail();
    }
}
