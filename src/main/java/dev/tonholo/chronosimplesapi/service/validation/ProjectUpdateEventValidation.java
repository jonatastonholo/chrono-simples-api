package dev.tonholo.chronosimplesapi.service.validation;

import dev.tonholo.chronosimplesapi.exception.ApiException;
import dev.tonholo.chronosimplesapi.service.event.ProjectUpdateEvent;
import dev.tonholo.chronosimplesapi.validator.Validation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.*;
import static dev.tonholo.chronosimplesapi.validator.Validator.notBlank;
import static dev.tonholo.chronosimplesapi.validator.Validator.notNull;

@Service
public class ProjectUpdateEventValidation implements Validation<ProjectUpdateEvent> {

    @Override
    public void validate(ProjectUpdateEvent projectUpdateEvent) {
        notNull(projectUpdateEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(projectUpdateEvent.getId(), PROJECT_ID_REQUIRED);

        if (Strings.isBlank(projectUpdateEvent.getName())
            && Objects.isNull(projectUpdateEvent.getHourValue())
            && Objects.isNull(projectUpdateEvent.getCurrencyCode())) {
            throw new ApiException(PROJECT_UPDATE_AT_LEAST_ONE_PARAMETER_MUST_BE_FILLED);
        }


    }
}
