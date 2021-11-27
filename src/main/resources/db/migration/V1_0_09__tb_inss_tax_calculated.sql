CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_inss_tax_calculated
(
    id                 VARCHAR(255) NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    period_begin       TIMESTAMP(6) NOT NULL DEFAULT now(),
    period_end         TIMESTAMP(6) NULL DEFAULT NULL,
    base_percentage    DECIMAL(6,2) NOT NULL,
    base_pro_labor     DECIMAL(12,2) NOT NULL,
    amount             DECIMAL(6,2) NOT NULL,
    created_at         TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at         TIMESTAMP(6) NOT NULL DEFAULT now(),
    deleted            BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_inss_tax_calculated ON db_chrono_simples.tb_inss_tax_calculated(id);