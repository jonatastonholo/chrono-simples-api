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
@Table("db_chrono_simples.tb_project")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {
    @Id private String id;
    private String name;
    private BigDecimal hourValue;
    private CurrencyCodeType currencyCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean deleted;
}
