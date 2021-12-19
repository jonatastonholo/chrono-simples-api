package dev.tonholo.chronosimplesapi.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExpenseTypeResponse {
    String value;
    String description;
}
