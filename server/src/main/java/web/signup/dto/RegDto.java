package web.signup.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.google.api.plus.Person;
import web.signup.email.dto.UserRegDetailsDto;

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
    public String googleId;

    public static RegDto getInstance(FacebookProfile facebookProfile){
        return new RegDto().setEmail(facebookProfile.getEmail()).setName(facebookProfile.getId()).setFacebookId(facebookProfile.getId()).setToken(random(6));
    }

    public static RegDto getInstance(UserRegDetailsDto userRegDetailsDto){
        return new RegDto().setEmail(userRegDetailsDto.email).setName(userRegDetailsDto.email).setToken(userRegDetailsDto.token);
    }

    public static RegDto getInstance(Person person) {
        return new RegDto().setEmail(person.getAccountEmail()).setName(person.getId()).setGoogleId(person.getId()).setToken(random(6));
    }
}
