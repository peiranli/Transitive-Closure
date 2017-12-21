CREATE TABLE customer
(
  name  TEXT NOT NULL,
  credit  INTEGER,
  PRIMARY KEY   (name)
);

CREATE TABLE account
(
  no  TEXT NOT NULL,
  balance   REAL,
  PRIMARY KEY   (no)
);

CREATE TABLE depositor
(
  cname  TEXT NOT NULL,
  ano  TEXT NOT NULL,
  FOREIGN KEY   (cname) REFERENCES customer,
  FOREIGN KEY   (ano) REFERENCES account,
  PRIMARY KEY   (cname, ano)
);

CREATE TABLE transfer
(
  src  TEXT,
  tgt  TEXT,
  timestamp  timestamptz,
  amount  REAL,
  FOREIGN KEY   (src) REFERENCES account,
  FOREIGN KEY   (tgt) REFERENCES account
);
