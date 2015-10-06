package common.util;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

// @author Titov Mykhaylo on 02.10.2015.
@Slf4j
@RequiredArgsConstructor
public class CustomEncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    @NonNull
    private final StringEncryptor stringEncryptor;

    @Override
    protected String convertPropertyValue(final String originalValue) {
        try {
            if (!PropertyValueEncryptionUtils.isEncryptedValue(originalValue)) {
                return originalValue;
            }
            return PropertyValueEncryptionUtils.decrypt(originalValue, this.stringEncryptor);
        } catch (EncryptionOperationNotPossibleException e) {
            log.error("Can't decrypt value: [{}]", originalValue);
            throw e;
        }
    }

    @Override
    protected String resolveSystemProperty(final String key) {
        return convertPropertyValue(super.resolveSystemProperty(key));
    }


}
