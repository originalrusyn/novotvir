package feature.domain;

import command.Command;
import command.SignUp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static common.service.CustomMessageSource.uk_UA_LOCALE;
import static org.junit.Assert.assertNotNull;

// @author: Mykhaylo Titov on 13.09.14 13:18.
@Getter
@Setter
@ToString(exclude = {"device", "appVersion", "account"})
@Accessors(chain = true)
public class Application{

    final Device device;
    final AppVersion appVersion;
    Account account;
    Locale locale;
    List<Command> commandHistory = new ArrayList<>();

    public Application(Device device, AppVersion appVersion){
        this(device, appVersion, uk_UA_LOCALE);
    }

    public Application(Device device, AppVersion appVersion, Locale locale){
        this.device = device;
        this.appVersion = appVersion;
        this.locale = locale;
        device.addApplication(this);
    }

    public static String randomToken(){
        return RandomStringUtils.random(10);
    }

    public SignUp signUp(SignUp signUp){
        assertNotNull(signUp);

        account = new Account(device.person, signUp);
        commandHistory.add(signUp);

        return signUp;
    }
}
