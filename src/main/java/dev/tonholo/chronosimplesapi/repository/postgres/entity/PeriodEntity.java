package dev.tonholo.chronosimplesapi.repository.postgres.entity;

import dev.tonholo.chronosimplesapi.domain.type.CurrencyCodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("db_chrono_simples.tb_period")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodEntity {
    @Id private String id;
    private String projectId;
    private LocalDateTime periodBegin;
    private LocalDateTime periodEnd;
    private BigDecimal hourValue;
    private CurrencyCodeType currency;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
}
