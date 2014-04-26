package novotvir.service;

import novotvir.dto.UserRegDetailsDto;
import novotvir.persistence.domain.User;

/**
 * @author Titov Mykhaylo (titov) on 17.03.14.
 */
public interface UserService {
    User updateLastWebLoginInfo(User user);
    User findOne(long userId);
    User findByName(String userName);
    User registerUser(UserRegDetailsDto userRegDetailsDto);
    User save(User user);
}
