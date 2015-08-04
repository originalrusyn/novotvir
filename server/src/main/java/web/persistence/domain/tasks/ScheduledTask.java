package web.persistence.domain.tasks;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;

// @author Titov Mykhaylo on 04.08.2015.
@SuppressFBWarnings("UCPM_USE_CHARACTER_PARAMETERIZED_METHOD")
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@RequiredArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
//@Entity
//@DiscriminatorValue("SCHEDULED")
public class ScheduledTask extends Task {

    private static final long serialVersionUID = -3465055694709104276L;

    private static final int MAX_REPEAT_COUNT_MIN_VALUE = 0;

    @NonNull
    private String schedule;

    @Min(MAX_REPEAT_COUNT_MIN_VALUE)
    private int maxRepeatCount;

    public ScheduledTask setMaxRepeatCount(int maxRepeatCount) {
        Preconditions.checkArgument(maxRepeatCount >= MAX_REPEAT_COUNT_MIN_VALUE);
        this.maxRepeatCount = maxRepeatCount;
        return this;
    }
}
