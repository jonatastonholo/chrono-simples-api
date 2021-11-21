package dev.tonholo.chronosimplesapi.service.validation;

import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import dev.tonholo.chronosimplesapi.service.event.PeriodCreationEvent;
import dev.tonholo.chronosimplesapi.validator.Validation;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.EVENT_CAN_NOT_BE_NULL;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_ID_REQUIRED;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class PeriodCreationEventValidation implements Validation<PeriodCreationEvent> {

    @Override
    public void validate(PeriodCreationEvent periodCreationEvent) {
        notNull(periodCreationEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(periodCreationEvent.getProjectId(), PROJECT_ID_REQUIRED);

        if (Objects.nonNull(periodCreationEvent.getBegin())
                && Objects.nonNull(periodCreationEvent.getEnd())) {
            isTrue(periodCreationEvent.getBegin().isBefore(periodCreationEvent.getEnd()), ExceptionMessage.PERIOD_BEGIN_MUST_BE_BEFORE_END);
        }
    }
}
