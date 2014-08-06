package novotvir.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author: Titov Mykhaylo (titov)
 * 18.02.14 16:20
 */
@Data
@Accessors(chain = true)
public class UserRegDetailsDto {
    public static final String USER_REG_DETAILS_DTO = "userRegDetailsDto";

    @Email public String email;
    @NotBlank public String token;
}
