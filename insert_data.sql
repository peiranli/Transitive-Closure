INSERT INTO customer VALUES('Andy',10);
INSERT INTO customer VALUES('Brian',100);
INSERT INTO customer VALUES('Charles',30);
INSERT INTO customer VALUES('Dyrus', 25);
INSERT INTO customer VALUES('Ella',35);
INSERT INTO customer VALUES('Fiora',60);
INSERT INTO customer VALUES('Graves',70);

INSERT INTO account VALUES('A1',1000);
INSERT INTO account VALUES('A2',5000);
INSERT INTO account VALUES('A3',2000);
INSERT INTO account VALUES('A4',4000);
INSERT INTO account VALUES('A5',1000);
INSERT INTO account VALUES('A6',3000);
INSERT INTO account VALUES('A7',2000);

INSERT INTO depositor VALUES('Andy', 'A1');
INSERT INTO depositor VALUES('Brian', 'A2');
INSERT INTO depositor VALUES('Charles', 'A3');
INSERT INTO depositor VALUES('Dyrus', 'A4');
INSERT INTO depositor VALUES('Ella', 'A5');
INSERT INTO depositor VALUES('Fiora', 'A6');
INSERT INTO depositor VALUES('Graves', 'A7');

INSERT INTO transfer VALUES('A1', 'A3', '2013-02-05',200);
INSERT INTO transfer VALUES('A2', 'A1', '2013-02-05',1000);
INSERT INTO transfer VALUES('A3', 'A6', '2013-02-05',5000);
INSERT INTO transfer VALUES('A2', 'A6', '2013-02-05',400);
INSERT INTO transfer VALUES('A4', 'A2', '2013-02-05',500);
INSERT INTO transfer VALUES('A6', 'A4', '2013-02-05',1000);
INSERT INTO transfer VALUES('A5', 'A7', '2013-02-05',1200);
