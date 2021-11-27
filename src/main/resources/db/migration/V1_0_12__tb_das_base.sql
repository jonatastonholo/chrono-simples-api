CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_das_base
(
    id                      SERIAL NOT NULL PRIMARY KEY,
    base_value_range_begin  DECIMAL(13,2) NOT NULL,
    base_value_range_end    DECIMAL(13,2) NOT NULL,
    aliquot                 DECIMAL(6,3) NOT NULL,
    deduction               DECIMAL(13,2) NOT NULL,
    created_at              TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at              TIMESTAMP(6) NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX idx_das_base_id ON db_chrono_simples.tb_das_base(id);