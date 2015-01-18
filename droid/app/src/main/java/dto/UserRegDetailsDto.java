package dto;

import lombok.Data;
import lombok.experimental.Accessors;

// @author: Mykhaylo Titov on 01.01.15 19:36.
@Data
@Accessors(chain = true)
public class UserRegDetailsDto {
    String email;
    String token;
}
