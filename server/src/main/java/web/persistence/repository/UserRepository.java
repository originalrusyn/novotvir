package web.persistence.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import web.persistence.domain.User;

import static javax.persistence.LockModeType.PESSIMISTIC_READ;

// @author: Titov Mykhaylo (titov) 21.06.13 13:21
@RestResource(rel ="users", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Lock(PESSIMISTIC_READ)
    User findOne(Long id);

    @Lock(PESSIMISTIC_READ)
    User findByName(String userName);

}
