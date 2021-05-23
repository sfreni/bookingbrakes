-- bookingbrakes_dev.customer definition

CREATE TABLE `customer`
(
    `id`             bigint NOT NULL,
    `birth_day`      datetime(6)  DEFAULT NULL,
    `city`           varchar(255) DEFAULT NULL,
    `first_name`     varchar(255) DEFAULT NULL,
    `last_name`      varchar(255) DEFAULT NULL,
    `street_address` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO bookingbrakes_dev.customer
    (id, birth_day, city, first_name, last_name, street_address)
VALUES (1, '1989-12-31 20:10:01', 'Florence', 'Rocco', 'Commisso', 'via delle vie n.12');
