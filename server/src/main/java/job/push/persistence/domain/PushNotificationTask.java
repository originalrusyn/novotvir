package job.push.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import job.common.persistence.domain.Task;
import lombok.*;
import lombok.experimental.Accessors;
import web.persistence.domain.PushNotification;

import javax.persistence.*;

import static job.push.persistence.domain.PushNotificationTask.UserFiltrationQuery.ALL_USERS;

// @author Titov Mykhaylo on 06.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@RequiredArgsConstructor
@Setter
@Getter
@ToString(callSuper = true, exclude = {"pushNotification"})
@Accessors(chain = true)
@Entity
@Table(name = "pushNotificationTasks")
public class PushNotificationTask extends Task {

    private static final long serialVersionUID = 7978621873339034709L;

    public enum UserFiltrationQuery{
        ALL_USERS
    }

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "pushNotificationId")
    private PushNotification pushNotification;

    @NonNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserFiltrationQuery userFiltrationQuery = ALL_USERS;
}
