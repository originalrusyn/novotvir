package novotvir.conf;

import novotvir.persistence.domain.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.config.ResourceMapping;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

// @author: Titov Mykhaylo (titov) on 21.06.13 14:12
@Configuration
public class MyRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        ResourceMapping resourceMapping = config.setResourceMappingForDomainType(User.class);
        resourceMapping.addResourceMappingFor("name").setPath("name");
        resourceMapping.addResourceMappingFor("id").setPath("id");
    }
}
