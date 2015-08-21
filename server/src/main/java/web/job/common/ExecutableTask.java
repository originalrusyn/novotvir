package web.job.common;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import web.job.common.persistence.domain.Work;

// @author Titov Mykhaylo on 21.08.2015.
@RequiredArgsConstructor
public class ExecutableTask implements Runnable{

    private @NonNull final Work work;

    @Override
    public void run() {

    }
}
