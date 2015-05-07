package web.account.service;

import web.persistence.domain.User;
import web.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// @author: Mykhaylo Titov on 18.01.15 21:39.
@Service("accountService")
public class UserServiceImpl {
    @Resource UserRepository userRepository;

    public User getUser(long userId){
        return userRepository.findOne(userId);
    }
}
