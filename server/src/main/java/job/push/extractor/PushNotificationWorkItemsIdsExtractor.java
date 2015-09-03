package job.push.extractor;

import job.common.extractor.WorkItemsIdsExtractor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import job.push.persistence.domain.PushNotificationTask;
import job.common.persistence.domain.Task;
import job.push.service.PushNotificationService;

import javax.annotation.Resource;
import java.util.Set;

// @author Titov Mykhaylo on 10.08.2015.
@Component
public class PushNotificationWorkItemsIdsExtractor implements WorkItemsIdsExtractor {

    @Resource PushNotificationService pushNotificationService;

    @Override
    public Set<Long> extract(@NonNull Task task) {
        return pushNotificationService.getNewPortionOfWorkItemsIds((PushNotificationTask) task);
    }

    @Override
    public boolean supports(Task task) {
        return task instanceof PushNotificationTask;
    }
}
