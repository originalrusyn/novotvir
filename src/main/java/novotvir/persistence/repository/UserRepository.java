package novotvir.persistence.repository;

import novotvir.enums.Role;
import novotvir.persistence.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.repository.annotation.RestResource;

import java.util.Set;

// @author: Titov Mykhaylo (titov) 21.06.13 13:21
@RestResource(rel ="users", path = "user")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByName(String userName);

    @Query("from User u " +
            "join FETCH u.authorities a " +
            "where " +
            "u.id in :ids " +
            "or u.name like %:keyWord% " +
            "or u.email like %:keyWord% " +
            "or u.activated in :activations " +
            "or a.role in :roles " +
            "order by u.id, u.name, u.email, u.activated")
    Set<User> findByIdOrNameOrEmailOrActivatedOrBlocked(@Param("ids") Set<Long> ids, @Param("keyWord") String keyWord, @Param("roles") Set<Role> roles, @Param("activations") Set<Boolean> activations);
}
