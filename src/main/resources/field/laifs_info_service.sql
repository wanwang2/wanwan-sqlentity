  id              VARCHAR2(20) not null,
  flowid          VARCHAR2(20) not null,
  createtime      DATE,
  userid          VARCHAR2(20) not null,
  role            VARCHAR2(30),
  service         VARCHAR2(2),
  customer        VARCHAR2(50),
  businesstype    VARCHAR2(10),
  producttype     VARCHAR2(10),
  servicedeadline VARCHAR2(10),
  servicefee      NUMBER(20),
  serviceapproval VARCHAR2(1),
  others          VARCHAR2(200),
  projectid       VARCHAR2(20)