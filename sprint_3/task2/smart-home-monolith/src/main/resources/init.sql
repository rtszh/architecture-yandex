CREATE TABLE IF NOT EXISTS heating_systems (
    id BIGSERIAL PRIMARY KEY,
    is_on BOOLEAN NOT NULL,
    target_temperature DOUBLE PRECISION NOT NULL,
    current_temperature DOUBLE PRECISION NOT NULL,
    account_id BIGINT,
    serial_number VARCHAR
);

CREATE TABLE IF NOT EXISTS temperature_sensors (
    id BIGSERIAL PRIMARY KEY,
    current_temperature DOUBLE PRECISION NOT NULL,
    last_updated TIMESTAMP NOT NULL
);

INSERT INTO heating_systems
(is_on, target_temperature, current_temperature, account_id, serial_number)
VALUES
(true, 20, 15, 10, 'oldSerNum10');
