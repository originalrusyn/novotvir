package novotvir.service;

import novotvir.persistence.domain.User;

/**
 * @author Titov Mykhaylo (titov) on 16.05.2014.
 */
public interface UserActivationService {

    User activate(String userName, String activationToken);
}
