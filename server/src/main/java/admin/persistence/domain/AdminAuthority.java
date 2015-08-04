package admin.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;
import common.enums.Role;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author: Titov Mykhaylo (titov) on 05.07.14 22:17.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Entity
@Table(name = "adminAuthorities", uniqueConstraints = {
        @UniqueConstraint(name = "admin_role", columnNames = {"adminId", "role"})
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@ToString(exclude = "admin")
public class AdminAuthority implements Serializable {

    private static final long serialVersionUID = -547661660277815495L;

    @Id
    @SequenceGenerator(name = "adminAuthorities_id_seq_gen", sequenceName = "adminAuthorities_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "adminAuthorities_id_seq_gen")
    private Long id;

    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "adminId")
    @NonNull
    private Admin admin;

    @Enumerated(STRING)
    @Column(nullable = false)
    @NonNull
    private Role role;

}
