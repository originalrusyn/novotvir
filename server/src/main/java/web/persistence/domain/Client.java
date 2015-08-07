package web.persistence.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 31.07.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Entity
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint( name = "userId", columnNames = "userId"),
        @UniqueConstraint( name = "deviceUID", columnNames = "deviceUID")
})
@Accessors(chain = true)
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString(exclude = {"pushTokenInfo", "user"})
@Setter
public class Client implements Serializable {

    private static final long serialVersionUID = 4828839385052160070L;

    public enum ClientType {
        ANDROID_APP, CHROME, UNKNOWN
    }

    @Id
    @SequenceGenerator(name = "device_id_seq_gen", sequenceName = "device_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "device_id_seq_gen")
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String deviceUID;

    @Enumerated(STRING)
    @Column(nullable = false)
    @NonNull
    private ClientType clientType;

    @Column
    private String platformVersion;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @NonNull
    private User user;

    @OneToOne
    @JoinColumn(name = "pushTokenInfoId")
    private PushTokenInfo pushTokenInfo;

    @Version
    private long version;

}
