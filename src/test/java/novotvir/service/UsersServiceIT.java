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
    public void shouldFindUsers() {
        //given
        String q = "USER";
        User user = userRepository.save(new User().setName("name").setEmail("email").setFacebookId("facebookId").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers(q);

        //then
        assertThat(users.size(), is(0));
    }

    @Test
    public void shouldFindUsers2() {
        //given
        String q = "USER";
        User user = userRepository.save(new User().setName("name").setEmail("email").setFacebookId("facebookId").setActivationToken("activationToken").setActivated(true).setLastSignInIpAddress("lastSignInIpAddress").setToken("token"));

        //when
        Set<User> users = usersService.findUsers(q);

        //then
        assertThat(users.size(), is(0));
    }
}