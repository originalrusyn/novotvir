package novotvir.service.impl;

import novotvir.dto.VersionDto;
import novotvir.service.VersionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author Titov Mykhaylo (titov) on 04.04.2014.
 */
@Service("versionService")
public class VersionServiceImpl implements VersionService {

    @Value("META-INF/MANIFEST.MF")
    private Resource manifestFile;
    private Attributes manifestAttributes;

    @PostConstruct
    public void setManifest() throws IOException {
        Manifest mf = new Manifest(manifestFile.getInputStream());
        manifestAttributes = mf.getMainAttributes();
    }

    @Override
    public VersionDto getVersion() {
        VersionDto dto = new VersionDto();
        dto.setVersion(manifestAttributes.getValue(IMPLEMENTATION_VERSION));
        String buildNumber = manifestAttributes.getValue(IMPLEMENTATION_BUILD);
        if (StringUtils.hasText(buildNumber)) {
            String[] vers = buildNumber.split("-", 2);
            dto.setBuild(vers[1]);
            dto.setRevision(vers[0]);
        }
        dto.setBranchName(manifestAttributes.getValue(BRANCH_ATTRIBUTE));
        return dto;
    }
}
