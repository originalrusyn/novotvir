package common.version.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.experimental.Accessors;

// @author Titov Mykhaylo (titov) on 04.04.2014.
@SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN", "UCPM_USE_CHARACTER_PARAMETERIZED_METHOD"})
@Data
@Accessors(chain = true)
public class VersionDto {
    String build;
    String version;
    String revision;
    String info;
    String branchName;
}
