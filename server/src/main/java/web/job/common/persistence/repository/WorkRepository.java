package web.job.common.persistence.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import web.job.common.persistence.domain.Work;

// @author Titov Mykhaylo on 21.08.2015.
public interface WorkRepository extends PagingAndSortingRepository<Work, Long> {

}
