package web.job.common.persistence.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import web.job.common.persistence.domain.Task;

import javax.persistence.LockModeType;
import java.util.stream.Stream;

// @author Titov Mykhaylo on 06.08.2015.
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {

    @Query("select task from Task task")
    Stream<Task> getAllTasks();

    @Lock(LockModeType.PESSIMISTIC_FORCE_INCREMENT)
    @Query("select task from Task task join fetch task.processingWorkItemsIds")
    Task lockTask(Task task);
}
