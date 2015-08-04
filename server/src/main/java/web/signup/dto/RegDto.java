package web.signup.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.social.facebook.api.User;
import org.springframework.social.google.api.plus.Person;
import web.signup.email.dto.UserRegDetailsDto;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang.RandomStringUtils.random;

/**
 * @author Titov Mykhaylo (titov) on 07.05.2014.
 */
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@ToString
@Accessors(chain = true)
@NoArgsConstructor(access = PRIVATE)
@Setter
@Getter
public class RegDto {
    public static final String FACEBOOK_USER_NAME_PREFIX = "f";
    public static final String GOOGLE_PLUS_USER_NAME_PREFIX = "g";

    private String name;
    private Optional<String> email;
    private @NotNull String token;
    private String facebookId;
    private String googleId;
    private boolean activated;

    @SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
    public static RegDto getInstance(User facebookProfile){
        return new RegDto().setEmail(Optional.of(facebookProfile.getEmail())).setName(FACEBOOK_USER_NAME_PREFIX + facebookProfile.getId()).setFacebookId(facebookProfile.getId()).setToken(random(6)).setActivated(true);
    }

    public static RegDto getInstance(UserRegDetailsDto userRegDetailsDto){
        return new RegDto().setEmail(Optional.of(userRegDetailsDto.email)).setName(userRegDetailsDto.email).setToken(userRegDetailsDto.token);
    }

    @SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
    public static RegDto getInstance(Person person) {
        return new RegDto().setEmail(Optional.of(person.getAccountEmail())).setName(GOOGLE_PLUS_USER_NAME_PREFIX + person.getId()).setGoogleId(person.getId()).setToken(random(6)).setActivated(true);
    }
}
