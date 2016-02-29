DROP TABLE ID_GEN;
DROP TABLE ORDER_ITEM;
DROP TABLE ORDER_REF;

CREATE TABLE ID_GEN
(
   GEN_KEY varchar(255),
   GEN_VALUE int
);

CREATE TABLE ORDER_REF
(
   ID bigint PRIMARY KEY NOT NULL,
   ADDRESS varchar(255),
   AMOUNT bigint,
   CREATION_DATE date,
   CUSTOMER_NAME varchar(255)
);

CREATE TABLE ORDER_ITEM
(
   ID bigint NOT NULL,
   ORDER_ID bigint NOT NULL,
   NAME varchar(255),
   QUANTITY bigint NOT NULL,
   SERIAL_NUMBER varchar(255),
   CONSTRAINT ORDER_ITEM_PK PRIMARY KEY (ID,ORDER_ID)
);

ALTER TABLE ORDER_ITEM ADD CONSTRAINT ORDER_ITEM_ORDER_FK FOREIGN KEY (ORDER_ID) REFERENCES ORDER_REF(ID);