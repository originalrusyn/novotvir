package web.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 31.07.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
//@Entity
//@Table(name = "pushTokenInfos")
@Accessors(chain = true)
@ToString
@Setter
public class PushTokenInfo implements Serializable {

    private static final long serialVersionUID = -8734460026777454369L;

    @Id
    @SequenceGenerator(name = "pushTokenInfos_id_seq_gen", sequenceName = "pushTokenInfos_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "pushTokenInfos_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    @NonNull
    public String token;

    @Version
    public long version;

}
