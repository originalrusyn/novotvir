package web.job.push;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import web.job.common.WorkProcessor;
import web.job.common.persistence.domain.Work;
import web.job.push.persistence.domain.PushNotificationTask;
import web.persistence.domain.PushNotification;

// @author: m on 27.08.15 18:19.
@Component
public class PushWorkProcessor implements WorkProcessor{

    @Override
    public void process(@NonNull Work work) {
        PushNotificationTask task = (PushNotificationTask) work.getTask();
        PushNotification pushNotification = task.getPushNotification();
    }

    @Override
    public boolean supports(@NonNull Work work) {
        return work.getTask() instanceof PushNotificationTask;
    }
}
