package web.persistence.domain;

import common.enums.Role;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Setter;
import lombok.ToString;
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
@ToString(exclude = {"authorities", "socialUserConnections"})
@Setter
@SuppressFBWarnings("EQ_DOESNT_OVERRIDE_EQUALS")
public class User implements Serializable {

    private static final long serialVersionUID = -8771610125938886166L;

    @Id
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    public String name;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "primaryEmailAddressId")
    public EmailAddress primaryEmailAddress;

    @OneToMany(mappedBy = "user", cascade = ALL)
    public List<EmailAddress> emailAddresses;

    @Column(nullable = true)
    public String token;

    @Column(nullable = false)
    public String lastSignInIpAddress;

    public LocalDateTime lastSignInTimestamp;

    public String activationToken;

    public boolean activated;

    public boolean blocked;

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    public List<Authority> authorities;

    @Version
    public long version;

    public User(){
        authorities = getUserDefaultAuthorities(this);
    }

    private List<Authority> getUserDefaultAuthorities(User user) {
        ArrayList<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority().setUser(user).setRole(Role.USER));
        return authorities;
    }
}
