package common.liquibase

import spock.lang.Specification

// @author Titov Mykhaylo on 19.06.2015.
class VersionComparatorTest extends Specification {

    def versionComparator = new VersionComparator()

    def "Compare"() {
        expect:
        versionComparator.compare(fileName1, fileName2) == result

        where:
        fileName1            | fileName2              | result
        "release1.1.sql"     | "release1.10.sql"      | -1
        "SQL/release2.1.sql" | "release1.10.sql"      | 1
        "release1.1.0.sql"   | "release1.10.sql"      | -1
        "release1.1.0.sql"   | "sql/release1.1.0.sql" | 0
        "release1.1.0.sql"   | "release1.1.sql"       | 0
    }
}
