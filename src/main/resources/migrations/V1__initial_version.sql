CREATE DATABASE  IF NOT EXISTS `domino_db` ;

USE `domino_db`;

--
-- Table structure for table `domino`
--

DROP TABLE IF EXISTS `domino`;
CREATE TABLE `domino` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `side_one` int(5) DEFAULT NULL,
  `side_two` int(5) DEFAULT NULL,
  `is_double` BOOLEAN DEFAULT FALSE ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `set_domino`
--

DROP TABLE IF EXISTS `set_domino`;
CREATE TABLE `set_domino` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `set_number` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `cell_of_set_domino`
--

DROP TABLE IF EXISTS `cell_of_set_domino`;
CREATE TABLE `cell_of_set_domino` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `set_domino_id` int(10) unsigned NOT NULL,
  `domino_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cell_of_set_domino` FOREIGN KEY (`set_domino_id`) REFERENCES `set_domino` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cell_of_set_domino_domino` FOREIGN KEY (`domino_id`) REFERENCES `domino` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `chain`
--

DROP TABLE IF EXISTS `chain`;
CREATE TABLE `chain` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `chain_number` VARCHAR(100) DEFAULT NULL,
  `set_number` VARCHAR(100) DEFAULT NULL,
  `element_amount` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `chain_link`
--

DROP TABLE IF EXISTS `chain_link`;
CREATE TABLE `chain_link` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `chain_id` int(10) unsigned  DEFAULT NULL,
  `domino_id` int(10) unsigned DEFAULT NULL,
  `parent_id` int(10) unsigned DEFAULT NULL,
  `link_number` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_chain_id_chain` FOREIGN KEY (`chain_id`) REFERENCES `chain` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_chain_id_domino` FOREIGN KEY (`domino_id`) REFERENCES `domino` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Insert default data
--

INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (0,0,TRUE );
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (0,1);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (0,2);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (0,3);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (0,4);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (0,5);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (0,6);
INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (1,1,TRUE );
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (1,2);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (1,3);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (1,4);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (1,5);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (1,6);
INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (2,2,TRUE );
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (2,3);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (2,4);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (2,5);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (2,6);
INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (3,3,TRUE );
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (3,4);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (3,5);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (3,6);
INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (4,4,TRUE );
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (4,5);
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (4,6);
INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (5,5,TRUE );
INSERT INTO `domino` (`side_one`,`side_two`) VALUES (5,6);
INSERT INTO `domino` (`side_one`,`side_two`,`is_double`) VALUES (6,6,TRUE );

INSERT INTO `set_domino` (set_number) VALUES (1);
INSERT INTO `set_domino` (set_number) VALUES (2);

INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (11000,1,2);
INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (21000,2,4);
INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (21001,2,4);
INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (21002,2,4);
INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (21003,2,4);
INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (21004,2,4);
INSERT INTO `chain` (chain_number, set_number, element_amount) VALUES (21005,2,4);

INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (1,16);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (1,19);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (1,17);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (1,3);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (1,10);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (2,12);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (2,17);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (2,22);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (2,24);
INSERT INTO `cell_of_set_domino` (set_domino_id, domino_id) VALUES (2,26);

INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (1,19,0,11000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (1,10,11000100,11000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (2,26,0,21000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (2,12,21000100,21000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (2,17,21000100,21000102);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (2,24,21000100,21000103);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (3,26,0,21000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (3,17,21000100,21000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (3,12,21000100,21000102);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (3,24,21000100,21000103);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (4,26,0,21000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (4,24,21000100,21000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (4,12,21000100,21000102);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (4,17,21000100,21000103);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (5,26,0,21000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (5,12,21000100,21000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (5,24,21000100,21000102);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (5,17,21000100,21000103);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (6,26,0,21000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (6,17,21000100,21000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (6,24,21000100,21000102);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (6,12,21000100,21000103);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (7,26,0,21000100);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (7,24,21000100,21000101);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (7,17,21000100,21000102);
INSERT INTO `chain_link` (chain_id, domino_id, parent_id, link_number) VALUES (7,12,21000100,21000103);

