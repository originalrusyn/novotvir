package novotvir.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Titov Mykhaylo (titov) on 04.04.2014.
 */
@ToString
@Accessors(chain = true)
public class VersionDto {
    @Setter @Getter String build;
    @Setter @Getter String version;
    @Setter @Getter String revision;
    @Setter @Getter String info;
    @Setter @Getter String branchName;
}
