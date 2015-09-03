package job.common.service;

import job.common.extractor.WorkItemsIdsExtractor;
import job.common.persistence.domain.Task;
import job.common.persistence.domain.Work;
import job.common.persistence.repository.TaskRepository;
import job.common.persistence.repository.WorkRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// @author Titov Mykhaylo on 06.08.2015.
@Component
public class WorkItemsExtractorsService {

    @Resource
    TaskRepository taskRepository;

    @Resource List<WorkItemsIdsExtractor> extractors;

    @Resource
    WorkRepository workRepository;

    @Transactional
    public List<Work> getNewPortionOfWorkToProcessing(@NonNull Task task, @NonNull LocalDateTime scheduleDateTime){
        Task lockedTask = taskRepository.lockTask(task);

        List<Work> works = Collections.emptyList();

        if (task.getVersion() == lockedTask.getVersion()) {
            for (WorkItemsIdsExtractor extractor : extractors) {
                if (extractor.supports(task)) {
                    Set<Long> newPortionOfWorkItemsIdsToProcessing = extractor.extract(task);

                    works = newPortionOfWorkItemsIdsToProcessing.stream().map(id -> new Work(id, task, scheduleDateTime)).collect(Collectors.toList());
                    workRepository.save(works);

                    task.lock(newPortionOfWorkItemsIdsToProcessing);
                }
            }
        }

        return works;
    }
}
