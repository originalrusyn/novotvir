package novotvir.security.service.impl

import groovy.transform.CompileStatic
import groovy.transform.TypeChecked
import org.springframework.security.core.userdetails.UserDetailsService
import spock.lang.Specification

// @author Titov Mykhaylo (titov) on 14.08.2014.
class CustomTokenBasedRememberMeServicesImplTest extends Specification {

    def userDetailsServiceMock = Mock(UserDetailsService);
    def customTokenBasedRememberMeServices = new CustomTokenBasedRememberMeServicesImpl("key", userDetailsServiceMock);

    def "should encode userName"() {
        given:
        String userName = ":a:b:c"
        when:
        def encodedUserName = customTokenBasedRememberMeServices.getEncodedUserName(userName);
        then:
        encodedUserName == "|a|b|c"
    }
}
