package novotvir.service;

import novotvir.persistence.domain.User;

// @author: Mykhaylo Titov on 18.01.15 21:39.
public interface UserService {
    User getUser(long userId);
}
