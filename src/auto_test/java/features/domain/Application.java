package features.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Locale;

// @author: Mykhaylo Titov on 13.09.14 13:18.
@Getter
@Setter
@ToString(exclude = {"device", "appVersion", "account"})
@Accessors(chain = true)
public class Application {

    public static final Locale ua_UA_LOCALE = new Locale("ua", "UA");

    final Device device;
    final AppVersion appVersion;
    Account account;
    Locale locale;

    public Application(Device device, AppVersion appVersion){
        this(device, appVersion, ua_UA_LOCALE);
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
}
