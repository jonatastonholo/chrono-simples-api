package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.domain.event.StopwatchStartEvent;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.EVENT_CAN_NOT_BE_NULL;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_ID_REQUIRED;
import static dev.tonholo.chronosimplesapi.validator.Validator.notBlank;
import static dev.tonholo.chronosimplesapi.validator.Validator.notNull;

@Service
public class StopwatchStartValidation implements Validation<StopwatchStartEvent> {

    @Override
    public void validate(StopwatchStartEvent stopwatchStartEvent) {
        notNull(stopwatchStartEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(stopwatchStartEvent.getProjectId(), PROJECT_ID_REQUIRED);
    }
}
