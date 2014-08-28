package novotvir.dto;// @author: Mykaylo Titov on 28.08.14 22:37.

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CriteriaSuggestionsDTO {
    private String query = "Unit";
    private List<String> suggestions;
}
