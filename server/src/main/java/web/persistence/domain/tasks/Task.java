package web.persistence.domain.tasks;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 04.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@Accessors(chain = true)
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "taskType", discriminatorType = DiscriminatorType.STRING)
//@Table(name = "tasks")
public class Task implements Serializable {

    private static final long serialVersionUID = -1488019555520585871L;

    private static final int RETRIES_ON_ERROR_MIN_VALUE = 0;
    private static final int RETRIES_ON_ERROR_MAX_VALUE = 10;

    public enum TaskStatus{
        ACTIVE,
        PROCESSING
    }

    @Id
    @SequenceGenerator(name = "tasks_id_seq_gen", sequenceName = "tasks_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "tasks_id_seq_gen")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NonNull
    private TaskStatus taskStatus = TaskStatus.ACTIVE;

    private LocalDateTime lastTaskRunDateTime;

    private LocalDateTime lastTaskCompletionDateTime;

    @Min(RETRIES_ON_ERROR_MIN_VALUE) @Max(RETRIES_ON_ERROR_MAX_VALUE)
    private int retriesOnError;

    @Version
    private long version;

    public Task setRetriesOnError(int retriesOnError) {
        Preconditions.checkArgument(retriesOnError >= RETRIES_ON_ERROR_MIN_VALUE && retriesOnError <= RETRIES_ON_ERROR_MAX_VALUE);
        this.retriesOnError = retriesOnError;
        return this;
    }
}
