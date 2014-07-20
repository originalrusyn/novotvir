package novotvir.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import novotvir.utils.RequestUtils;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
@Data
@Accessors(chain = true)
public class ErrorDto {
    public static final String ERROR_DTO = "errorDto";

    private String requestUrl = RequestUtils.getRequestUrl();
    private String errCode;
    private String localizedMessage;
}
