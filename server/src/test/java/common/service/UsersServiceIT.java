package common.service;

import admin.user.service.UserServiceImpl;
import web.persistence.domain.User;
import web.persistence.repository.UserRepository;
import common.util.DataBaseIT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextConfiguration({"/conf/spring/services-test.xml", "/conf/spring/security.xml", "/conf/spring/dao-test.xml"})
public class UsersServiceIT extends DataBaseIT{

    @Resource UserServiceImpl usersService;
    @Resource UserRepository userRepository;

    @Test
    public void shouldFindUsersByNameAndEmailAndRole() {
        //given
        User user = userRepository.save(new User().setName("name").setEmail("email").setFacebookId("facebookId").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers("name='name' and email='email' and authorities.role='USER'");

        //then
        ArrayList<User> userList = new ArrayList<>(users);

        assertThat(users.size(), is(1));
        assertThat(userList.get(0).id, is(user.id));
    }
}