package job.common.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

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
@Table(name = "works")
@Entity
public class Work implements Serializable{

    private static final long serialVersionUID = 8984778954151783321L;

    public enum WorkStatus {
        ERROR,
        PROCESSING
    }

    @Id
    @SequenceGenerator(name = "works_id_seq_gen", sequenceName = "works_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "works_id_seq_gen")
    private Long id;

    @NonNull
    private Long workItemId;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "taskId")
    private Task task;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus = WorkStatus.PROCESSING;

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

    public Work incrementRetriesOnError(){
        retriesOnError++;
        return this;
    }
}
