package web.persistence.domain;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 06.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@ToString
@Accessors(chain = true)
@Entity
@Table(name = "pushNotifications")
public class PushNotification implements Serializable{

    private static final long serialVersionUID = -460740407822666633L;

    @Id
    @SequenceGenerator(name = "pushNotifications_id_seq_gen", sequenceName = "pushNotifications_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "pushNotifications_id_seq_gen")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String pushMessage;

    @NotBlank
    @Column(nullable = false)
    private String queryNameForFetchReceivers;

    @Setter(AccessLevel.PRIVATE)
    @Version
    private long version;

    public PushNotification(@NonNull String pushMessage, @NonNull String queryNameForFetchReceivers){
        Preconditions.checkArgument(StringUtils.hasText(pushMessage));
        Preconditions.checkArgument(StringUtils.hasText(queryNameForFetchReceivers));
        this.pushMessage = pushMessage;
        this.queryNameForFetchReceivers = queryNameForFetchReceivers;
    }

    public PushNotification setPushMessage(@NonNull String pushMessage){
        Preconditions.checkArgument(StringUtils.hasText(pushMessage));
        this.pushMessage = pushMessage;
        return this;
    }

    public PushNotification setQueryNameForFetchReceivers(@NonNull String queryNameForFetchReceivers){
        Preconditions.checkArgument(StringUtils.hasText(queryNameForFetchReceivers));
        this.queryNameForFetchReceivers = queryNameForFetchReceivers;
        return this;
    }
}
