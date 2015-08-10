package web.job.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.job.persistence.domain.tasks.Task;
import web.job.persistence.repository.TaskRepository;
import web.job.processor.TaskProcessor;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

// @author Titov Mykhaylo on 06.08.2015.
@Component
public class TaskService {

    @Resource TaskRepository taskRepository;

    @Resource List<TaskProcessor> processors;

    public Stream<Task> getAllTasks(){
        return taskRepository.getAllTasks();
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Task process(Task task){
        Task lockedTask = taskRepository.lockTask(task.lock(new HashSet<>()));

        for (TaskProcessor processor : processors) {
            if(processor.supports(task)){
                lockedTask = processor.process(task);
            }
        }

        return lockedTask;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public Task unlockTask(Task task){
        return taskRepository.save(task.unlock());
    }
}
