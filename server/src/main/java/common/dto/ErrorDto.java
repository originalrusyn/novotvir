package common.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import common.util.RequestUtils;

// @author Titov Mykhaylo (titov) on 22.05.2014.
@SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN", "UCPM_USE_CHARACTER_PARAMETERIZED_METHOD"})
@Data
@Accessors(chain = true)
public class ErrorDto {
    public static final String ERROR_DTO = "errorDto";

    private final @NonNull String requestUrl = RequestUtils.getRequestUrl();
    private final @NonNull String errCode;
    private final @NonNull String localizedMessage;
}
