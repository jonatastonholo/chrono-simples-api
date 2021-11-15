package dev.tonholo.chronosimplesapi.validator;

import dev.tonholo.chronosimplesapi.domain.event.PeriodUpdateEvent;
import dev.tonholo.chronosimplesapi.exception.ExceptionMessage;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.EVENT_CAN_NOT_BE_NULL;
import static dev.tonholo.chronosimplesapi.exception.ExceptionMessage.PERIOD_ID_REQUIRED;
import static dev.tonholo.chronosimplesapi.validator.Validator.*;

@Service
public class PeriodUpdateEventValidation implements Validation<PeriodUpdateEvent> {
    @Override
    public void validate(PeriodUpdateEvent periodUpdateEvent) {
        notNull(periodUpdateEvent, EVENT_CAN_NOT_BE_NULL);
        notBlank(periodUpdateEvent.getId(), PERIOD_ID_REQUIRED);

        if (Objects.nonNull(periodUpdateEvent.getBegin())
                && Objects.nonNull(periodUpdateEvent.getEnd())) {
            isTrue(periodUpdateEvent.getBegin().isBefore(periodUpdateEvent.getEnd()), ExceptionMessage.PERIOD_BEGIN_MUST_BE_BEFORE_END);
        }
    }
}
