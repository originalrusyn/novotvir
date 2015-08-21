package web.job.common;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import web.job.common.persistence.domain.Work;
import web.job.common.persistence.repository.TaskRepository;
import web.job.common.service.WorkItemsExtractorsService;
import web.job.common.persistence.domain.Task;

import javax.annotation.Resource;
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

    @Resource
    WorkItemsExtractorsService processingItemsExtractorsService;

    @Resource TaskExecutor executor;

    @Resource TaskRepository taskRepository;

    static final Comparator<Task> taskByLastTaskRunDateTimeComparator = comparing(Task::getLastTaskRunDateTime, nullsLast(Comparator.<LocalDateTime>naturalOrder()));

    public void execute(){
        Stream<Task> allTasksStream = taskRepository.getAllTasks();

        List<Task> sortedByPriorityDescTasks = allTasksStream.sorted(taskByLastTaskRunDateTimeComparator).collect(Collectors.toList());

        ListIterator<Task> taskListIterator = sortedByPriorityDescTasks.listIterator(sortedByPriorityDescTasks.size());

        while (taskListIterator.hasPrevious()) {
            Task task = taskListIterator.previous();
            try {
                LocalDateTime scheduleDateTime = getScheduleDateTime(task);
                if (scheduleDateTime.isAfter(LocalDateTime.now())) {
                    List<Work> works = processingItemsExtractorsService.getNewPortionOfWorkToProcessing(task, scheduleDateTime);

                    for (Work work : works) {
                        executor.execute(new ExecutableTask(work));
                    }
                }
            } catch (Exception e) {
                log.error("Can't process task {}", task, e);
            }
        }
    }

    private LocalDateTime getScheduleDateTime(Task task) throws ParseException {
        if (task.isRunEachTime()) {
            return LocalDateTime.now();
        } else {
            return calcNextScheduleDateTime(task);
        }
    }

    private LocalDateTime calcNextScheduleDateTime(Task task) throws ParseException {
        Date date;
        if (task.getLastTaskRunDateTime() == null) {
            date = new Date();
        } else {
            date = Date.from(task.getLastTaskRunDateTime().atZone(ZoneId.systemDefault()).toInstant());
        }
        CronExpression cronExpression = new CronExpression(task.getSchedule());
        Date nextValidTimeAfter = cronExpression.getNextValidTimeAfter(date);
        return LocalDateTime.ofInstant(nextValidTimeAfter.toInstant(), ZoneId.systemDefault());
    }

}
