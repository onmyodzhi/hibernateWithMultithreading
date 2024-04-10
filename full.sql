BEGIN;

DROP TABLE IF EXISTS Users CASCADE;
CREATE TABLE Users

(
    id   bigserial primary key,
    name varchar(20) not null
);
INSERT INTO Users (name)
VALUES ('Alex'),
       ('Pedro'),
       ('Donatello'),
       ('Cris'),
       ('Milton'),
       ('Ivanushka'),
       ('Busy man'),
       ('Shrder');

DROP TABLE IF EXISTS Lots CASCADE;
CREATE TABLE Lots
(
    id            bigserial primary key,
    title         varchar(40) not null,
    current_bet   int         not null CHECK ( current_bet >= 0),
    current_owner bigint
);
INSERT INTO Lots (title, current_bet, current_owner) VALUES
('one',0, null),
('two',0, null),
('three',0, null),
('four',0, null);


COMMIT;