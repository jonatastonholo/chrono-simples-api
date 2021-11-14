package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import dev.tonholo.chronosimplesapi.web.model.ProjectRequest;
import org.springframework.stereotype.Service;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PROJECT_NAME_REQUIRED;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.REQUEST_CAN_NOT_BE_NULL;
import static dev.tonholo.chronosimplesapi.validator.Validator.notBlank;
import static dev.tonholo.chronosimplesapi.validator.Validator.notNull;

@Service
public class ProjectRequestValidation implements Validation<ProjectRequest> {

    @Override
    public void validate(ProjectRequest projectRequest) {
        notNull(projectRequest, REQUEST_CAN_NOT_BE_NULL);
        notBlank(projectRequest.getName(), PROJECT_NAME_REQUIRED);
        notNull(projectRequest.getHourValue(), ExceptionMessage.PROJECT_HOUR_VALUE_REQUIRED);
        notNull(projectRequest.getCurrencyCode(), ExceptionMessage.PROJECT_CURRENCY_CODE_REQUIRED);
    }
}
