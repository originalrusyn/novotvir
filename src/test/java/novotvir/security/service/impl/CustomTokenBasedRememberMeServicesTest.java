package novotvir.security.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Titov Mykhaylo (titov)
 *         09.02.14 11:28
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomTokenBasedRememberMeServicesTest {

    String key;
    @Mock UserDetailsService userDetailsService;
    CustomTokenBasedRememberMeServicesImpl customTokenBasedRememberMeServices;

    @Before
    public void setUp(){
        key ="key";
        customTokenBasedRememberMeServices = new CustomTokenBasedRememberMeServicesImpl(key, userDetailsService);
    }

    @Test
    public void shouldEncodeUserName(){
        //given
        String userName = "";

        //when
        String encodedUserName = customTokenBasedRememberMeServices.getEncodedUserName(userName);

        //then
        assertThat(encodedUserName, is(""));
    }

}
