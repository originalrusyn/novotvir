package admin.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author: Titov Mykhaylo (titov) on 05.07.14 22:17.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Entity
@Table(name = "admins", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = "email"),
})
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Accessors(chain = true)
@ToString(exclude = "authorities")
@Setter
@Getter
public class Admin implements Serializable {

    private static final long serialVersionUID = 5332524357319929085L;

    @Id
    @SequenceGenerator(name = "admins_id_seq", sequenceName = "admins_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "admins_id_seq")
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String token;

    private String lastSignInIpAddress;

    private LocalDateTime lastSignInDateTime;

    private boolean blocked;

    @OneToMany(mappedBy = "admin", fetch = EAGER, cascade = ALL)
    @NonNull
    private List<AdminAuthority> authorities = new ArrayList<>();

}
