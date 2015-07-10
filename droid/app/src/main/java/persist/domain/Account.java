package persist.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

// @author: Mykhaylo Titov on 12.04.15 18:25.
@Accessors(chain = true)
@Getter
@Setter
@ToString
@Entity
public class Account implements Serializable {

    @Id String name;
    @Column String displayName;
    @Column String imageUrl;
    @Column String email;
    @Column boolean activated;
    @Column boolean blocked;
    @Column(nullable = false) String rememberMeToken;
}
