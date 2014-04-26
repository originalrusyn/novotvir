package novotvir.service;

import novotvir.dto.VersionDto;

/**
 * @author Titov Mykhaylo (titov) on 04.04.2014.
 */
public interface VersionService {
    String IMPLEMENTATION_BUILD = "Implementation-Build";
    String IMPLEMENTATION_VERSION = "Implementation-Version";
    String BRANCH_ATTRIBUTE = "Branch";

    public VersionDto getVersion();
}
