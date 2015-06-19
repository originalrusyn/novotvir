package common.liquibase;

import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.xml.XMLChangeLogSAXParser;
import liquibase.resource.ResourceAccessor;

// @author Titov Mykhaylo on 19.06.2015.
public class CustomXMLChangeLogSAXParser extends XMLChangeLogSAXParser {

    @Override
    public int getPriority() {
        return  Integer.MAX_VALUE;
    }

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        ParsedNode parsedNode = parseToNode(physicalChangeLogLocation, changeLogParameters, resourceAccessor);
        if (parsedNode == null) {
            return null;
        }

        DatabaseChangeLog changeLog = new CustomDBChangeLog(physicalChangeLogLocation);
        changeLog.setChangeLogParameters(changeLogParameters);
        try {
            changeLog.load(parsedNode, resourceAccessor);
        } catch (Exception e) {
            throw new ChangeLogParseException(e);
        }

        return changeLog;
    }
}
