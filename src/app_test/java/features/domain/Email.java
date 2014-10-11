package features.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import static java.util.UUID.randomUUID;

// @author: Mykhaylo Titov on 13.09.14 14:09.
@Data
@Accessors(chain = true)
@ToString(exclude = "person")
public class Email {
    final String value;
    Person person;

    public static Email uniqueEmail(){
        return new Email(randomUUID().toString().replaceAll("-", "") + "@i.ua");
    }
}
