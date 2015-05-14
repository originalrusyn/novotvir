-- liquibase formatted sql logicalFilePath:release1.0.0.sql
-- changeset titov:1 dbms:postgresql runInTransaction:true
CREATE TABLE
  users (
  id                       BIGSERIAL           NOT NULL,
  name                     VARCHAR(255) UNIQUE NOT NULL,
  primaryEmailAddressId    BIGINT,
  token                    VARCHAR(255)        NOT NULL,
  lastSignInIpAddress      VARCHAR(255)        NOT NULL,
  activationToken          VARCHAR(255)        NOT NULL,
  activated                boolean             NOT NULL,
  blocked                  boolean             NOT NULL,
  lastWebSignInTimestamp   timestamp with time zone,
  PRIMARY KEY (id)
);

-- rollback drop table if exists users;

-- changeset titov:2 dbms:postgresql runInTransaction:true
CREATE TABLE
  emailAddresses (
  id                       BIGSERIAL           NOT NULL,
  email                    VARCHAR(255)        NOT NULL,
  userId                   bigint              NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT userId_email UNIQUE (userId, email),
  CONSTRAINT emailAddresses_users FOREIGN KEY (userId) REFERENCES users (id) MATCH SIMPLE
);

-- rollback drop table if exists emailAddresses;

-- changeset titov:3 dbms:postgresql runInTransaction:true
alter table users add CONSTRAINT users_emailAddresses FOREIGN KEY (primaryEmailAddressId) REFERENCES emailAddresses (id) MATCH SIMPLE ON DELETE CASCADE;

-- rollback alter table users drop constraint if exists users_emailAddresses;

-- changeset titov:4 dbms:postgresql runInTransaction:true
create table
  socialUserConnection (
  userId                 varchar(255) not null,
  providerId             varchar(255) not null,
  providerUserId         varchar(255),
  rank                   int          not null,
  displayName            varchar(255),
  profileUrl             varchar(512),
  imageUrl               varchar(512),
  accessToken            varchar(255) not null,
  secret                 varchar(255),
  refreshToken           varchar(255),
  expireTime             bigint,
  primary key (userId, providerId, providerUserId),
  CONSTRAINT UserConnectionRank UNIQUE (userId, providerId, rank),
  CONSTRAINT UserConnection_user FOREIGN KEY (userId) REFERENCES users (name) MATCH SIMPLE
);

-- rollback drop table if exists UserConnection;

-- changeset titov:5 dbms:postgresql runInTransaction:true
CREATE
TABLE
  authorities(
  id BIGSERIAL NOT NULL ,
  userId bigint NOT NULL,
  role VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT userId_role UNIQUE (userId,role),
  CONSTRAINT authorities_user FOREIGN KEY (userId) REFERENCES users (id) MATCH SIMPLE ON DELETE CASCADE
);

-- rollback drop table if exists authorities;

-- changeset titov:6 dbms:postgresql runInTransaction:true
CREATE
TABLE
  userLogs(
  id BIGSERIAL NOT NULL,
  timestamp timestamp with time zone NOT NULL,
  userId BIGINT NOT NULL,
  body json,
  PRIMARY KEY (id)
);

-- rollback drop table if exists authorities;

-- changeset titov:7 dbms:postgresql runInTransaction:true splitStatements:false endDelimiter:#
create function userLogProcedure() returns trigger as $$
begin
insert into userLogs(timestamp, userId, body) select current_timestamp, new.id, row_to_json(u.*) from users u where u.id=new.id;
return new;
end;
$$ LANGUAGE 'plpgsql';

-- rollback drop function if exists userLogProcedure;

-- changeset titov:8 dbms:postgresql runInTransaction:true
create trigger userLogTrigger after insert or update on users for each row execute PROCEDURE userLogProcedure();

-- rollback drop trigger if exists userLogTrigger;

-- changeset titov:9 dbms:postgresql runInTransaction:true
INSERT INTO users
(id, name            , token                                                                           , lastSignInIpAddress, activationToken , activated, blocked, lastWebSignInTimestamp) VALUES
(1 , '${admin_email}', MD5('${admin_email}' || MD5('${pass_salt}'|| '${admin_pass}' || '${pass_salt}')), '127.0.0.1'        , '${admin_email}', true     , false  , NULL);

INSERT INTO emailAddresses
(id, email           , userId) VALUES
(1 , '${admin_email}', 1     );

update users set primaryEmailAddressId = 1 where id = 1;

INSERT INTO authorities
(userId, role  ) VALUES
(1     , 'USER'),
(1     , 'DEV');

-- rollback delete from users;
-- rollback delete from emailAddresses;
-- rollback delete from authorities;

-- changeset titov:10 dbms:postgresql runInTransaction:true
CREATE
TABLE
  admins (
  id                       BIGSERIAL           NOT NULL,
  email                    VARCHAR(255) UNIQUE NOT NULL,
  token                    VARCHAR(255)        NOT NULL,
  lastSignInIpAddress      VARCHAR(255)        NOT NULL,
  blocked                  boolean             NOT NULL,
  lastWebSignInTimestamp   timestamp with time zone,
  PRIMARY KEY (id)
);

-- rollback drop table if exists admins;

-- changeset titov:11 dbms:postgresql runInTransaction:true
CREATE
TABLE
  adminAuthorities(
  id BIGSERIAL NOT NULL ,
  adminId bigint NOT NULL,
  role VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT adminId_role UNIQUE (adminId,role),
  CONSTRAINT adminAuthorities_managers FOREIGN KEY (adminId) REFERENCES admins (id) MATCH SIMPLE ON DELETE CASCADE
);

-- rollback drop table if exists adminAuthorities;

-- changeset titov:12 dbms:postgresql runInTransaction:true
INSERT INTO admins
(id, email           , token                                                                                       , lastSignInIpAddress, blocked, lastWebSignInTimestamp) VALUES
(1 , '${admin_email}', MD5('${admin_email}' || MD5('${admin_pass_salt}'|| '${admin_pass}' || '${admin_pass_salt}')), '127.0.0.1'        , false  , NULL);

INSERT INTO adminAuthorities
(adminId, role  ) VALUES
(1     , 'ADMIN');

-- rollback delete from admins;
-- rollback delete from adminAuthorities;

-- changeset titov:13 dbms:postgresql runInTransaction:true
alter SEQUENCE users_id_seq RESTART WITH 2;

-- changeset titov:14 dbms:postgresql runInTransaction:true
alter SEQUENCE admins_id_seq RESTART WITH 2;
