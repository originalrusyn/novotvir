package admin.persistence.repository;

import admin.persistence.domain.Admin;
import org.springframework.data.repository.PagingAndSortingRepository;

// @author: Titov Mykhaylo (titov) on 06.07.14.
public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {

    Admin findByEmail(String email);
}
