package job.common;

import job.common.persistence.domain.Task;
import job.common.persistence.domain.Work;

// @author: m on 27.08.15 18:20.
public interface WorkProcessor {

    void process(Work work);

    boolean supports(Task task);
}
