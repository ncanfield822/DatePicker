-- -----------------------------------------------------
-- Table DATE_IDEAS
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS DATE_IDEAS (
  DATE_ID INT AUTO_INCREMENT PRIMARY KEY,
  DATE_NAME VARCHAR(45) NOT NULL,
  DATE_DESCRIPTION VARCHAR(255) NOT NULL,
  DURATION INT NOT NULL,
  COST DOUBLE NOT NULL);