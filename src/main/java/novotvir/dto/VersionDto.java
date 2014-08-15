package novotvir.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Titov Mykhaylo (titov) on 04.04.2014.
 */
@Data
@Accessors(chain = true)
public class VersionDto {
    String build;
    String version;
    String revision;
    String info;
    String branchName;
}
