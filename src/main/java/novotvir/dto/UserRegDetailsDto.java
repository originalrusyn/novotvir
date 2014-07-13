package novotvir.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author: Titov Mykhaylo (titov)
 * 18.02.14 16:20
 */
@ToString
@Accessors(chain = true)
public class UserRegDetailsDto {
    public static final String USER_REG_DETAILS_DTO = "userRegDetailsDto";

    @Email @Setter @Getter public String email;
    @NotBlank @Setter @Getter public String token;
}
