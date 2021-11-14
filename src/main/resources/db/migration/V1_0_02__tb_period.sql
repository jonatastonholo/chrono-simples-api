CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_period
(
    id              VARCHAR(255) NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id      VARCHAR(255) NOT NULL,
    period_begin    TIMESTAMP(6) NOT NULL DEFAULT now(),
    period_end      TIMESTAMP(6) NULL DEFAULT NULL,
    hour_value      DECIMAL(12,2) NOT NULL,
    description     VARCHAR(255) NULL DEFAULT NULL,
    created_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    deleted         BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_tb_period_tb_project FOREIGN KEY (project_id) REFERENCES db_chrono_simples.tb_project (id)
);

CREATE UNIQUE INDEX idx_period_id ON db_chrono_simples.tb_period(id);
CREATE INDEX idx_period_project_id ON db_chrono_simples.tb_period(project_id);