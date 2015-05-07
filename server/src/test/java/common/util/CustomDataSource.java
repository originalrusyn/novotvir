package common.util;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class CustomDataSource extends BasicDataSource {

    public void clearPool(){
        connectionPool.clear();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
