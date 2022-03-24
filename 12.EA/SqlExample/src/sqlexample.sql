CREATE DATABASE sqlexample;
USE sqlexample;
CREATE TABLE `person` (
	`id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, 
	`name` varchar(100) NOT NULL
);
INSERT INTO `person` (`name`) VALUES
('Tendermich Plumberpatch'),
('Mary K. Nanny'),
('Kim John Gun'),
('Bruce Pain');