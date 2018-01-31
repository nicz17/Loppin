-- create Association table

-- to open mysql client:
-- mysql -u loppin -p loppin

CREATE TABLE IF NOT EXISTS Association (
  idxAssociation INT(11) NOT NULL AUTO_INCREMENT,
  asPlant1 INT(11) NOT NULL,
  asPlant2 INT(11) NOT NULL,
  asKind VARCHAR(10) NOT NULL,
  asDescription VARCHAR(512) DEFAULT NULL,
  PRIMARY KEY (idxAssociation),
  UNIQUE(asPlant1, asPlant2),
  FOREIGN KEY (asPlant1) REFERENCES Plant(idxPlant),
  FOREIGN KEY (asPlant2) REFERENCES Plant(idxPlant)
);

