package common.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.experimental.Accessors;
import common.util.RequestUtils;

// @author Titov Mykhaylo (titov) on 22.05.2014.
@SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN"})
@Data
@Accessors(chain = true)
public class ErrorDto {
    public static final String ERROR_DTO = "errorDto";

    private String requestUrl = RequestUtils.getRequestUrl();
    private String errCode;
    private String localizedMessage;
}
