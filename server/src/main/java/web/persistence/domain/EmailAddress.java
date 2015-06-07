package web.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

// @author: Mykhaylo Titov on 11.05.15 12:49.
@Entity
@Table(name = "emailAddresses")
@Accessors(chain = true)
@ToString(exclude = "user")
@Setter
public class EmailAddress implements Serializable {

    private static final long serialVersionUID = 3855752452899393695L;

    @Id
    @SequenceGenerator(name = "emailAddresses_id_seq_gen", sequenceName = "emailAddresses_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "emailAddresses_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    public String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    public User user;
}
