package web.job.processor;

import web.job.persistence.domain.tasks.Task;

// @author Titov Mykhaylo on 10.08.2015.
public interface TaskProcessor<T extends Task> {

    T process(Task task);

    boolean supports(Task task);

}
