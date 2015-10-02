package web.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import web.persistence.domain.User;

// @author: Titov Mykhaylo (titov) 21.06.13 13:21
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findOne(Long id);

    User findByName(String userName);
}
