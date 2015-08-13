package admin.user.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import web.persistence.domain.Authority;
import web.persistence.domain.EmailAddress;
import web.persistence.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableMap;

// @author Titov Mykhaylo (titov) on 14.07.2014.
@Service
public class UserServiceImpl {

    private static final String USER = "user";
    private static final String AUTHORITIES = "authorities";
    private static final String EMAIL_ADDRESSES = "emailAddresses";
    private static final String PRIMARY_EMAIL_ADDRESS = "primaryEmailAddress";

    public static final Map<String, Class<?>> aliasClassMap;

    static {
        Map<String, Class<?>> map = new HashMap<>();
        map.put(USER, User.class);
        map.put(AUTHORITIES, Authority.class);
        map.put(EMAIL_ADDRESSES, EmailAddress.class);
        map.put(PRIMARY_EMAIL_ADDRESS, EmailAddress.class);

        aliasClassMap = unmodifiableMap(map);
    }

    @PersistenceContext EntityManager entityManager;

    @SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN", "SQL_INJECTION_JPA"})
    @Transactional(readOnly = true)
    public Set<User> findUsers(String criteria, Pageable pageable) {
        Assert.doesNotContain(";", criteria);
        Assert.doesNotContain(" limit ", criteria);
        Assert.notNull(pageable);
        Query query = entityManager.
                createQuery("select user from User " + USER + " join user.primaryEmailAddress "+ PRIMARY_EMAIL_ADDRESS +" join user.emailAddresses "+ EMAIL_ADDRESSES + " left join user.authorities " + AUTHORITIES + " where " + criteria, User.class)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked") Set<User> set = new HashSet<>(query.getResultList());
        return set;
    }
}
