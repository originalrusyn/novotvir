package novotvir.service.impl;

import novotvir.enums.Role;
import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.service.UsersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;
import static org.apache.commons.lang.math.NumberUtils.isNumber;
import static org.apache.commons.lang.math.NumberUtils.toLong;

/**
 * @author Titov Mykhaylo (titov) on 14.07.2014.
 */
@Service
public class UsersServiceImpl implements UsersService {

    @Resource(name = "userRepository") UserRepository userRepository;

    private class QueriedParamExtractor{
        private final String q;
        private final Set<Long> ids;
        private final Set<Boolean> activations;
        private final Set<Role> roles;
        private String lastKeyWord;

        private QueriedParamExtractor(String q) {
            this.q = q;

            Set<Long> ids = new HashSet<>();
            Set<Boolean> activations = new HashSet<>();
            Set<Role> roles = new HashSet<>();

            String[] keyWords = q.split(" ");
            for (int i = 0; i < keyWords.length; i++) {
                lastKeyWord = keyWords[i];
                if(isNumber(q)) {
                    ids.add(toLong(lastKeyWord));
                }else {
                    if (lastKeyWord.equals("activated")) activations.add(true);
                    else if (lastKeyWord.equals("disabled")) activations.add(false);
                    for (Role role : Role.values()) {
                        if (role.name().equals(lastKeyWord)) {
                            roles.add(role);
                            break;
                        }
                    }
                }
            }

            this.ids = unmodifiableSet(ids);
            this.activations = unmodifiableSet(activations);
            this.roles = unmodifiableSet(roles);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<User> findUsers(String q) {
        QueriedParamExtractor paramExtractor = new QueriedParamExtractor(q);
        return userRepository.findByIdOrNameOrEmailOrActivatedOrBlocked(paramExtractor.ids, paramExtractor.lastKeyWord, paramExtractor.roles, paramExtractor.activations);
    }
}
