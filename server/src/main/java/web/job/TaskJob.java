package web.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.job.service.TaskService;
import web.job.persistence.domain.tasks.Task;

import javax.annotation.Resource;
import javax.persistence.OptimisticLockException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;

// @author Titov Mykhaylo on 06.08.2015.
@Slf4j
@Component
public class TaskJob {

    @Resource TaskService taskService;

    static final Comparator<Task> taskByLastTaskRunDateTimeComparator = comparing(Task::getLastTaskRunDateTime, nullsLast(Comparator.<LocalDateTime>naturalOrder()));

    @Transactional
    public void execute(){
        Stream<Task> activeTasksStream = taskService.findActiveTasks();

        List<Task> activeTasks = activeTasksStream.sorted(taskByLastTaskRunDateTimeComparator).collect(Collectors.toList());

        ListIterator<Task> taskListIterator = activeTasks.listIterator(activeTasks.size());

        while (taskListIterator.hasPrevious()) {
            Task task = taskListIterator.previous();
            try {
                if (shouldBeRun(task)) {
                    Task actualTask = taskService.lockTask(task);
                }
            }catch (OptimisticLockException e){
                log.warn("Task {} will be skipped because it's already locked", task);
            } catch (Exception e) {
               log.error("Can't process task {}", task, e);
            }
        }
    }

    private boolean shouldBeRun(Task task) {
        return task.isRunEachTime() || scheduledTimeIsOnThePast(task);
    }

    private boolean scheduledTimeIsOnThePast(Task task) {
        try {
            CronExpression cronExpression = new CronExpression(task.getSchedule());
            Date date;
            if (task.getLastTaskRunDateTime() == null) {
                date = new Date();
            } else {
                date = Date.from(task.getLastTaskRunDateTime().atZone(ZoneId.systemDefault()).toInstant());
            }
            Date nextValidTimeAfter = cronExpression.getNextValidTimeAfter(date);
            return nextValidTimeAfter.toInstant().isAfter(Instant.now());
        } catch (ParseException e) {
            log.error("Can't calc task schedule time for schedule expression", task.getSchedule(), e);
            return false;
        }
    }

}
