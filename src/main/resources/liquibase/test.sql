-- liquibase formatted sql

-- changeset Yaroslav:4

CREATE TABLE animals
(
    animal_id     BIGSERIAL PRIMARY KEY,
    pet_name      VARCHAR(30) NOT NULL,
    breed         VARCHAR(40) NOT NULL,
    pet_age       INT         NOT NULL,
    is_in_shelter BOOLEAN     NOT NULL,
    user_id       INT,
    FOREIGN KEY (user_id) REFERENCES shelter_clients (user_id)
);

CREATE TABLE animal_advice
(
    introduction_rules                    TEXT,
    necessary_documents                   TEXT,
    transportation_recommendations        TEXT,
    adult_home_arrangement                TEXT,
    home_arrangement_with_limited_ability TEXT,
    kitten_home_arrangement               TEXT,
    puppy_home_arrangement                TEXT,
    initial_communication_with_dog_advice TEXT,
    communication_with_grown_dog_advice   TEXT,
    reasons_for_refusing_animal_transfer  TEXT
);

--changeset Yaroslav:5

ALTER TABLE shelter_clients
    ADD COLUMN animal_id INT REFERENCES animals (animal_id);

--changeset Yaroslav:6

INSERT INTO animal_advice (introduction_rules, necessary_documents, transportation_recommendations,
                           adult_home_arrangement, home_arrangement_with_limited_ability, kitten_home_arrangement,
                           puppy_home_arrangement, initial_communication_with_dog_advice,
                           communication_with_grown_dog_advice, reasons_for_refusing_animal_transfer)
VALUES ('1. Познакомьтесь с животным на его территории. 2. Позвольте животному подойти к вам, не навязывайте свою игру. 3. Изучите язык тела животного, понимайте его сигналы',
        '1. Ветеринарный паспорт. 2. Клеймо (для собак). 3. Договор на передачу животного владельцу',
        '1. Перевозите животное только в переноске. 2. Убедитесь, что во время перевозки достаточно тепла и комфорта. 3. При наличии медицинских противопоказаний обратитесь к ветеринару за советом',
        '1. Зонтики и наличники, чтобы животное не бегало от дома. 2. Укрытие от солнца и дождя. 3. Достаточное пространство для движения. 4. Регулярная уборка помещения от пыли и мусора',
        '1. Обеспечить закрытый и безопасный доступ к местам, где может произойти падение. 2. Убрать все острые края и опасные предметы. 3. Ознакомиться со специальными приспособлениями для животных с ограниченными возможностями',
        '1. Безопасное место для игр и развлечений. 2. Корм и поилки, которые соответствуют возрасту котенка. 3. Окна с защитными решетками',
        '1. Сделайте зону, где щенок может играть и спать безопасно. 2. Лежак и поилки, которые соответствуют возрасту щенка. 3. Щенок должен иметь свой туалет.',
        '1. Не позволяйте собаке разрывать поводок. 2. Изучите язык тела собаки, чтобы понимать ее сигналы. 3. Используйте руку в качестве щита, чтобы защитить себя от возможного нападения.',
        '1. Не держите собаку на поводке даже дома. 2. Изучите язык тела собаки, чтобы понимать ее сигналы. 3. Установите доверительные отношения со своей собакой.',
        '1. Некуда поместить животное в вашей квартире. 2. Вы уже имеете число животных, которые превышают разумные пределы. 3. Вы не можете предоставить правильный уход и лечение животного. 4. Отказ в владении животным в соответствии с вашими условиями проживания. 5. Отказ владельца от предоставления нашего приюта сведений о заболеваниях или проблемах животного.');

--changeset Yaroslav:7

ALTER TABLE shelter_buddy
    ADD CONSTRAINT shelter_name_unique UNIQUE (shelter_name);

--changeset Yaroslav:8

