package web.persistence.domain.tasks;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

// @author Titov Mykhaylo on 04.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
//@Entity
//@DiscriminatorValue("REGULAR")
public class RegularTask extends Task {

    private static final long serialVersionUID = 1534038352008950194L;

}
