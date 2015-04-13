package persist.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// @author: Mykhaylo Titov on 12.04.15 18:25.
@Accessors(chain = true)
@Getter
@Setter
@ToString
@Entity
public class Account {

    @Id String name;
    @Column String email;
    @Column boolean activated;
    @Column boolean blocked;
    @Column String rememberMeToken;
}
