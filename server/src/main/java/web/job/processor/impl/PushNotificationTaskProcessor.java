package web.job.processor.impl;

import org.springframework.stereotype.Component;
import web.job.persistence.domain.tasks.PushNotificationTask;
import web.job.persistence.domain.tasks.Task;
import web.job.processor.TaskProcessor;
import web.job.service.PushNotificationService;

import javax.annotation.Resource;

// @author Titov Mykhaylo on 10.08.2015.
@Component
public class PushNotificationTaskProcessor implements TaskProcessor<PushNotificationTask> {

    @Resource PushNotificationService pushNotificationService;

    @Override
    public PushNotificationTask process(Task task) {
        return pushNotificationService.getAndLockProcessingItems((PushNotificationTask) task);
    }

    @Override
    public boolean supports(Task task) {
        return task instanceof PushNotificationTask;
    }
}
