package common.liquibase;

import liquibase.changelog.DatabaseChangeLog;

import java.util.Comparator;

// @author Titov Mykhaylo on 19.06.2015.
public class CustomDBChangeLog extends DatabaseChangeLog {

    public CustomDBChangeLog(String physicalFilePath) {
        super(physicalFilePath);
    }

    final static Comparator<String> versionComparator = new VersionComparator();

    @Override
    protected Comparator<String> getStandardChangeLogComparator() {
        return versionComparator;
    }
}
