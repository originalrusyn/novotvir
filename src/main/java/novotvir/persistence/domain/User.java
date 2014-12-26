package novotvir.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import novotvir.enums.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author: Titov Mykhaylo (titov) 20.06.13 17:22
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint( name = "name", columnNames = "name"),
        @UniqueConstraint( name = "name_facebookId", columnNames = {"name" ,"facebookId"}),
        @UniqueConstraint( name = "activationToken", columnNames = "activationToken")
})
@Accessors(chain = true)
@ToString(exclude = "authorities")
@Setter
public class User {

    @Id
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String email;

    @Column(nullable = true)
    public String token;

    @Column(nullable = true)
    public String facebookId;

    @Column(nullable = false)
    public String lastSignInIpAddress;

    public Date lastWebSignInTimestamp;

    @Column(nullable = false)
    public String activationToken;

    public boolean activated;

    public boolean blocked;

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    public List<Authority> authorities;

    public User(){
        this.setAuthorities(getUserDefaultAuthorities(this));
    }

    private List<Authority> getUserDefaultAuthorities(User user) {
        return asList(new Authority().setUser(user).setRole(Role.USER));
    }
}
