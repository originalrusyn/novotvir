package job.common.persistence.repository;

import job.common.persistence.domain.Work;
import org.springframework.data.repository.PagingAndSortingRepository;

// @author Titov Mykhaylo on 21.08.2015.
public interface WorkRepository extends PagingAndSortingRepository<Work, Long> {

}
