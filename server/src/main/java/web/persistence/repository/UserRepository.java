package web.persistence.repository;

import web.persistence.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

// @author: Titov Mykhaylo (titov) 21.06.13 13:21
@RestResource(rel ="users", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String userName);

}
