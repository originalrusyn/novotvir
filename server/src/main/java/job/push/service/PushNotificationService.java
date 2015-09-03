package job.push.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import job.push.persistence.domain.PushNotificationTask;
import job.push.persistence.domain.PushNotificationTask.UserFiltrationQuery;
import web.persistence.repository.ClientRepository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

// @author Titov Mykhaylo on 10.08.2015.
@Service
public class PushNotificationService {

    @Resource ClientRepository clientRepository;

    public Set<Long> getNewPortionOfWorkItemsIds(@NonNull PushNotificationTask task){
        Set<Long> lockedProcessingIds = task.getProcessingWorkItemsIds();

        UserFiltrationQuery userFiltrationQuery = task.getUserFiltrationQuery();
        switch (userFiltrationQuery){
            case ALL_USERS:
                return clientRepository.findAllItems(lockedProcessingIds);
            default:
                return Collections.emptySet();
        }
    }
}
