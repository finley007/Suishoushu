/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/6/20 21:24:47                           */
/*==============================================================*/


drop table if exists ENTERPRISE_MODIFY_HISTORY;

drop table if exists MERCHANT_INVOICE;

drop table if exists INVOICE;

drop table if exists MERCHANT;

drop table if exists MERCHANT_TYPE;

drop table if exists USER;

drop table if exists ENTERPRISE;

/*==============================================================*/
/* Table: ENTERPRISE                                            */
/*==============================================================*/
create table ENTERPRISE
(
   CREDIT_CODE          varchar(20) not null,
   NAME                 varchar(128) not null,
   TYPE                 varchar(128) not null,
   LEGAL_PERSON         varchar(64) not null,
   REG_CAPITAL          decimal(10,2) not null,
   ESTABLISH_DATE       date not null,
   BIZ_PERIOD_START     date not null,
   BIZ_PERIOD_END       date,
   REG_AUTHORITY        varchar(128) not null,
   ADDRESS              varchar(256) not null,
   MAIN_BIZ             BLOB,
   PHONE                varchar(16) not null,
   BANK                 varchar(256) not null,
   BANK_ACCOUNT         varchar(32) not null,
   CREATE_TIME          datetime not null,
   CREATE_BY            varchar(64) not null,
   MODIFY_TIME          datetime,
   MODIFY_BY            varchar(64),
   primary key (CREDIT_CODE)
);

/*==============================================================*/
/* Table: ENTERPRISE_MODIFY_HISTORY                             */
/*==============================================================*/
create table ENTERPRISE_MODIFY_HISTORY
(
   ID                   int not null,
   ENT_CREDIT_CODE      varchar(20),
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
   ID                   int not null,
   ENT_CREDIT_CODE      varchar(20),
   OPEN_ID              varchar(64) not null,
   TYPE                 smallint not null,
   USER_NAME            varchar(32),
   CREDIT_CODE          varchar(32),
   CREATE_TIME          timestamp not null,
   MODIFY_TIME          timestamp,
   IS_DEFAULT           smallint not null,
   primary key (ID)
);

/*==============================================================*/
/* Table: MERCHANT                                              */
/*==============================================================*/
create table MERCHANT
(
   ID                   int not null,
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
   MODIFY_TIME          timestamp,
   MODIFY_BY            varchar(32),
   EXPIRE_TIME          timestamp,
   primary key (ID)
);

/*==============================================================*/
/* Table: MERCHANT_INVOICE                                      */
/*==============================================================*/
create table MERCHANT_INVOICE
(
   ID                   int not null,
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
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   OPEN_ID              varchar(64) not null,
   NICK_NAME            varchar(64) not null,
   GENDER               smallint,
   CITY                 varchar(64),
   PROVINCE             varchar(128),
   COUNTRY              varchar(128),
   CREATE_TIME          timestamp not null,
   LAST_LOGIN_TIME      datetime not null,
   primary key (OPEN_ID)
);

alter table ENTERPRISE_MODIFY_HISTORY add constraint FK_ENTERPRISE_HISTORY foreign key (ENT_CREDIT_CODE)
      references ENTERPRISE (CREDIT_CODE) on delete restrict on update restrict;

alter table INVOICE add constraint FK_Relationship_5 foreign key (ENT_CREDIT_CODE)
      references ENTERPRISE (CREDIT_CODE) on delete restrict on update restrict;

alter table INVOICE add constraint FK_USER_INVOICE foreign key (OPEN_ID)
      references USER (OPEN_ID) on delete restrict on update restrict;

alter table MERCHANT add constraint FK_MERCHANT_TYPE foreign key (TYPE)
      references MERCHANT_TYPE (ID) on delete restrict on update restrict;

alter table MERCHANT_INVOICE add constraint FK_INVOICE_MERCHAT foreign key (INVOICE_ID)
      references INVOICE (ID) on delete restrict on update restrict;

alter table MERCHANT_INVOICE add constraint FK_MERCHAT_INVOICE foreign key (MERCHANT_ID)
      references MERCHANT (ID) on delete restrict on update restrict;

