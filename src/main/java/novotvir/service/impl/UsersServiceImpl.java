package novotvir.service.impl;

import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Titov Mykhaylo (titov) on 14.07.2014.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @PersistenceContext EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Set<User> findUsers(String q) {
        Query query = entityManager.createQuery("select user from User user left join user.authorities authority where "+q, User.class);
        return new HashSet<User>(query.getResultList());
    }
}
