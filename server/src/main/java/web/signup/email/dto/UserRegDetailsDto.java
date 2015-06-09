package web.signup.email.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

// @author: Titov Mykhaylo (titov) on 18.02.14 16:20
@SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN"})
@Data
@Accessors(chain = true)
public class UserRegDetailsDto {
    public static final String USER_REG_DETAILS_DTO = "userRegDetailsDto";

    @Email public String email;
    @NotBlank public String token;
}
