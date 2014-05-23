package novotvir.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import novotvir.utils.RequestUtils;

/**
 * @author Titov Mykhaylo (titov) on 22.05.2014.
 */
@Data
@Accessors(chain = true)
public class ErrorDto {
    public static final String ERROR_DTO = "errorDto";

    private String requestUrl = getRequestUrl();
    private String messageCode;
    private Object[] messageArgs;
}
