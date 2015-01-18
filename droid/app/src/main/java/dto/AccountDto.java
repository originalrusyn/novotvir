package dto;

import lombok.Data;
import lombok.experimental.Accessors;

// @author: Mykhaylo Titov on 01.01.15 19:52.
@Data
@Accessors(chain = true)
public class AccountDto {
    String email;
    String name;
    boolean activated;
    boolean blocked;
}