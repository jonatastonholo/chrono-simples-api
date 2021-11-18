CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_financial_dependent
(
    id              VARCHAR(255) NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(255) NULL DEFAULT NULL,
    irrf_deduct     BOOLEAN NOT NULL,
    period_begin    DATE NOT NULL,
    period_end      DATE NOT NULL,
    created_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    deleted         BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_financial_dependent ON db_chrono_simples.tb_financial_dependent(id);