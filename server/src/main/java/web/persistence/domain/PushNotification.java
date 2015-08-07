package web.persistence.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 06.08.2015.
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
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
}
