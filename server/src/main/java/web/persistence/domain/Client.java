package web.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

// @author Titov Mykhaylo on 31.07.2015.
@Entity
@Table(name = "devices", uniqueConstraints = {
        @UniqueConstraint( name = "userId", columnNames = "userId"),
        @UniqueConstraint( name = "deviceUID", columnNames = "deviceUID")
})
@Accessors(chain = true)
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
    public Long id;

    @Column(nullable = false)
    public String deviceUID;

    @Enumerated(STRING)
    @Column(nullable = false)
    public ClientType clientType;

    @Column
    public String version;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    public User user;

    @OneToOne
    @JoinColumn(name = "pushTokenInfoId")
    public PushTokenInfo pushTokenInfo;

}
