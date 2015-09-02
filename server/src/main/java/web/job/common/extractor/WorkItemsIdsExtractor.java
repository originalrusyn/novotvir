package web.job.common.extractor;

import web.job.common.persistence.domain.Task;

import java.util.Set;

// @author Titov Mykhaylo on 10.08.2015.
public interface WorkItemsIdsExtractor {

    Set<Long> extract(Task task);

    boolean supports(Task task);

}
