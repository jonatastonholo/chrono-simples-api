package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.domain.event.PeriodCreationEvent;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.EVENT_CAN_NOT_BE_NULL;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_ID_REQUIRED;
import static dev.tonholo.chronosimplesapi.validator.Validator.notBlank;
import static dev.tonholo.chronosimplesapi.validator.Validator.notNull;

@Service
public class PeriodCreationEventValidation implements Validation<PeriodCreationEvent> {

    @Override
    public void validate(PeriodCreationEvent periodCreationEvent) {
        notNull(periodCreationEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(periodCreationEvent.getProjectId(), PROJECT_ID_REQUIRED);
    }
}
