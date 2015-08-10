package web.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import web.persistence.domain.Client;

import java.util.Set;

// @author Titov Mykhaylo on 10.08.2015.
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    @Query("select client.id from Client client where client.pushToken is not null and client.id not in :lockedProcessingIds")
    Set<Long> findAllItems(@Param("lockedProcessingIds") Set<Long> lockedProcessingIds);
}
