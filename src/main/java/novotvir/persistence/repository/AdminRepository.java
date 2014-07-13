package novotvir.persistence.repository;

import novotvir.persistence.domain.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;

// @author:  Mykaylo Titov on 06.07.14.
public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {

    Admin findByEmail(String email);
}
