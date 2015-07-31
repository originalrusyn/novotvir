package web.persistence.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;
import static web.persistence.domain.PushTokenInfo.PushTokenInfoStatus.ACTIVE;

// @author Titov Mykhaylo on 31.07.2015.
@Entity
@Table(name = "pushTokenInfos", uniqueConstraints = {
        @UniqueConstraint( name = "name", columnNames = "name"),
        @UniqueConstraint( name = "activationToken", columnNames = "activationToken")
})
@Accessors(chain = true)
@ToString
@Setter
public class PushTokenInfo implements Serializable {

    private static final long serialVersionUID = -8734460026777454369L;

    public enum PushTokenInfoStatus{
        ACTIVE, PROCESSING, ERROR
    }

    @Id
    @SequenceGenerator(name = "pushTokenInfos_id_seq_gen", sequenceName = "pushTokenInfos_id_seq", initialValue = 2, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "pushTokenInfos_id_seq_gen")
    public Long id;

    @Column(nullable = false)
    public String token;

    @Enumerated(STRING)
    @Column(nullable = false)
    public PushTokenInfoStatus pushTokenInfoStatus = ACTIVE;

    @Column
    public int retriesCounter;

    @Column
    public LocalDateTime lastSuccessfulPushSendingTimestamp;

    @Version
    public long version;

    public int incrementRetriesCounter(){
        return ++retriesCounter;
    }

    public void resetRetriesCounter(){
        retriesCounter = 0;
    }

}
