package app.tests.features.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

import static app.tests.features.domain.DeviceType.ANDROID;
import static java.util.UUID.randomUUID;

// @author: Mykhaylo Titov on 13.09.14 13:24.
@Data
@Accessors(chain = true)
public class Device {

    final DeviceType deviceType;
    final String deviceUID;
    transient final Set<Application> applications = new HashSet<>();

    String osVersion;
    Person person;

    public Device(DeviceType deviceType, String osVersion){
        this.deviceType=deviceType;
        this.osVersion=osVersion;
        this.deviceUID = randomUUID().toString();
    }

    public static Device android(String osVersion){
        return new Device(ANDROID, osVersion);
    }

    public Device addApplication(Application application){
        applications.add(application);
        return this;
    }
}
