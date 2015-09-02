package web.job.common;

import web.job.common.persistence.domain.Work;

// @author: m on 27.08.15 18:20.
public interface WorkProcessor {

    void process(Work work);

    boolean supports(Work work);
}
