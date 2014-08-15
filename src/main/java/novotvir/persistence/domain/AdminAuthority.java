package novotvir.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import novotvir.enums.Role;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

// @author: Mykaylo Titov on 05.07.14 22:17.
@Entity
@Table(name = "adminAuthorities", uniqueConstraints = {
        @UniqueConstraint(name = "admin_role", columnNames = {"adminId", "role"})
})
@Accessors(chain = true)
@ToString(exclude = "admin")
public class AdminAuthority {

    @Id
    @SequenceGenerator(name = "adminAuthorities_id_seq_gen", sequenceName = "adminAuthorities_id_seq")
    @GeneratedValue(strategy = SEQUENCE, generator = "adminAuthorities_id_seq_gen")
    public Long id;

    @Setter
    @ManyToOne(fetch = EAGER, optional = false)
    @JoinColumn(name = "adminId")
    public Admin admin;

    @Setter
    @Enumerated(STRING)
    @Column(nullable = false)
    public Role role;

}
