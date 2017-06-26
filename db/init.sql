/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/6/20 21:24:47                           */
/*==============================================================*/


drop table if exists ENTERPRISE_MODIFY_HISTORY;

drop table if exists MERCHANT_INVOICE;

drop table if exists INVOICE;

drop table if exists MERCHANT;

drop table if exists MERCHANT_TYPE;

drop table if exists CUSTOMER;

drop table if exists ENTERPRISE;

drop view if exists V_INVOICE;

/*==============================================================*/
/* Table: ENTERPRISE                                            */
/*==============================================================*/
create table ENTERPRISE
(
   CREDIT_CODE          varchar(20) not null,
   NAME                 varchar(128) not null,
   TYPE                 varchar(128),
   LEGAL_PERSON         varchar(64),
   REG_CAPITAL          decimal(10,2),
   ESTABLISH_DATE       date,
   BIZ_PERIOD_START     date,
   BIZ_PERIOD_END       date,
   REG_AUTHORITY        varchar(128),
   ADDRESS              varchar(256) not null,
   MAIN_BIZ             BLOB,
   PHONE                varchar(16) not null,
   BANK                 varchar(256) not null,
   BANK_ACCT         varchar(32) not null,
   CREATE_TIME          timestamp not null default CURRENT_TIMESTAMP,
   CREATE_BY            varchar(64) not null,
   MODIFY_TIME          timestamp default CURRENT_TIMESTAMP,
   MODIFY_BY            varchar(64),
   primary key (CREDIT_CODE)
);

/*==============================================================*/
/* Table: ENTERPRISE_MODIFY_HISTORY                             */
/*==============================================================*/
create table ENTERPRISE_MODIFY_HISTORY
(
   ID                   int not null AUTO_INCREMENT,
   CREDIT_CODE          varchar(20) not null,
   MODIFY_TIME          timestamp not null,
   MODIFY_BY            varchar(64) not null,
   FIELD                varchar(32) not null,
   OLD_VALUE            varchar(255) not null,
   NEW_VALUE            varchar(255) not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: INVOICE                                               */
/*==============================================================*/
create table INVOICE
(
   ID                   int not null AUTO_INCREMENT,
   OPEN_ID              varchar(64) not null,
   TYPE                 smallint not null,
   USER_NAME            varchar(32),
   CREDIT_CODE          varchar(32),
   CREATE_TIME          timestamp not null,
   MODIFY_TIME          timestamp default CURRENT_TIMESTAMP,
   IS_DEFAULT           smallint not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: MERCHANT                                              */
/*==============================================================*/
create table MERCHANT
(
   ID                   int not null AUTO_INCREMENT,
   MER_ID               int,
   NAME                 varchar(255) not null,
   TYPE                 smallint not null,
   ADDRESS              varchar(255) not null,
   EMAIL                varchar(64),
   PHONE1               varchar(32),
   PHONE2               varchar(32),
   LONGITUDE            decimal(10,3) not null,
   LETITUDE             decimal(10,3) not null,
   ZIP_CODE             varchar(10),
   STATUS               smallint not null,
   CREATE_TIME          timestamp not null,
   CREATE_BY            varchar(32) not null,
   MODIFY_TIME          timestamp default CURRENT_TIMESTAMP,
   MODIFY_BY            varchar(32),
   EXPIRE_TIME          timestamp default '2036-12-31 23:59:59',
   primary key (ID)
);

/*==============================================================*/
/* Table: MERCHANT_INVOICE                                      */
/*==============================================================*/
create table MERCHANT_INVOICE
(
   ID                   int not null AUTO_INCREMENT,
   INV_ID               int,
   MER_ID               int,
   MERCHANT_ID          int not null,
   INVOICE_ID           int not null,
   AMOUNT               decimal(10,2),
   CREATE_TIME          timestamp not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: MERCHANT_TYPE                                         */
/*==============================================================*/
create table MERCHANT_TYPE
(
   ID                   int not null,
   NAME                 varchar(255) not null,
   REMARK               varchar(1024),
   primary key (ID)
);

/*==============================================================*/
/* Table: CUSTOMER                                                  */
/*==============================================================*/
create table CUSTOMER
(
   OPEN_ID              varchar(64) not null,
   NICK_NAME            varchar(64) not null,
   GENDER               smallint,
   CITY                 varchar(64),
   PROVINCE             varchar(128),
   COUNTRY              varchar(128),
   CREATE_TIME          timestamp not null default CURRENT_TIMESTAMP,
   LAST_LOGIN_TIME      timestamp not null default CURRENT_TIMESTAMP,
   primary key (OPEN_ID)
);

CREATE VIEW V_INVOICE AS SELECT I.ID, I.OPEN_ID, I.TYPE, I.USER_NAME, I.CREDIT_CODE, I.CREATE_TIME, I.MODIFY_TIME, I.IS_DEFAULT, E.NAME AS CORP_NAME, E.ADDRESS, E.PHONE, E.BANK, E.BANK_ACCT FROM INVOICE I LEFT JOIN ENTERPRISE E ON I.CREDIT_CODE = E.CREDIT_CODE;

alter table ENTERPRISE_MODIFY_HISTORY add constraint FK_ENTERPRISE_HISTORY foreign key (CREDIT_CODE)
      references ENTERPRISE (CREDIT_CODE) on delete restrict on update restrict;

alter table INVOICE add constraint FK_INVOICE_ENTERPRISE foreign key (CREDIT_CODE)
      references ENTERPRISE (CREDIT_CODE) on delete restrict on update restrict;

alter table INVOICE add constraint FK_CUSTOMER_INVOICE foreign key (OPEN_ID)
      references CUSTOMER (OPEN_ID) on delete restrict on update restrict;

alter table MERCHANT add constraint FK_MERCHANT_TYPE foreign key (TYPE)
      references MERCHANT_TYPE (ID) on delete restrict on update restrict;

alter table MERCHANT_INVOICE add constraint FK_INVOICE_MERCHAT foreign key (INVOICE_ID)
      references INVOICE (ID) on delete restrict on update restrict;

alter table MERCHANT_INVOICE add constraint FK_MERCHAT_INVOICE foreign key (MERCHANT_ID)
      references MERCHANT (ID) on delete restrict on update restrict;

