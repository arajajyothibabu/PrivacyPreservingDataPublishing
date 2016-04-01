CREATE TABLE rawdata (
  id NUMBER(5) NOT NULL PRIMARY KEY,
  sex VARCHAR(6),
  age NUMBER(5),
  code VARCHAR(100),
  class VARCHAR(1)
);

INSERT into rawdata VALUES('1', 'Female', '33', '15,16,31,32', 'N');
INSERT into rawdata VALUES('2', 'Female', '60', '15,31', 'Y');
INSERT into rawdata VALUES('3', 'Female', '37', '16', 'Y');
INSERT into rawdata VALUES('4', 'Female', '35', '15,16', 'N');
INSERT into rawdata VALUES('5', 'Male', '16', '15', 'N');
INSERT into rawdata VALUES('6', 'Male', '36', '16,31', 'Y');
INSERT into rawdata VALUES('7', 'Female', '46', '15,16,31,32', 'N');
INSERT into rawdata VALUES('8', 'Male', '27', '16,31,32', 'Y');

CREATE TABLE anonymous (
  age VARCHAR(10) NOT NULL,
  sex VARCHAR(6),
  code VARCHAR(100),
  class VARCHAR(1),
  count NUMBER(10),
  CONSTRAINT id PRIMARY KEY(age, sex, code, class)
);