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

/**
 * Created by Mykaylo Titov on 05.07.14.
 */
@Entity
@Table(name = "admins", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = "email"),
})
@Accessors(chain = true)
@ToString(exclude = "authorities")
public class Admin {

    @Id
    @SequenceGenerator(name = "admins_id_seq", sequenceName = "admins_id_seq")
    @GeneratedValue(strategy = SEQUENCE, generator = "admins_id_seq")
    public Long id;

    @Column(nullable = false)
    @Setter
    public String email;

    @Column(nullable = true)
    @Setter
    public String token;

    @Column(nullable = false)
    @Setter
    public String lastSignInIpAddress;

    @Setter
    public Date lastWebSignInTimestamp;

    @Setter
    public boolean blocked;

    @OneToMany(mappedBy = "admin", fetch = EAGER, cascade = ALL)
    @Setter
    public List<AdminAuthority> authorities;

}
