-- create table Journal

-- to open mysql client:
-- mysql -u loppin -p loppin

CREATE TABLE IF NOT EXISTS Journal (
  idxJournal INT(11) NOT NULL AUTO_INCREMENT,
  joTitle VARCHAR(64) NOT NULL,
  joText  VARCHAR(512) DEFAULT NULL,
  joDate DATETIME NOT NULL,
  joGarden INT(11) DEFAULT NULL,
  PRIMARY KEY (idxJournal),
  FOREIGN KEY (joGarden) REFERENCES Garden(idxGarden)
);


