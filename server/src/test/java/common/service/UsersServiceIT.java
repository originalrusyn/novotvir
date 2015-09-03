package common.service;

import admin.user.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
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
@ContextConfiguration({"/conf/spring/services-test.xml", "/conf/spring/security.xml", "/conf/spring/dao-test.xml", "/conf/spring/jobs.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsersServiceIT {

    @Resource UserServiceImpl usersService;
    @Resource UserRepository userRepository;
    @Resource EmailAddressRepository emailAddressRepository;

    @Test
    public void shouldFindUsersByNameAndEmailAndRole() {
        //given
        User user = userRepository.save(new User("name", "token").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress"));
        EmailAddress emailAddress = emailAddressRepository.save(new EmailAddress("email@email.com", user));
        user = userRepository.save(user.setPrimaryEmailAddress(emailAddress).setEmailAddresses(singletonList(emailAddress)));
        Pageable pageable = new PageRequest(0, 2);

        //when
        Set<User> users = usersService.findUsers("name='name' and primaryEmailAddress.email='email@email.com' and authorities.role='USER'", pageable);

        //then
        ArrayList<User> userList = new ArrayList<>(users);

        assertThat(users.size(), is(1));
        assertThat(userList.get(0).getId(), is(user.getId()));
    }
}