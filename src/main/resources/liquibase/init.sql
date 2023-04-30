--liquibase formatted sql --changeset yarosla7: 1
CREATE TABLE shelter_buddy
(
    shelter_id             BIGSERIAL PRIMARY KEY,
    shelter_name           VARCHAR(255) NOT NULL,
    address                VARCHAR(255) NOT NULL,
    shelter_phone          VARCHAR(20)  NOT NULL,
    security_phone         VARCHAR(20)  NOT NULL,
    driving_directions     TEXT         NOT NULL,
    safety_recommendations TEXT         NOT NULL,
    shelter_info           TEXT         NOT NULL
);
CREATE TABLE shelter_clients
(
    user_id     BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    number      VARCHAR(255) NOT NULL,
    took_animal BOOLEAN      NOT NULL,
    shelter_id  BIGINT       NOT NULL REFERENCES shelter_buddy (shelter_id) ON DELETE CASCADE
);
CREATE INDEX ON shelter_clients (shelter_id);
COMMENT ON TABLE shelter_buddy IS 'Таблица, содержащая информацию об приютах для животных';
COMMENT ON TABLE shelter_clients IS 'Таблица, содержащая информацию о клиентах, которые использовали приюты для животных';
COMMENT ON COLUMN shelter_buddy.shelter_id IS 'Уникальный идентификатор для каждого приюта для животных';
COMMENT ON COLUMN shelter_buddy.shelter_name IS 'Название приюта для животных';
COMMENT ON COLUMN shelter_buddy.address IS 'Адрес приюта для животных';
COMMENT ON COLUMN shelter_buddy.shelter_phone IS 'Номер телефона приюта для животных';
COMMENT ON COLUMN shelter_buddy.security_phone IS 'Номер телефона экстренного контакта для приюта для животных';
COMMENT ON COLUMN shelter_buddy.driving_directions IS 'Инструкции для проезда к приюту для животных';
COMMENT ON COLUMN shelter_buddy.safety_recommendations IS 'Рекомендации по безопасности для посетителей приюта для животных';
COMMENT ON COLUMN shelter_buddy.shelter_info IS 'Дополнительная информация о приюте для животных';
COMMENT ON COLUMN shelter_clients.user_id IS 'Уникальный идентификатор для каждого клиента приютов для животных';
COMMENT ON COLUMN shelter_clients.name IS 'Имя клиента, который использовал приют для животных';
COMMENT ON COLUMN shelter_clients.number IS 'Номер телефона клиента, который использовал приют для животных';
COMMENT ON COLUMN shelter_clients.took_animal IS 'True, если клиент взял животное из приюта';
COMMENT ON COLUMN shelter_clients.shelter_id IS 'Внешний ключ, ссылающийся на приют для животных, который использовал клиент';