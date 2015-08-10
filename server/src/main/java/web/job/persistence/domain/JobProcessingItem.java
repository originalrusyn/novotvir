package web.job.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;
import web.job.persistence.domain.tasks.Task;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 06.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@ToString(exclude = {"task"})
@Accessors(chain = true)
@Table(name = "jobProcessingItems")
@Entity
public class JobProcessingItem implements Serializable{

    private static final long serialVersionUID = 8984778954151783321L;

    public enum JobItemStatus {
        ERROR,
        PROCESSING
    }

    @Id
    @SequenceGenerator(name = "jobProcessingItems_id_seq_gen", sequenceName = "jobProcessingItems_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "jobProcessingItems_id_seq_gen")
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "taskId")
    private Task task;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobItemStatus jobItemStatus;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime forScheduleDateTime;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime creationDateTime = LocalDateTime.now();

    private int retriesOnError;

    @Setter(AccessLevel.PRIVATE)
    @Version
    private long version;

    public JobProcessingItem incrementRetriesOnError(){
        retriesOnError++;
        return this;
    }
}
