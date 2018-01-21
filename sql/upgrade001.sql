-- create first tables: Soil, Plant

-- to open mysql client:
-- mysql -u root -p loppin

CREATE TABLE IF NOT EXISTS Soil (
  idxSoil INT(11) NOT NULL AUTO_INCREMENT,
  soName VARCHAR(64) NOT NULL,
  soDescription VARCHAR(512) DEFAULT NULL,
  soColor VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (idxSoil),
  UNIQUE(soName)
);

CREATE TABLE IF NOT EXISTS Plant (
  idxPlant INT(11) NOT NULL AUTO_INCREMENT,
  plName VARCHAR(64) NOT NULL,
  plNameLatin VARCHAR(64) NOT NULL,
  plFamily VARCHAR(64) NOT NULL,
  plDescription VARCHAR(512) DEFAULT NULL,
  plKind VARCHAR(10) NOT NULL,
  plSowing SMALLINT(5) DEFAULT 0,
  plPlanting SMALLINT(5) DEFAULT 0,
  plHarvest1 SMALLINT(5) DEFAULT 0,
  plHarvest2 SMALLINT(5) DEFAULT 0,
  plSoil INT(11) NOT NULL,
  plPhoto VARCHAR(512) DEFAULT NULL,
  PRIMARY KEY (idxPlant),
  UNIQUE(plName),
  UNIQUE(plNameLatin),
  FOREIGN KEY (plSoil) REFERENCES Soil(idxSoil)
);


