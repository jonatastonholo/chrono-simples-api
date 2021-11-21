package dev.tonholo.chronosimplesapi.service.validation;

import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import dev.tonholo.chronosimplesapi.service.event.ProjectCreationEvent;
import dev.tonholo.chronosimplesapi.validator.Validation;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.EVENT_CAN_NOT_BE_NULL;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_NAME_REQUIRED;
import static dev.tonholo.chronosimplesapi.validator.Validator.notBlank;
import static dev.tonholo.chronosimplesapi.validator.Validator.notNull;

@Service
public class ProjectCreationEventValidation implements Validation<ProjectCreationEvent> {

    @Override
    public void validate(ProjectCreationEvent projectCreationEvent) {
        notNull(projectCreationEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(projectCreationEvent.getName(), PROJECT_NAME_REQUIRED);
        notNull(projectCreationEvent.getHourValue(), ExceptionMessage.PROJECT_HOUR_VALUE_REQUIRED);
        notNull(projectCreationEvent.getCurrencyCode(), ExceptionMessage.PROJECT_CURRENCY_CODE_REQUIRED);
    }
}
