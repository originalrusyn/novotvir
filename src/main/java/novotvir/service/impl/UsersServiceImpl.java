package novotvir.service.impl;

import novotvir.persistence.domain.Authority;
import novotvir.persistence.domain.User;
import novotvir.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

// @author Titov Mykhaylo (titov) on 14.07.2014.
@Service
public class UsersServiceImpl implements UsersService {

    private static final String USER = "user";
    private static final String AUTHORITIES = "authorities";

    public static final Map<String, Class<?>> aliasClassMap;

    static {
        Map<String, Class<?>> map = new HashMap();
        map.put(USER, User.class);
        map.put(AUTHORITIES, Authority.class);

        aliasClassMap = Collections.unmodifiableMap(map);
    }

    @PersistenceContext EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Set<User> findUsers(String criteria) {
        Query query = entityManager.createQuery("select user from User "+USER+" left join user.authorities "+AUTHORITIES+" where " + criteria, User.class);
        return new HashSet<>(query.getResultList());
    }
}
