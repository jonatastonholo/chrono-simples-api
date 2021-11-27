CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_irrf_base
(
    id                      SERIAL NOT NULL PRIMARY KEY,
    base_value_range_begin  DECIMAL(8,2) NOT NULL,
    base_value_range_end    DECIMAL(8,2) NOT NULL,
    aliquot                 DECIMAL(6,3) NOT NULL,
    deduction               DECIMAL(6,2) NOT NULL,
    created_at              TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at              TIMESTAMP(6) NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX idx_irrf_base_id ON db_chrono_simples.tb_irrf_base(id);