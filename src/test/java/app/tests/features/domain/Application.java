package app.tests.features.domain;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang.RandomStringUtils;

// @author: Mykhaylo Titov on 13.09.14 13:18.
@Data
@Accessors(chain = true)
@ToString(exclude = "device")
public class Application {
    final Device device;
    final AppVersion appVersion;
    Account account;

    public Application(Device device, AppVersion appVersion){
        this.device = device;
        this.appVersion = appVersion;
        device.addApplication(this);
    }

    public static String randomToken(){
        return RandomStringUtils.random(10);
    }

}
