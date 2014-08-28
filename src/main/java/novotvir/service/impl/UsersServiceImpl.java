package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author Titov Mykhaylo (titov) on 14.07.2014.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Resource(name = "userRepository") UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Set<User> findUsers(String q) {
        return userRepository.findByIdOrNameOrEmailOrActivatedOrBlocked(q);
    }
}
