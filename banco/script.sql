SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `PokerBASE` ;
CREATE SCHEMA IF NOT EXISTS `PokerBASE` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `PokerBASE` ;

-- -----------------------------------------------------
-- Table `PokerBASE`.`T_Usuario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_Usuario` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_Usuario` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(120) NOT NULL ,
  `username` VARCHAR(45) NOT NULL ,
  `senha` VARCHAR(14) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_Sala`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_Sala` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_Sala` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `Nome` VARCHAR(45) NOT NULL ,
  `website` VARCHAR(100) NULL ,
  `descricao` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_Sessao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_Sessao` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_Sessao` (
  `codigo` INT NOT NULL ,
  `data` DATE NOT NULL ,
  `inicio` TIME NOT NULL ,
  `final` TIME NULL ,
  `T_Usuario_id` INT NOT NULL ,
  PRIMARY KEY (`codigo`, `T_Usuario_id`) ,
  CONSTRAINT `fk_T_Sessao_T_Usuario`
    FOREIGN KEY (`T_Usuario_id` )
    REFERENCES `PokerBASE`.`T_Usuario` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_Sessao_T_Usuario_idx` ON `PokerBASE`.`T_Sessao` (`T_Usuario_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_CashGame`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_CashGame` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_CashGame` (
  `T_Sessao_codigo` INT NOT NULL ,
  `id` INT NOT NULL AUTO_INCREMENT ,
  `limite` VARCHAR(10) NULL ,
  `blind` VARCHAR(45) NULL ,
  `numero_de_maos` INT(11) NULL ,
  `duracao` TIME NULL ,
  `qtd_jogadores_mesa` INT(11) NULL ,
  `T_Sala_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Sessao_codigo`, `T_Sala_id`) ,
  CONSTRAINT `fk_T_CashGame_T_Sessao1`
    FOREIGN KEY (`T_Sessao_codigo` )
    REFERENCES `PokerBASE`.`T_Sessao` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_CashGame_T_Sala1`
    FOREIGN KEY (`T_Sala_id` )
    REFERENCES `PokerBASE`.`T_Sala` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_CashGame_T_Sala1_idx` ON `PokerBASE`.`T_CashGame` (`T_Sala_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_SitAndGo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_SitAndGo` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_SitAndGo` (
  `T_Sessao_codigo` INT NOT NULL ,
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `buy_in` INT(11) NOT NULL ,
  `qtd_jogadores_mesa` INT(11) NULL ,
  `qtd_jogadores` INT(11) NOT NULL ,
  `posicao` INT(11) NULL ,
  `duracao` TIME NULL ,
  `valor_ganho` DOUBLE NULL ,
  `rebuy_valor` DOUBLE NULL ,
  `qtd_rebuys` INT(11) NULL ,
  `T_Sala_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Sessao_codigo`, `T_Sala_id`) ,
  CONSTRAINT `fk_T_SitAndGo_T_Sessao1`
    FOREIGN KEY (`T_Sessao_codigo` )
    REFERENCES `PokerBASE`.`T_Sessao` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_SitAndGo_T_Sala1`
    FOREIGN KEY (`T_Sala_id` )
    REFERENCES `PokerBASE`.`T_Sala` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_SitAndGo_T_Sala1_idx` ON `PokerBASE`.`T_SitAndGo` (`T_Sala_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_MultiTableTournaments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_MultiTableTournaments` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_MultiTableTournaments` (
  `T_Sessao_codigo` INT NOT NULL ,
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `buy_in` DOUBLE NOT NULL ,
  `qtd_jogadores_mesa` INT(11) NULL ,
  `qtd_jogadores` INT(11) NULL ,
  `posicao` INT(11) NULL ,
  `valor_ganho` DOUBLE NULL ,
  `duracao` TIME NULL ,
  `valor_rebuy` DOUBLE NULL ,
  `qtd_rebuys` INT(11) NULL ,
  `T_Sala_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Sessao_codigo`, `T_Sala_id`) ,
  CONSTRAINT `fk_T_MultiTableTournaments_T_Sessao1`
    FOREIGN KEY (`T_Sessao_codigo` )
    REFERENCES `PokerBASE`.`T_Sessao` (`codigo` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_MultiTableTournaments_T_Sala1`
    FOREIGN KEY (`T_Sala_id` )
    REFERENCES `PokerBASE`.`T_Sala` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_MultiTableTournaments_T_Sala1_idx` ON `PokerBASE`.`T_MultiTableTournaments` (`T_Sala_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_Bankroll`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_Bankroll` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_Bankroll` (
  `id` INT NOT NULL ,
  `valor_inicial` DOUBLE NOT NULL ,
  `valor_atual` DOUBLE NOT NULL ,
  `T_Sala_id` INT NOT NULL ,
  `T_Usuario_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Sala_id`, `T_Usuario_id`) ,
  CONSTRAINT `fk_T_Bankroll_T_Sala1`
    FOREIGN KEY (`T_Sala_id` )
    REFERENCES `PokerBASE`.`T_Sala` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_T_Bankroll_T_Usuario1`
    FOREIGN KEY (`T_Usuario_id` )
    REFERENCES `PokerBASE`.`T_Usuario` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_Bankroll_T_Sala1_idx` ON `PokerBASE`.`T_Bankroll` (`T_Sala_id` ASC) ;

CREATE INDEX `fk_T_Bankroll_T_Usuario1_idx` ON `PokerBASE`.`T_Bankroll` (`T_Usuario_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_Bonus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_Bonus` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_Bonus` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `valor` DOUBLE NULL ,
  `data` DATE NULL ,
  `T_Bankroll_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Bankroll_id`) ,
  CONSTRAINT `fk_T_Bonus_T_Bankroll1`
    FOREIGN KEY (`T_Bankroll_id` )
    REFERENCES `PokerBASE`.`T_Bankroll` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_Bonus_T_Bankroll1_idx` ON `PokerBASE`.`T_Bonus` (`T_Bankroll_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_Deposito`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_Deposito` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_Deposito` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `valor` DOUBLE NULL ,
  `data` DATE NULL ,
  `T_Bankroll_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Bankroll_id`) ,
  CONSTRAINT `fk_T_Deposito_T_Bankroll1`
    FOREIGN KEY (`T_Bankroll_id` )
    REFERENCES `PokerBASE`.`T_Bankroll` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_Deposito_T_Bankroll1_idx` ON `PokerBASE`.`T_Deposito` (`T_Bankroll_id` ASC) ;


-- -----------------------------------------------------
-- Table `PokerBASE`.`T_SAQUE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PokerBASE`.`T_SAQUE` ;

CREATE  TABLE IF NOT EXISTS `PokerBASE`.`T_SAQUE` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `valor` DOUBLE NULL ,
  `data` DATE NULL ,
  `T_Bankroll_id` INT NOT NULL ,
  PRIMARY KEY (`id`, `T_Bankroll_id`) ,
  CONSTRAINT `fk_T_SAQUE_T_Bankroll1`
    FOREIGN KEY (`T_Bankroll_id` )
    REFERENCES `PokerBASE`.`T_Bankroll` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_T_SAQUE_T_Bankroll1_idx` ON `PokerBASE`.`T_SAQUE` (`T_Bankroll_id` ASC) ;

USE `PokerBASE` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
