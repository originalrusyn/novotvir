package web.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

// @author: Mykhaylo Titov on 11.05.15 12:49.
@Entity
@Table(name = "emailAddresses")
@Accessors(chain = true)
@ToString(exclude = "user")
@Setter
public class EmailAddress {

    @Id
    @SequenceGenerator(name = "emailAddresses_id_seq_gen", sequenceName = "emailAddresses_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "emailAddresses_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    public String email;

    @ManyToOne
    @JoinColumn(name = "userId")
    public User user;
}
