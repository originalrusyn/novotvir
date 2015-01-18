package features.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

import static features.domain.DeviceType.ANDROID;
import static java.util.UUID.randomUUID;

// @author: Mykhaylo Titov on 13.09.14 13:24.
@Getter
@Setter
@Accessors(chain = true)
@ToString(exclude = {"deviceType", "applications", "person"})
public class Device {

    final DeviceType deviceType;
    final String deviceUID;
    final Set<Application> applications = new HashSet<>();

    String osVersion;
    Person person;

    public Device(DeviceType deviceType, String osVersion){
        this(deviceType, osVersion, randomUUID().toString());
    }

    public Device(DeviceType deviceType, String osVersion, String deviceUID){
        this.deviceType = deviceType;
        this.osVersion = osVersion;
        this.deviceUID = deviceUID;
    }

    public static Device android(String osVersion){
        return new Device(ANDROID, osVersion);
    }

    public Device addApplication(Application application){
        applications.add(application);
        return this;
    }
}
