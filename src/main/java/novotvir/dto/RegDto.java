package novotvir.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.social.facebook.api.FacebookProfile;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.RandomStringUtils.random;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
@ToString
@Accessors(chain = true)
@NoArgsConstructor(access = PRIVATE)
public class RegDto {
    @Setter public String name;
    @Setter public String email;
    @Setter public String token;
    @Setter public String facebookId;

    public static RegDto getInstance(FacebookProfile facebookProfile){
        return new RegDto().setEmail(facebookProfile.getEmail()).setName(facebookProfile.getEmail()).setFacebookId(facebookProfile.getId()).setToken(random(6));
    }

    public static RegDto getInstance(UserRegDetailsDto userRegDetailsDto){
        return new RegDto().setEmail(userRegDetailsDto.email).setName(userRegDetailsDto.email).setToken(userRegDetailsDto.token);
    }
}
