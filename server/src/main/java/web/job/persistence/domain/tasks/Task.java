package web.job.persistence.domain.tasks;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;
import org.quartz.CronExpression;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;
import static web.job.persistence.domain.tasks.Task.TaskStatus.ACTIVE;
import static web.job.persistence.domain.tasks.Task.TaskStatus.PROCESSING;

// @author Titov Mykhaylo on 04.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task implements Serializable {

    private static final long serialVersionUID = -1488019555520585871L;

    private static final int MAX_RETRIES_ON_ERROR_MIN_VALUE = 0;
    private static final int MAX_RETRIES_ON_ERROR_MAX_VALUE = 10;

    private static final long MAX_REPEAT_COUNT_MIN_VALUE = 0L;

    private static final long REPEAT_COUNT_MIN_VALUE = 0L;

    private static final String RUN_EACH_TIME = "RUN_EACH_TIME";

    public enum TaskStatus{
        ACTIVE,
        PROCESSING
    }

    @Id
    @SequenceGenerator(name = "tasks_id_seq_gen", sequenceName = "tasks_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "tasks_id_seq_gen")
    private Long id;

    @Setter(AccessLevel.PRIVATE)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private TaskStatus taskStatus = ACTIVE;

    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime lastTaskRunDateTime;

    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime lastTaskCompletionDateTime;

    @Min(MAX_RETRIES_ON_ERROR_MIN_VALUE) @Max(MAX_RETRIES_ON_ERROR_MAX_VALUE)
    private int maxRetriesOnError;

    @Column(nullable = false)
    @NonNull
    private String schedule = RUN_EACH_TIME;

    @Min(MAX_REPEAT_COUNT_MIN_VALUE)
    private long maxRepeatCount;

    @Min(REPEAT_COUNT_MIN_VALUE)
    private long repeatCount;

    @Version
    private long version;

    public Task setMaxRetriesOnError(int maxRetriesOnError) {
        Preconditions.checkArgument(maxRetriesOnError >= MAX_RETRIES_ON_ERROR_MIN_VALUE && maxRetriesOnError <= MAX_RETRIES_ON_ERROR_MAX_VALUE);
        this.maxRetriesOnError = maxRetriesOnError;
        return this;
    }

    public Task setMaxRepeatCount(long maxRepeatCount) {
        Preconditions.checkArgument(maxRepeatCount >= MAX_REPEAT_COUNT_MIN_VALUE);
        this.maxRepeatCount = maxRepeatCount;
        return this;
    }

    public Task setRepeatCount(long repeatCount) {
        Preconditions.checkArgument(repeatCount >= REPEAT_COUNT_MIN_VALUE);
        this.repeatCount = repeatCount;
        return this;
    }

    public Task setSchedule(@NonNull String schedule){
        Preconditions.checkArgument(CronExpression.isValidExpression(schedule));
        this.schedule = schedule;
        return this;
    }

    public Task lock(){
        setTaskStatus(PROCESSING);
        setLastTaskRunDateTime(LocalDateTime.now());
        return this;
    }

    public Task unlock(){
        setTaskStatus(ACTIVE);
        return this;
    }

    public Task markAsCompleted(){
        if(lastTaskCompletionDateTime != null){
            incrementRepeatCount();
        }
        setLastTaskCompletionDateTime(LocalDateTime.now());
        return this;
    }

    public Task setRunEachTime(){
        this.schedule = RUN_EACH_TIME;
        return this;
    }

    public boolean isRunEachTime(){
        return RUN_EACH_TIME.equals(schedule);
    }

    private long incrementRepeatCount(){
        return ++repeatCount;
    }
}
