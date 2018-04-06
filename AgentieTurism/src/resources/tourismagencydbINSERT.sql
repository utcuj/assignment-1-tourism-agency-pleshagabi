-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema tourismagencydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema tourismagencydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `tourismagencydb` DEFAULT CHARACTER SET utf8 ;
-- ---------------------------------------clientclient--------------
-- Schema tourismagencydb
-- -----------------------------------------------------
USE `tourismagencydb` ;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`administrator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`administrator` (
  `idAdministrator` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idAdministrator`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`client` (
  `idclient` INT(11) NOT NULL,
  `firstName` VARCHAR(45) NULL DEFAULT NULL,
  `lastName` VARCHAR(45) NULL DEFAULT NULL,
  `cnp` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idclient`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`reservation` (
  `idreservation` INT(11) NOT NULL,
  `destination` VARCHAR(45) NULL DEFAULT NULL,
  `hotel` VARCHAR(45) NULL DEFAULT NULL,
  `finalPaymentDate` DATE NULL DEFAULT NULL,
  `fullPrice` INT(11) NULL DEFAULT NULL,
  `partialPrice` INT(11) NULL DEFAULT NULL,
  `numberofpersons` INT(11) NULL DEFAULT NULL,
  `client_idclient` INT(11) NOT NULL,
  PRIMARY KEY (`idreservation`),
  INDEX `fk_reservation_client1_idx` (`client_idclient` ASC),
  CONSTRAINT `fk_reservation_client1`
    FOREIGN KEY (`client_idclient`)
    REFERENCES `tourismagencydb`.`client` (`idclient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`payment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`payment` (
  `idPayment` INT(11) NOT NULL,
  `Date` DATE NULL DEFAULT NULL,
  `reservation_idreservation` INT(11) NOT NULL,
  `payment` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idPayment`),
  INDEX `fk_Payment_reservation1_idx` (`reservation_idreservation` ASC),
  CONSTRAINT `fk_Payment_reservation1`
    FOREIGN KEY (`reservation_idreservation`)
    REFERENCES `tourismagencydb`.`reservation` (`idreservation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`reservationperson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`reservationperson` (
  `idResevationPerson` INT(11) NOT NULL,
  `reservation_idreservation` INT(11) NOT NULL,
  PRIMARY KEY (`idResevationPerson`),
  INDEX `fk_ResevationPerson_reservation1_idx` (`reservation_idreservation` ASC),
  CONSTRAINT `fk_ResevationPerson_reservation1`
    FOREIGN KEY (`reservation_idreservation`)
    REFERENCES `tourismagencydb`.`reservation` (`idreservation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`person` (
  `idperson` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  `cnp` VARCHAR(45) NULL DEFAULT NULL,
  `sex` ENUM('Male', 'Female') NULL DEFAULT NULL,
  `reservationperson_idResevationPerson` INT(11) NOT NULL,
  PRIMARY KEY (`idperson`),
  INDEX `fk_person_reservationperson1_idx` (`reservationperson_idResevationPerson` ASC),
  CONSTRAINT `fk_person_reservationperson1`
    FOREIGN KEY (`reservationperson_idResevationPerson`)
    REFERENCES `tourismagencydb`.`reservationperson` (`idResevationPerson`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`user` (
  `iduser` INT(11) NOT NULL,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`iduser`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tourismagencydb`.`useractivity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `tourismagencydb`.`useractivity` (
  `iduserActivity` INT(11) NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `info` VARCHAR(255) NULL DEFAULT NULL,
  `user_iduser` INT(11) NOT NULL,
  PRIMARY KEY (`iduserActivity`),
  INDEX `fk_userActivity_user_idx` (`user_iduser` ASC),
  CONSTRAINT `fk_userActivity_user`
    FOREIGN KEY (`user_iduser`)
    REFERENCES `tourismagencydb`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `tourismagencydb`.`client` 
CHANGE COLUMN `missedDeadline` `missedDeadline` TINYINT(1) NOT NULL DEFAULT 0 ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO `tourismagencydb`.`administrator` (`idAdministrator`, `name`, `username`, `password`, `age`) VALUES ('1', 'Plesa Gabriel', 'plesha', 'plesha', '22');
INSERT INTO `tourismagencydb`.`administrator` (`idAdministrator`, `name`, `username`, `password`, `age`) VALUES ('2', 'Pocol Alexandru', 'pocol', 'alexandru', '21');


INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('1', 'Maria', 'Ioana', '196011025786', 'cosasilor 53', '23');
INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('2', 'Anca', 'Iacbon', '195011025786', 'andrei muresanu 53', '22');
INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('3', 'Plesa', 'Mihai', '194011025786', 'urusagului 31', '27');
INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('4', 'Becali', 'Gigi', '155411025786', 'pipera 1', '55');
INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('5', 'Raicu', 'Andreea', '189110257861', 'baritiu 15', '35');
INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('6', 'Papa', 'De la roma', '146011025786', 'vatican 0', '100');
INSERT INTO `tourismagencydb`.`client` (`idclient`, `firstName`, `lastName`, `cnp`, `address`, `age`) VALUES ('7', 'Bompa', 'Andreea', '198011025786', 'louis pasteor 10', '21');

INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('1', 'Hawai', 'Hotel Hawai', '2018-05-25', '6900', '0', '2', '1');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('2', 'Saint-Tropez', 'Hotel Saint', '2018-03-03', '5000', '500', '3', '2');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('3', 'Paris', 'Hotel Hawai', '2018-07-11', '4300', '100', '4', '3');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('4', 'Londra', 'Grand Hotel Italia', '2018-08-20', '8000', '300', '2', '4');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('5', 'Havana', 'Hotel Hawai', '2018-05-15', '5600', '0', '3', '5');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('6', 'Beijing', 'Hotel Hawai', '2018-04-19', '8555', '0', '2', '6');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('7', 'Bucuresti', 'Hotel Hawai', '2018-06-08', '2500', '1000', '4', '7');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('8', 'Cluj-Napoca', 'Grand Hotel Italia', '2018-07-22', '2000', '50', '3', '1');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('9', 'Ploiesti', 'Grand Hotel Italia', '2018-08-01', '1550', '0', '5', '3');
INSERT INTO `tourismagencydb`.`reservation` (`idreservation`, `destination`, `hotel`, `finalPaymentDate`, `fullPrice`, `partialPrice`, `numberofpersons`, `client_idclient`) VALUES ('10', 'Sibiu', 'Grand Hotel Italia', '2018-11-23', '1890', '0', '5', '2');


INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('1', '1');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('2', '2');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('3', '3');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('4', '4');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('5', '5');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('6', '6');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('7', '7');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('8', '1');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('9', '3');
INSERT INTO `tourismagencydb`.`reservationperson` (`idResevationPerson`, `reservation_idreservation`) VALUES ('10', '2');


INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('1', 'Paul Vangogh', '22', '1940110125786', 'Male', '1');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('2', 'Antonia Madalina', '21', '1970110125786', 'Female', '1');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('3', 'Paris Hilton', '31', '1960110125786', 'Female', '2');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('4', 'Vivian pamela', '25', '1860110125786', 'Female', '2');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('5', 'Mos Craciun', '99', '1360110125786', 'Male', '2');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('6', 'Voicu Vasile-Vasile-Vasile', '55', '1560110125786', 'Male', '3');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('7', 'Maica Tereza', '69', '1660110125786', 'Female', '3');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('8', 'Daniela Crudu', '30', '1860110125786', 'Female', '4');
INSERT INTO `tourismagencydb`.`person` (`idperson`, `name`, `age`, `cnp`, `sex`, `reservationperson_idResevationPerson`) VALUES ('9', 'Pavel Stratan', '41', '1760110125786', 'Male', '4');


INSERT INTO `tourismagencydb`.`payment` (`idPayment`, `Date`, `reservation_idreservation`, `payment`) VALUES ('1', '2018-04-01', '1', '100');
INSERT INTO `tourismagencydb`.`payment` (`idPayment`, `Date`, `reservation_idreservation`, `payment`) VALUES ('2', '2018-03-05', '2', '200');
INSERT INTO `tourismagencydb`.`payment` (`idPayment`, `Date`, `reservation_idreservation`, `payment`) VALUES ('3', '2018-02-05', '3', '150');
INSERT INTO `tourismagencydb`.`payment` (`idPayment`, `Date`, `reservation_idreservation`, `payment`) VALUES ('4', '2018-01-05', '4', '1000');
INSERT INTO `tourismagencydb`.`payment` (`idPayment`, `Date`, `reservation_idreservation`, `payment`) VALUES ('5', '2018-04-04', '5', '250');
INSERT INTO `tourismagencydb`.`payment` (`idPayment`, `Date`, `reservation_idreservation`, `payment`) VALUES ('6', '2018-04-04', '1', '350');


INSERT INTO `tourismagencydb`.`user` (`iduser`, `username`, `password`, `name`, `age`) VALUES ('1', 'Antonia', 'Antonia', 'Antonia Madalina', '22');
INSERT INTO `tourismagencydb`.`user` (`iduser`, `username`, `password`, `name`, `age`) VALUES ('2', 'Vasile', 'vosi', 'Vasile Gheorghe-Gheorghiu-Dej', '40');

UPDATE `tourismagencydb`.`reservation` SET `hotel`='Hotel Havana' WHERE `idreservation`='5';
UPDATE `tourismagencydb`.`reservation` SET `hotel`='Hotel Beijing' WHERE `idreservation`='6';
UPDATE `tourismagencydb`.`reservation` SET `hotel`='Hotel Bucuresti' WHERE `idreservation`='7';
UPDATE `tourismagencydb`.`reservation` SET `hotel`='Grand Hotel Ploiesti' WHERE `idreservation`='9';
UPDATE `tourismagencydb`.`reservation` SET `hotel`='Grand Hotel Sibiu' WHERE `idreservation`='10';
UPDATE `tourismagencydb`.`reservation` SET `hotel`='Hotel Paris' WHERE `idreservation`='3';

ALTER TABLE `tourismagencydb`.`client` 
ADD COLUMN `missedDeadline` TINYINT NULL AFTER `age`;

