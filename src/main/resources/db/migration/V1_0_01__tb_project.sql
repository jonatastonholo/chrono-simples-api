CREATE TABLE IF NOT EXISTS db_chrono_simples.tb_project
(
    id              VARCHAR(255) NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(255) NOT NULL,
    hour_value      DECIMAL(12,2) NOT NULL,
    currency_code   VARCHAR(3) NOT NULL,
    created_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP(6) NOT NULL DEFAULT now(),
    deleted         BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE UNIQUE INDEX idx_project_id ON db_chrono_simples.tb_project(id ASC);