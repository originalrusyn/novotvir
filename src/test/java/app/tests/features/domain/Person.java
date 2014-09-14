package app.tests.features.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;


// @author: Mykhaylo Titov on 13.09.14 12:46.
@Data
@Accessors(chain = true)
public class Person {
    transient final Set<Device> devices = new HashSet<>();
    transient final Set<Email> emails = new HashSet<>();
    transient final Set<String> phoneNumbers = new HashSet<>();

    public Person addDevice(Device device){
        devices.add(device.setPerson(this));
        return this;
    }

    public Person addEmail(Email email){
        emails.add(email.setPerson(this));
        return this;
    }

    public Person addApplication(Application application){
        addDevice(application.getDevice());
        return this;
    }

}
