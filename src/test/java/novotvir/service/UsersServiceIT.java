package novotvir.service;

import novotvir.persistence.domain.User;
import novotvir.persistence.repository.UserRepository;
import novotvir.util.DataBaseIT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired UsersService usersService;
    @Resource UserRepository userRepository;

    @Test
    public void shouldFindUserByName() {
        //given
        String q = "name";
        User user = userRepository.save(new User().setName(q).setEmail("email").setFacebookId("facebookId").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers(q);

        //then
        assertThat(users.size(), is(1));
        assertThat(users.iterator().next().id, is(user.id));
    }

    @Test
    public void shouldFindUserByEmail() {
        //given
        String q = "email";
        User user = userRepository.save(new User().setName("name").setEmail(q).setFacebookId("facebookId").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers(q);

        //then
        assertThat(users.size(), is(1));
        assertThat(users.iterator().next().id, is(user.id));
    }

    @Test
    public void shouldFindUserByActivation() {
        //given
        boolean q = true;
        User user = userRepository.save(new User().setName("name").setEmail("email").setFacebookId("facebookId").setActivationToken("activationToken").setActivated(q).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers(Boolean.toString(q));

        //then
        ArrayList<User> userList = new ArrayList<>(users);

        assertThat(users.size(), is(2));
        assertThat(userList.get(0).id, is(1L));
        assertThat(userList.get(1).id, is(user.id));
    }

    @Test
    public void shouldFindUsersByRole() {
        //given
        String q = "USER";
        User user = userRepository.save(new User().setName("name").setEmail("email").setFacebookId("facebookId").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers(q);

        //then
        ArrayList<User> userList = new ArrayList<>(users);

        assertThat(users.size(), is(2));
        assertThat(userList.get(0).id, is(1L));
        assertThat(userList.get(1).id, is(user.id));
    }
}