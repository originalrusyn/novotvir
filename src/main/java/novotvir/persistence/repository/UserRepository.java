package novotvir.persistence.repository;

import novotvir.persistence.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.repository.annotation.RestResource;

/**
 * @autor: Titov Mykhaylo (titov)
 * 21.06.13 13:21
 */
@RestResource(rel ="users", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String userName);
}
