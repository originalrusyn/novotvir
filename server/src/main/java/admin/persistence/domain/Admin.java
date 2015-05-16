package admin.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author: Titov Mykhaylo (titov) on 05.07.14 22:17.
@Entity
@Table(name = "admins", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = "email"),
})
@Accessors(chain = true)
@ToString(exclude = "authorities")
@Setter
public class Admin {

    @Id
    @SequenceGenerator(name = "admins_id_seq", sequenceName = "admins_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "admins_id_seq")
    public Long id;

    @Column(nullable = false)
    public String email;

    @Column(nullable = true)
    public String token;

    @Column(nullable = false)
    public String lastSignInIpAddress;

    public Date lastSignInTimestamp;

    public boolean blocked;

    @OneToMany(mappedBy = "admin", fetch = EAGER, cascade = ALL)
    public List<AdminAuthority> authorities;

}
