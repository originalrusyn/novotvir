package novo.tvir.access.signup.dto;

import lombok.Data;
import lombok.experimental.Accessors;

// @author: m on 29.03.15 19:36.
@Data
@Accessors(chain = true)
public class SignUpDto {
    String email;
    String token;
}
