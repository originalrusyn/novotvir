package admin.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

// @author: Titov Mykhaylo (titov) on 28.08.14 22:37.
@Data
@Accessors(chain = true)
public class CriteriaSuggestionsDTO {
    private String query = "Unit";
    private List<String> suggestions;
}
