package novotvir.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.social.facebook.api.FacebookProfile;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.RandomStringUtils.random;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
@ToString
@Accessors(chain = true)
@NoArgsConstructor(access = PRIVATE)
@Setter
public class RegDto {
    public String name;
    public String email;
    public String token;
    public String facebookId;

    public static RegDto getInstance(FacebookProfile facebookProfile){
        return new RegDto().setEmail(facebookProfile.getEmail()).setName(facebookProfile.getId()).setFacebookId(facebookProfile.getId()).setToken(random(6));
    }

    public static RegDto getInstance(UserRegDetailsDto userRegDetailsDto){
        return new RegDto().setEmail(userRegDetailsDto.email).setName(userRegDetailsDto.email).setToken(userRegDetailsDto.token);
    }
}
