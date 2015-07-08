package common.liquibase;

import liquibase.integration.spring.SpringLiquibase;
import liquibase.servicelocator.ServiceLocator;

// @author Titov Mykhaylo on 19.06.2015.
public class CustomSpringLiquibase extends SpringLiquibase {

    static ServiceLocator serviceLocator = ServiceLocator.getInstance();

    static {
        serviceLocator.addPackageToScan(CustomXMLChangeLogSAXParser.class.getPackage().getName());
    }
}
