DROP TABLE IF exists `rooms` CASCADE;

CREATE TABLE `rooms` (
	`id` BIGINT GENERATED BY DEFAULT AS IDENTITY,
	`name` VARCHAR(300) NOT NULL,
	`date` DATE NOT NULL,
	`start_hour` TIME NOT NULL,
	`end_hour` TIME NOT NULL,
	PRIMARY KEY (`id`)
)