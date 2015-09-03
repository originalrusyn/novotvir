package job.push;

import job.common.persistence.domain.Task;
import job.common.persistence.domain.Work;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import job.common.WorkProcessor;
import job.push.persistence.domain.PushNotificationTask;
import web.persistence.domain.PushNotification;

// @author: m on 27.08.15 18:19.
@Component
public class PushWorkProcessor implements WorkProcessor{

    @Override
    public void process(@NonNull Work work) {
        PushNotificationTask task = (PushNotificationTask) work.getTask();
        PushNotification pushNotification = task.getPushNotification();
        System.out.println(pushNotification);
    }

    @Override
    public boolean supports(@NonNull Task task) {
        return task instanceof PushNotificationTask;
    }
}
