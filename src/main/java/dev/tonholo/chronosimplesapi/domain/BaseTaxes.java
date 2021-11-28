package dev.tonholo.chronosimplesapi.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class BaseTaxes {
    private List<InssBase> inssBases;
    private List<IrrfBase> irrfBases;
    private List<DasBase> dasBases;
    private BigDecimal deductionAmountPerFinancialDependent;
    private BigDecimal defaultRFactor;
    private BigDecimal pensionCeiling;
}