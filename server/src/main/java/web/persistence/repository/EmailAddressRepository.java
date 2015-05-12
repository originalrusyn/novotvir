package web.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.persistence.domain.EmailAddress;

// @author: Mykhaylo Titov on 11.05.15 18:59.
public interface EmailAddressRepository extends JpaRepository<EmailAddress, Long> {
}
