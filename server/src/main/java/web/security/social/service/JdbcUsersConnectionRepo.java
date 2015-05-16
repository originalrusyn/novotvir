package web.security.social.service;

import common.util.RequestUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static web.security.social.ConnectionSignUpImpl.SIGN_UP;

// @author: Mykhaylo Titov on 16.05.15 00:44.
public class JdbcUsersConnectionRepo extends JdbcUsersConnectionRepository {

    final JdbcTemplate jdbcTemplate;
    String tablePrefix = "";
    ConnectionSignUp connectionSignUp;

    public JdbcUsersConnectionRepo(DataSource dataSource, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor, ConnectionSignUp connectionSignUp) {
        super(dataSource, connectionFactoryLocator, textEncryptor);

        Assert.notNull(dataSource);
        Assert.notNull(connectionSignUp);

        this.connectionSignUp = connectionSignUp;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        super.setTablePrefix(tablePrefix);
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        boolean signUp = Boolean.parseBoolean(RequestUtils.getHttpServletRequest().getParameter(SIGN_UP));
        List<String> localUserIds = jdbcTemplate.queryForList("select userId from " + tablePrefix + "UserConnection where providerId = ? and providerUserId = ?",
                String.class, key.getProviderId(), key.getProviderUserId());
        if (signUp) {
            if(!localUserIds.isEmpty()){
                return Collections.emptyList();
            }else {
                String newUserId = connectionSignUp.execute(connection);
                createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }
        return localUserIds;

    }
}
