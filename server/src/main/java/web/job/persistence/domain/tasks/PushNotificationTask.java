package web.job.persistence.domain.tasks;

import lombok.*;
import lombok.experimental.Accessors;
import web.persistence.domain.PushNotification;

import javax.persistence.*;

import static web.job.persistence.domain.tasks.PushNotificationTask.UserFiltrationQuery.ALL_USERS;

// @author Titov Mykhaylo on 06.08.2015.
@RequiredArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
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
    private UserFiltrationQuery userFiltrationQuery = ALL_USERS;
}
