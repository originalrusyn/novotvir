package admin.user.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

// @author: Titov Mykhaylo (titov) on 28.08.14 22:37.
@SuppressFBWarnings({"USBR_UNNECESSARY_STORE_BEFORE_RETURN"})
@Data
@Accessors(chain = true)
public class CriteriaSuggestionsDTO {
    private String query = "Unit";
    private List<String> suggestions;
}
