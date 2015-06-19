package common.liquibase;

import java.util.Comparator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @author Titov Mykhaylo on 19.06.2015.
public class VersionComparator implements Comparator<String> {

    public static final String FILE_NAME_FORMAT = ".*release(\\d+)\\.(\\d+)(\\.(\\d+))?.*\\.sql";
    private final Pattern pattern = Pattern.compile(FILE_NAME_FORMAT);

    @Override
    public int compare(String filePath1, String filePath2) {
        Matcher matcher1 = pattern.matcher(filePath1);
        Matcher matcher2 = pattern.matcher(filePath2);

        if(!matcher1.matches()) {
            throwWrongFileNameFormatException(filePath1);
        }
        if(!matcher2.matches()) {
            throwWrongFileNameFormatException(filePath2);
        }

        for (int i = 1; i < 3; i++) {
            int number1 = getAnInt(matcher1, i);
            int number2 = getAnInt(matcher2, i);

            int comparisonResult = Integer.compare(number1, number2);

            if (comparisonResult != 0) {
                return comparisonResult;
            }
        }
        return 0;
    }

    private int getAnInt(MatchResult matcher, int i) {
        if (matcher.groupCount() == i) {
            return 0;
        }
        return Integer.parseInt(matcher.group(i));
    }

    private void throwWrongFileNameFormatException(String fileName) {
        throw new RuntimeException(String.format("Wrong file name format %s. It has to match following ref exp: %s. " +
                "For example, release1.1.1-SNAPSHOT.sql, release1.2.sql, release10.150-SNAPSHOT.sql", fileName, FILE_NAME_FORMAT));
    }
}
