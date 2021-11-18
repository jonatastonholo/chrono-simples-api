package dev.tonholo.chronosimplesapi.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinancialDependentRequest {
    private String name;
    private Boolean irrfDeduct;
    private LocalDate periodBegin;
    private LocalDate periodEnd;
}