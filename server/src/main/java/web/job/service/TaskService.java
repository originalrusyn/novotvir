package web.job.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.job.persistence.domain.tasks.Task;
import web.job.persistence.repository.TaskRepository;

import javax.annotation.Resource;
import java.util.stream.Stream;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

// @author Titov Mykhaylo on 06.08.2015.
@Component
public class TaskService {

    @Resource TaskRepository taskRepository;

    @Transactional
    public Stream<Task> findActiveTasks() {
        return taskRepository.findActiveTasks();
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Task lockTask(Task task){
        return taskRepository.save(task.lock());
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Task unlockTask(Task task){
        return taskRepository.save(task.lock());
    }
}
