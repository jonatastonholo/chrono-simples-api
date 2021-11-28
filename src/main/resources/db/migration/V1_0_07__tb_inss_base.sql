CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_inss_base
(
    id                      SERIAL NOT NULL PRIMARY KEY,
    base_value_range_begin  DECIMAL(8,2) NOT NULL,
    base_value_range_end    DECIMAL(8,2) NOT NULL,
    base_percentage         DECIMAL(6,2) NOT NULL,
    created_at              TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at              TIMESTAMP(6) NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX idx_inss_base ON db_chrono_simples.tb_inss_base(id);