package common.service;

import admin.user.service.UserServiceImpl;
import common.util.DataBaseIT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import web.persistence.domain.EmailAddress;
import web.persistence.domain.User;
import web.persistence.repository.EmailAddressRepository;
import web.persistence.repository.UserRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration({"/conf/spring/services-test.xml", "/conf/spring/security.xml", "/conf/spring/dao-test.xml"})
public class UsersServiceIT extends DataBaseIT{

    @Resource UserServiceImpl usersService;
    @Resource UserRepository userRepository;
    @Resource EmailAddressRepository emailAddressRepository;

    @Test
    public void shouldFindUsersByNameAndEmailAndRole() {
        //given
        EmailAddress emailAddress = emailAddressRepository.save(new EmailAddress().setEmail("email"));
        User user = userRepository.save(new User().setName("name").setEmailAddresses(singletonList(emailAddress)).setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));
        user = userRepository.save(user.setPrimaryEmailAddress(emailAddress));

        //when
        Set<User> users = usersService.findUsers("name='name' and primaryEmailAddress.email='email' and authorities.role='USER'");

        //then
        ArrayList<User> userList = new ArrayList<>(users);

        assertThat(users.size(), is(1));
        assertThat(userList.get(0).id, is(user.id));
    }
}