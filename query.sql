DROP TABLE IF EXISTS funds
CREATE TABLE funds AS
SELECT DISTINCT csrc.name AS from, ctgt.name AS to
FROM transfer, depositor dsrc, depositor dtgt, customer csrc, customer ctgt
WHERE transfer.src = dsrc.ano
AND transfer.tgt = dtgt.ano
AND dsrc.cname = csrc.name
AND dtgt.cname = ctgt.name

DROP TABLE IF EXISTS influence
CREATE TABLE influence
(
  "from"    TEXT,
  "to"    TEXT,
  FOREIGN KEY   ("from") REFERENCES customer,
  FOREIGN KEY   ("to") REFERENCES customer
)

DROP TABLE IF EXISTS delta
CREATE TABLE delta
(
  "from"    TEXT,
  "to"    TEXT,
  FOREIGN KEY   ("from") REFERENCES customer,
  FOREIGN KEY   ("to") REFERENCES customer
)

DROP TABLE IF EXISTS old_influence
CREATE TABLE old_influence
(
  "from"    TEXT,
  "to"    TEXT,
  FOREIGN KEY   ("from") REFERENCES customer,
  FOREIGN KEY   ("to") REFERENCES customer
)

DELETE FROM influence
INSERT INTO influence
SELECT DISTINCT * FROM funds

DELETE FROM delta
INSERT INTO delta
SELECT DISTINCT * FROM funds

SELECT COUNT(*) FROM delta

DELETE FROM old_influence
INSERT INTO old_influence
SELECT DISTINCT * FROM influence

DELETE FROM influence
INSERT INTO influence
SELECT DISTINCT * FROM old_influence
UNION
(
  SELECT DISTINCT x.from, y.to
  FROM delta x, old_influence y
  WHERE x.to = y.from
)
UNION
(
  SELECT DISTINCT x.from, y.to
  FROM old_influence x, delta y
  WHERE x.to = y.from
)

DELETE FROM delta
INSERT INTO delta
(SELECT DISTINCT * FROM influence)
EXCEPT
(SELECT DISTINCT * FROM old_influence)
