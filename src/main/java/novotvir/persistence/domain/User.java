package novotvir.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;
import static novotvir.enums.Role.USER;
import static scala.actors.threadpool.Arrays.asList;

/**
 * author: Titov Mykhaylo (titov)
 * 20.06.13 17:22
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint( name = "name", columnNames = "name"),
        @UniqueConstraint( name = "name_facebookId", columnNames = {"name" ,"facebookId"})
})
@Accessors(chain = true)
@ToString(exclude = "authorities")
public class User {

    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq")
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq")
    public Long id;

    @Column(nullable = false)
    @Setter
    public String name;

    @Column(nullable = false)
    @Setter
    public String email;

    @Column(nullable = true)
    @Setter
    public String token;

    @Column(nullable = true)
    @Setter
    public String facebookId;

    @Column(nullable = false)
    @Setter
    public String lastSignInIpAddress;

    @Setter
    public Date lastWebSignInTimestamp;

    @Setter
    public boolean activated;

    @Setter
    public boolean blocked;

    @OneToMany(mappedBy = "user", fetch = EAGER, cascade = ALL)
    @Setter
    public List<Authority> authorities;

    public User(){
        this.setAuthorities(getUserDefaultAuthorities(this));
    }

    private List getUserDefaultAuthorities(User user) {
        return asList(new Authority[]{new Authority().setUser(user).setRole(USER)});
    }
}
