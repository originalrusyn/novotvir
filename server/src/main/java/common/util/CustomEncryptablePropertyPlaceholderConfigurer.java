package common.util;

import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.TextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

// @author Titov Mykhaylo on 02.10.2015.
public class CustomEncryptablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    final Logger logger = LoggerFactory.getLogger(CustomEncryptablePropertyPlaceholderConfigurer.class);

    private final StringEncryptor stringEncryptor;
    private final TextEncryptor textEncryptor;

    public CustomEncryptablePropertyPlaceholderConfigurer(
            final StringEncryptor stringEncryptor) {
        super();
        CommonUtils.validateNotNull(stringEncryptor, "Encryptor cannot be null");
        this.stringEncryptor = stringEncryptor;
        this.textEncryptor = null;
    }


    public CustomEncryptablePropertyPlaceholderConfigurer(final TextEncryptor textEncryptor) {
        super();
        CommonUtils.validateNotNull(textEncryptor, "Encryptor cannot be null");
        this.stringEncryptor = null;
        this.textEncryptor = textEncryptor;
    }

    @Override
    protected String convertPropertyValue(final String originalValue) {
        try {
            if (!PropertyValueEncryptionUtils.isEncryptedValue(originalValue)) {
                return originalValue;
            }
            if (this.stringEncryptor != null) {
                return PropertyValueEncryptionUtils.decrypt(originalValue,
                        this.stringEncryptor);

            }
            return PropertyValueEncryptionUtils.decrypt(originalValue, this.textEncryptor);
        } catch (EncryptionOperationNotPossibleException e) {
            logger.error("Can't decrypt value: [{}]", originalValue);
            throw e;
        }
    }

    @Override
    protected String resolveSystemProperty(final String key) {
        return convertPropertyValue(super.resolveSystemProperty(key));
    }


}
