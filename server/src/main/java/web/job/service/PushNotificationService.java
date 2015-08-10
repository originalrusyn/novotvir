package web.job.service;

import org.springframework.stereotype.Service;
import web.job.persistence.domain.tasks.PushNotificationTask;
import web.job.persistence.domain.tasks.PushNotificationTask.UserFiltrationQuery;
import web.persistence.repository.ClientRepository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

// @author Titov Mykhaylo on 10.08.2015.
@Service
public class PushNotificationService {

    @Resource ClientRepository clientRepository;

    public PushNotificationTask getAndLockProcessingItems(PushNotificationTask task){
        Set<Long> items = getItems(task);
        task.lock(items);

        return task;
    }

    private Set<Long> getItems(PushNotificationTask task) {
        Set<Long> lockedProcessingIds = task.getItemsInProcessing();

        UserFiltrationQuery userFiltrationQuery = task.getUserFiltrationQuery();
        switch (userFiltrationQuery){
            case ALL_USERS:
                return clientRepository.findAllItems(lockedProcessingIds);
            default:
                return Collections.emptySet();
        }
    }
}
