CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_expense
(
    id              VARCHAR(255) NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    description     VARCHAR(255) NULL DEFAULT NULL,
    value           DECIMAL(12,2) NOT NULL,
    type            VARCHAR(255) NOT NULL,
    period_begin    DATE NOT NULL,
    period_end      DATE NOT NULL,
    created_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    deleted         BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_expense ON db_chrono_simples.tb_expense(id);