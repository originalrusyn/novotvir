package common.liquibase;

import java.io.Serializable;
import java.util.Comparator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @author Titov Mykhaylo on 19.06.2015.
public class VersionComparator implements Comparator<String>, Serializable{

    private static final long serialVersionUID = 1848516584535155650L;

    private static final Pattern PATTERN = Pattern.compile(".*release(\\d+)\\.(\\d+)(\\.(\\d+))?\\.sql");

    @Override
    public int compare(String filePath1, String filePath2) {
        Matcher matcher1 = PATTERN.matcher(filePath1);
        Matcher matcher2 = PATTERN.matcher(filePath2);

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
                "For example, release1.2.sql, release10.150.5.sql", fileName, PATTERN));
    }
}
