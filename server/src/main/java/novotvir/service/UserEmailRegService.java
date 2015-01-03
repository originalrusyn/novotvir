package novotvir.service;

import novotvir.dto.UserRegDetailsDto;
import novotvir.persistence.domain.User;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
public interface UserEmailRegService {
    User registerUser(UserRegDetailsDto userRegDetailsDto);
}
