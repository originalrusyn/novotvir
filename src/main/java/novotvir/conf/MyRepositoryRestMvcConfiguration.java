package novotvir.conf;

import novotvir.persistence.domain.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * @author: Titov Mykhaylo (titov)
 * 21.06.13 14:12
 */
@Configuration
public class MyRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setResourceMappingForDomainType(User.class).addResourceMappingFor("name").setPath("name");
        config.setResourceMappingForDomainType(User.class).addResourceMappingFor("id").setPath("id");
    }
}
