package web.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

// @author: Mykhaylo Titov on 11.05.15 12:49.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Entity
@Table(name = "emailAddresses")
@Accessors(chain = true)
@ToString(exclude = "user")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Setter
@Getter
public class EmailAddress implements Serializable {

    private static final long serialVersionUID = 3855752452899393695L;

    @Id
    @SequenceGenerator(name = "emailAddresses_id_seq_gen", sequenceName = "emailAddresses_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "emailAddresses_id_seq_gen")
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    @NonNull
    private User user;
}
