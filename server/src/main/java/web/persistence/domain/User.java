package web.persistence.domain;

import common.enums.Role;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
        @UniqueConstraint( name = "activationToken", columnNames = "activationToken")
})
@Accessors(chain = true)
@ToString(exclude = {"authorities"})
@Setter
public class User {

    @Id
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "users_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    public String name;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "primaryEmailAddressId")
    public EmailAddress primaryEmailAddress;

    @OneToMany(cascade = ALL)
    public List<EmailAddress> emailAddresses;

    @Column(nullable = true)
    public String token;

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
