package novotvir.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import novotvir.enums.Role;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Titov Mykhaylo (titov) on 19.03.2014.
 */
@Entity
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint( name = "user_role", columnNames = {"userId", "role"})
})
@Accessors(chain = true)
@ToString(exclude = "user")
public class Authority {

    @Id
    @SequenceGenerator(name = "authorities_id_seq", sequenceName = "authorities_id_seq")
    @GeneratedValue(strategy = SEQUENCE, generator = "authorities_id_seq")
    public Long id;

    @Setter
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "userId")
    public User user;

    @Setter
    @Enumerated(STRING)
    @Column(nullable = false)
    public Role role;
}
