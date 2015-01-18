package novotvir.util;// @author: m on 09.08.14 18:54.

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.annotation.Resource;

@Slf4j
public class DataBaseIT{
    @Resource CustomDataSource customDataSource;
    @Resource DataSourceInitializer dataSourceInitializer;
    @Resource SpringLiquibase springLiquibase;

    @Before
    public void setUp()  {
        customDataSource.clearPool();
        dataSourceInitializer.afterPropertiesSet();
        try {
            springLiquibase.afterPropertiesSet();
        } catch (LiquibaseException e) {
            log.error("Couldn't populate database", e);
        }
    }
}
