package feature.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

// @author: Mykhaylo Titov on 13.09.14 12:46.
@Getter
@Setter
@Accessors(chain = true)
@ToString(exclude = {"accounts", "devices", "emails"})
public class Person {
    final Set<Account> accounts = new HashSet<>();
    final Set<Device> devices = new HashSet<>();
    final Set<Email> emails = new HashSet<>();
    final Set<String> phoneNumbers = new HashSet<>();

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

    public Person addAccount(Account account) {
        accounts.add(account);
        return this;
    }
}
