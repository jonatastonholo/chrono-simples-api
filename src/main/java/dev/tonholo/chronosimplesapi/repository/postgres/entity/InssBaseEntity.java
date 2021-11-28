package dev.tonholo.chronosimplesapi.repository.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("db_chrono_simples.tb_inss_base")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InssBaseEntity {
    @Id private Integer id;
    private BigDecimal baseValueRangeBegin;
    private BigDecimal baseValueRangeEnd;
    private BigDecimal basePercentage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
