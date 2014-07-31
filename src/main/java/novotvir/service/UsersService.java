package novotvir.service;

import novotvir.persistence.domain.User;

import java.util.Set;

/**
 * @author Titov Mykhaylo (titov) on 14.07.2014.
 */
public interface UsersService {
    Set<User> findUsers(String q);
}
