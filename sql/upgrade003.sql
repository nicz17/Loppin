-- create table Garden

-- to open mysql client:
-- mysql -u loppin -p loppin

CREATE TABLE IF NOT EXISTS Garden (
  idxGarden INT(11) NOT NULL AUTO_INCREMENT,
  gaName VARCHAR(64) NOT NULL,
  gaDescription VARCHAR(512) DEFAULT NULL,
  gaPhoto VARCHAR(512) DEFAULT NULL,
  gaSizeX INT(11) NOT NULL,
  gaSizeY INT(11) NOT NULL,
  gaSizeTile INT(11) NOT NULL,
  PRIMARY KEY (idxGarden),
  UNIQUE(gaName)
);


