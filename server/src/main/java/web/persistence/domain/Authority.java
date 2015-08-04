package web.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;
import common.enums.Role;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo (titov) on 19.03.2014.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Entity
@Table(name = "authorities", uniqueConstraints = {
        @UniqueConstraint( name = "user_role", columnNames = {"userId", "role"})
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Accessors(chain = true)
@ToString(exclude = "user")
@Setter
@Getter
public class Authority implements Serializable {

    private static final long serialVersionUID = -3142986930024203254L;

    @Id
    @SequenceGenerator(name = "authorities_id_seq", sequenceName = "authorities_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "authorities_id_seq")
    private Long id;

    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "userId")
    @NonNull
    private User user;

    @Enumerated(STRING)
    @Column(nullable = false)
    @NonNull
    private Role role;

}
