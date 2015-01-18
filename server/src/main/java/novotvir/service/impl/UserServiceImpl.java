package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// @author: Mykhaylo Titov on 18.01.15 21:39.
@Service("accountService")
public class UserServiceImpl implements UserService {
    @Resource UserRepository userRepository;

    @Override
    public User getUser(long userId){
        return userRepository.findOne(userId);
    }
}
