--liquibase formatted sql

--changeset Yaroslav:1
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

--changeset Yaroslav:2

INSERT INTO shelter_buddy (shelter_name, address, shelter_phone, security_phone, driving_directions,
                           safety_recommendations, shelter_info)
VALUES ('Приют "Собачий дом"', 'улица Чернышевского, дом 7', '+7 (812) 777-77-77', '+7 (812) 888-88-88',
        'От станции метро "Площадь Восстания" до остановки "улица Чернышевского". Идти примерно 500 метров.',
        'На территории приюта запрещается кормить животных из рук, использовать свой транспорт на территории приюта, прикармливать животных своими лекарствами или проводить ветеринарные манипуляции. Обращаться только к персоналу приюта',
        'Приют "Собачий дом" был создан в 2010 году для временного содержания бездомных собак. Здесь живет большое количество животных, находящихся в поиске новых заботливых хозяев.');

--changeset Yaroslav:3

INSERT INTO shelter_buddy (shelter_name, address, shelter_phone, security_phone, driving_directions,
                           safety_recommendations, shelter_info)
VALUES ('Приют "Кошачий дом"', 'улица Белы Куна, дом 12', '+7 (812) 555-55-55', '+7 (812) 666-66-66',
        'От станции метро "Ладожская" до остановки "улица Белы Куна". Идти примерно 300 метров.',
        'На территории приюта запрещается кормить животных из рук, использовать свой транспорт на территории приюта, прикармливать животных своими лекарствами или проводить ветеринарные манипуляции. Обращаться только к персоналу приюта',
        'Приют "Кошачий дом" был создан в 2005 году для временного содержания бездомных кошек. Здесь живет большое количество животных, находящихся в поиске новых заботливых хозяев.');