CREATE TABLE reports
(
    id                   BIGSERIAL PRIMARY KEY,
    user_id              INT,
    shelter_name         VARCHAR(255),
    date                 DATE,
    report_text          TEXT,
    telegram_server_link VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES shelter_clients (user_id),
    FOREIGN KEY (shelter_name) REFERENCES shelter_buddy (shelter_name)
);

--changeset Yaroslav:9

INSERT INTO animals (pet_name, breed, pet_age, is_in_shelter, user_id)
VALUES ('Борис', 'Британская короткошерстная', 4, true, null),
       ('Мурка', 'Шотландская вислоухая', 2, true, null),
       ('Маркиз', 'Персидская', 3, true, null),
       ('Вася', 'Сибирская', 7, true, null),
       ('Фрекен Бок', 'Норвежская лесная', 1, true, null),
       ('Барбос', 'Лабрадор', 2, true, null),
       ('Шарик', 'Джек Рассел терьер', 3, true, null),
       ('Тайсон', 'Американский питбультерьер', 8, true, null),
       ('Хатико', 'Акита-ину', 6, true, null),
       ('Рекс', 'Немецкая овчарка', 4, true, null),
       ('Рысик', 'Бенгальская', 2, true, null),
       ('Мурзик', 'Персидская', 5, true, null),
       ('Шалун', 'Корги', 1, true, null),
       ('Чарли', 'Бигль', 2, true, null),
       ('Дружок', 'Дворняжка', 3, true, null),
       ('Лиза', 'Пекинес', 6, true, null),
       ('Джекки', 'Чихуахуа', 4, true, null),
       ('Гарри', 'Скоттиш-терьер', 9, true, null),
       ('Бим', 'Бульдог', 7, true, null),
       ('Буся', 'Йоркширский терьер', 5, true, null);

--changeset Yaroslav:10
DROP TABLE reports;


--changeset Yaroslav:11
CREATE TABLE reports
(
    id                  BIGSERIAL PRIMARY KEY,
    user_id             INT,
    shelter_id          INT,
    date                DATE,
    report_text         TEXT,
    telegram_photo_link VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES shelter_clients (user_id),
    FOREIGN KEY (shelter_id) REFERENCES shelter_buddy (shelter_id)
);

--changeset Yaroslav:12

ALTER TABLE animal_advice
    ADD COLUMN id BIGSERIAL PRIMARY KEY;

--changeset Vladimir:1


CREATE TABLE choice
(
    id           BIGINT,
    shelter_type VARCHAR(255)
);

--changeset Vladimir:2

ALTER TABLE shelter_buddy
    ADD COLUMN schedule varchar(320);
UPDATE shelter_buddy
set schedule='С 8:00 до 21:00, без выходных'
where shelter_id = 1;
UPDATE shelter_buddy
set schedule='С 9:00 до 22:00, без выходных'
where shelter_id = 2;

--changeset Vladimir:3

ALTER TABLE animal_advice
    RENAME COLUMN communication_with_grown_dog_advice to best_dog_handlers;

-- changeset Vladimir:4

UPDATE animal_advice SET best_dog_handlers = 'Контакты проверенных кинологов: 1. Иван Петров - petrov.kinolog@gmail.com, +7-999-123-45-67
2. Елена Иванова - ivanova.kinolog@mail.ru, +7-977-890-12-34
3. Алексей Смирнов - smirnov.kinolog@gmail.com, +7-925-456-78-90
4. Ольга Кузнецова - kuznetsova.kinolog@mail.ru, +7-903-654-32-10
5. Дмитрий Морозов - morozov.kinolog@gmail.com, +7-985-234-56-78' WHERE id = 1;

-- changeset Anastasiya:1

CREATE TABLE message_to_volunteer
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    sender    VARCHAR(255),
    text      VARCHAR(255),
    local_date_time TIMESTAMP WITHOUT TIME ZONE,
    status_message  VARCHAR(255),
    CONSTRAINT pk_message_to_volunteer PRIMARY KEY (id)
);

