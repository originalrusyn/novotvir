package web.persistence.domain;

import common.enums.Role;
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

// @author: Titov Mykhaylo (titov) 20.06.13 17:22
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint( name = "name", columnNames = "name"),
        @UniqueConstraint( name = "activationToken", columnNames = "activationToken")
})
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@ToString(exclude = {"authorities", "clients"})
@Setter
@Getter
@SuppressFBWarnings({"EQ_DOESNT_OVERRIDE_EQUALS", "UCPM_USE_CHARACTER_PARAMETERIZED_METHOD"})
public class User implements Serializable {

    private static final long serialVersionUID = -8771610125938886166L;

    @Id
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq_gen")
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "primaryEmailAddressId")
    private EmailAddress primaryEmailAddress;

    @OneToMany(mappedBy = "user", cascade = ALL)
    @NonNull
    private List<EmailAddress> emailAddresses = new ArrayList<>();

    @Column(nullable = false)
    @NonNull
    private String token;

    private String lastSignInIpAddress;

    private LocalDateTime lastSignInDateTime;

    private String activationToken;

    private boolean activated;

    private boolean blocked;

    //@OneToMany
    //@NonNull
    //private List<Client> clients = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    @NonNull
    private List<Authority> authorities = getUserDefaultAuthorities(this);

    @Version
    private long version;

    private List<Authority> getUserDefaultAuthorities(User user) {
        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(user, Role.USER));
        return authorities;
    }
}
