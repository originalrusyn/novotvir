package web.job.persistence.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;
import web.job.persistence.domain.tasks.Task;
import web.job.persistence.domain.tasks.Task.TaskStatus;

import javax.persistence.LockModeType;
import java.util.stream.Stream;

// @author Titov Mykhaylo on 06.08.2015.
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    Stream<Task> findByTaskStatus(TaskStatus taskStatus);

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    default Stream<Task> findActiveTasks(){
        return findByTaskStatus(TaskStatus.ACTIVE);
    }
}
