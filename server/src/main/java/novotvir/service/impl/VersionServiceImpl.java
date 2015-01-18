package novotvir.service.impl;

import novotvir.dto.VersionDto;
import novotvir.service.VersionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import static org.springframework.util.StringUtils.hasText;

// @author Titov Mykhaylo (titov) on 04.04.2014.
@Service("versionService")
public class VersionServiceImpl implements VersionService {

    @Value("classpath:META-INF/MANIFEST.MF") Resource manifestFile;
    Attributes manifestAttributes;

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
        if (hasText(buildNumber)) {
            String[] vers = buildNumber.split("-", 2);
            dto.setBuild(vers[1]);
            dto.setRevision(vers[0]);
        }
        dto.setBranchName(manifestAttributes.getValue(BRANCH_ATTRIBUTE));
        return dto;
    }
}
