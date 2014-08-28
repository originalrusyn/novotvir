package novotvir.persistence.repository;

import novotvir.persistence.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Set;

// @author: Titov Mykhaylo (titov) 21.06.13 13:21
@RestResource(rel ="users", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String userName);

    @Query( nativeQuery=true,
            value = "select u.* from users u " +
            "join authorities a on u.id=a.userId " +
            "where " +
            "cast(u.id as text) similar to :q " +
            "or u.name similar to :q " +
            "or u.email similar to :q " +
            "or cast(u.activated as text) similar to :q " +
            "or a.role similar to  :q " +
            "order by u.id, u.name, u.email, u.activated")
    Set<User> findByIdOrNameOrEmailOrActivatedOrBlocked(@Param("q") String q);

}
