CREATE TABLE trunk
(
    id            BIGSERIAL PRIMARY KEY,
    trunk_id      BIGINT                   NOT NULL,
    trunk_name    VARCHAR(255),
    switch_id     BIGINT,
    group_type    VARCHAR(50),
    assignment_id BIGINT,
    customer_id   BIGINT,
    from_date     TIMESTAMP WITH TIME ZONE,
    to_date       TIMESTAMP WITH TIME ZONE,
    active        BOOLEAN                  NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_trunk_trunk_id UNIQUE (trunk_id)
);

CREATE INDEX idx_trunk_name
    ON trunk (trunk_name);

CREATE INDEX idx_trunk_switch_id
    ON trunk (switch_id);

CREATE INDEX idx_trunk_customer_id
    ON trunk (customer_id);

CREATE INDEX idx_trunk_assignment_id
    ON trunk (assignment_id);

CREATE INDEX idx_trunk_dates
    ON trunk (from_date, to_date);