CREATE TABLE trial_period
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    owner_name VARCHAR(255),
    animal_name VARCHAR(255),
    start_date date,
    end_date   date,
    result    VARCHAR(255),
    CONSTRAINT pk_trial_period PRIMARY KEY (id)
);

create table trial_period_report
(
    trial_period_id bigint not null
        constraint fkipftsx186s1e9e1lk5onryuh7
            references trial_period,
    report_id  bigint not null
        constraint uk_k01alvu7bnbjdjysbclq6qjnb
            unique
        constraint fkrvxxs01ttp0ucho7yp8addx4m
            references reports,
    constraint trial_period_report_pkey
        primary key (trial_period_id, report_id)
);

create table cat_owner_trial_period
(
    cat_owner_id bigint not null
        constraint fkipftsx146s1e9e1lk5onryuh7
            references cat_owner,
    trial_period_id  bigint not null
        constraint uk_k01alvu8bnbjdjysbclq6qjnb
            unique
        constraint fkrvxxs08ttp0ucho7yp8addx4m
            references trial_period,
    constraint cat_owner_trial_period_pkey
        primary key (cat_owner_id, trial_period_id)
);

CREATE TABLE cat_owner
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    phone_number VARCHAR(255),
    CONSTRAINT pk_cat_owner PRIMARY KEY (id)
);

create table cat_owner_cat
(
    cat_owner_id bigint not null
        constraint fkipftsx386s1e9e1lk5onryuh5
            references cat_owner,
    cat_id  bigint not null
        constraint uk_k06alvu5bnbjdjysbclq6qjnb
            unique
        constraint fkrvxxs01ttp0ucho9yp8addx4m
            references animals,
    constraint cat_owner_cat_pkey
        primary key (cat_owner_id, cat_id)
);


create table cat_owner_completed_trial_period
(
    cat_owner_id bigint not null
        constraint fkipftsx136s1e9e1lk5onryuh7
            references cat_owner,
    trial_period_id  bigint not null
        constraint uk_k03alvu8bnbjdjysbclq6qjnb
            unique
        constraint fkrvxxs02ttp0ucho7yp8addx4m
            references trial_period,
    constraint cat_owner_completed_trial_period_pkey
        primary key (cat_owner_id, trial_period_id)
);

CREATE TABLE dog_owner
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    phone_number VARCHAR(255),
    CONSTRAINT pk_dog_owner PRIMARY KEY (id)
);

create table dog_owner_dog
(
    dog_owner_id bigint not null
        constraint fkipftsx386s1e9e1lk5onryuh5
            references dog_owner,
    dog_id  bigint not null
        constraint uk_k09alvu5bnbjdjysbclq6qjnb
            unique
        constraint fkrvxxs01ttp0ucho9yp8addx4m
            references animals,
    constraint dogOwner_dog_pkey
        primary key (dog_owner_id, dog_id)
);

create table dog_owner_trial_period
(
    dog_owner_id bigint not null
        constraint fkipftsx146s1e9e1lk5onryuh7
            references dog_owner,
    trial_period_id  bigint not null
        constraint uk_k01alvu8bnbjdjysbclq1qjnb
            unique
        constraint fkrvxxs08ttp0ucho7yp8addx4m
            references trial_period,
    constraint dog_owner_trial_period_pkey
        primary key (dog_owner_id, trial_period_id)
);

create table dog_owner_completed_trial_period
(
    dog_owner_id bigint not null
        constraint fkipftsx135s1e9e1lk5onryuh7
            references dog_owner,
    trial_period_id  bigint not null
        constraint uk_k03alvu8bnbjdjysbclq2qjnb
            unique
        constraint fkrvxxs07ttp0ucho7yp8addx4m
            references trial_period,
    constraint dog_owner_completed_trial_period_pkey
        primary key (dog_owner_id, trial_period_id)
);

