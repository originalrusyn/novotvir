package web.job.persistence.domain;

import lombok.*;
import lombok.experimental.Accessors;
import web.job.persistence.domain.tasks.Task;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 06.08.2015.
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Table(name = "jobProcessingItems")
@Entity
public class JobProcessingItem implements Serializable{

    private static final long serialVersionUID = 8984778954151783321L;

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
    private LocalDateTime forScheduleDateTime;

    @NonNull
    @Column(nullable = false)
    private LocalDateTime creationDateTime = LocalDateTime.now();

    private int retriesOnError;

    public JobProcessingItem incrementRetriesOnError(){
        retriesOnError++;
        return this;
    }
}
