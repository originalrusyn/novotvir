-- liquibase formatted sql logicalFilePath:release1.0.0.sql
-- changeset titov:1 dbms:postgresql runInTransaction:true
CREATE TABLE
  users (
  id                       BIGSERIAL           NOT NULL,
  name                     VARCHAR(255) UNIQUE NOT NULL,
  primaryEmailAddressId    BIGINT,
  token                    VARCHAR(255)        NOT NULL,
  activationToken          VARCHAR(255),
  activated                BOOLEAN             NOT NULL,
  blocked                  BOOLEAN             NOT NULL,
  lastSignInIpAddress      VARCHAR(255),
  lastSignInDateTime       TIMESTAMP     WITH TIME ZONE,
  version                  BIGINT              NOT NULL,
  PRIMARY KEY (id)
);

-- rollback drop table if exists users;

-- changeset titov:2 dbms:postgresql runInTransaction:true
CREATE TABLE
  emailAddresses (
  id                       BIGSERIAL           NOT NULL,
  email                    VARCHAR(255)        NOT NULL,
  userId                   BIGINT              NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT userId_email UNIQUE (userId, email),
  CONSTRAINT emailAddresses_users FOREIGN KEY (userId) REFERENCES users (id) MATCH SIMPLE
);

-- rollback drop table if exists emailAddresses;

-- changeset titov:3 dbms:postgresql runInTransaction:true
ALTER TABLE users ADD CONSTRAINT users_emailAddresses FOREIGN KEY (primaryEmailAddressId) REFERENCES emailAddresses (id) MATCH SIMPLE ON DELETE CASCADE;

-- rollback alter table users drop constraint if exists users_emailAddresses;

-- changeset titov:4 dbms:postgresql runInTransaction:true
CREATE TABLE
  socialUserConnection (
  userId                 VARCHAR(255) NOT NULL,
  providerId             VARCHAR(255) NOT NULL,
  providerUserId         VARCHAR(255),
  rank                   INT          NOT NULL,
  displayName            VARCHAR(255),
  profileUrl             VARCHAR(512),
  imageUrl               VARCHAR(512),
  accessToken            TEXT         NOT NULL,
  secret                 TEXT,
  refreshToken           TEXT,
  expireTime             BIGINT,
  PRIMARY KEY (userId, providerId, providerUserId),
  CONSTRAINT UserConnectionRank UNIQUE (userId, providerId, rank),
  CONSTRAINT UserConnection_user FOREIGN KEY (userId) REFERENCES users (name) MATCH SIMPLE
);

-- rollback drop table if exists socialUserConnection;

-- changeset titov:5 dbms:postgresql runInTransaction:true
CREATE TABLE
  authorities(
  id BIGSERIAL NOT NULL,
  userId BIGINT NOT NULL,
  role VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT userId_role UNIQUE (userId,role),
  CONSTRAINT authorities_user FOREIGN KEY (userId) REFERENCES users (id) MATCH SIMPLE ON DELETE CASCADE
);

-- rollback drop table if exists authorities;

-- changeset titov:6 dbms:postgresql runInTransaction:true
CREATE TABLE
  userLogs(
  id BIGSERIAL NOT NULL,
  timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
  userId BIGINT NOT NULL,
  body JSON,
  PRIMARY KEY (id)
);

-- rollback drop table if exists authorities;

-- changeset titov:7 dbms:postgresql runInTransaction:true splitStatements:false endDelimiter:#
CREATE FUNCTION userLogProcedure() RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO userLogs(timestamp, userId, body) SELECT current_timestamp, NEW.id, row_to_json(u.*) FROM users u WHERE u.id=new.id;
  RETURN NEW;
END;
$$ LANGUAGE 'plpgsql';

-- rollback drop function if exists userLogProcedure;

-- changeset titov:8 dbms:postgresql runInTransaction:true
CREATE TRIGGER userLogTrigger AFTER INSERT OR UPDATE ON users FOR EACH ROW EXECUTE PROCEDURE userLogProcedure();

-- rollback drop trigger if exists userLogTrigger;

-- changeset titov:9 dbms:postgresql runInTransaction:true
INSERT INTO users
(id, name            , token                                                                           , lastSignInIpAddress, activationToken , activated, blocked, lastSignInDateTime, version) VALUES
(1 , '${admin_email}', MD5('${admin_email}' || MD5('${pass_salt}'|| '${admin_pass}' || '${pass_salt}')), '127.0.0.1'        , '${admin_email}', true     , false  , NULL              , 1);

INSERT INTO emailAddresses
(id, email           , userId) VALUES
(1 , '${admin_email}', 1     );

UPDATE users SET primaryEmailAddressId = 1 WHERE id = 1;

INSERT INTO authorities
(userId, role  ) VALUES
(1     , 'USER'),
(1     , 'DEV');

-- rollback delete from users;
-- rollback delete from emailAddresses;
-- rollback delete from authorities;

-- changeset titov:10 dbms:postgresql runInTransaction:true
CREATE TABLE
  admins (
  id                       BIGSERIAL           NOT NULL,
  email                    VARCHAR(255) UNIQUE NOT NULL,
  token                    VARCHAR(255)        NOT NULL,
  lastSignInIpAddress      VARCHAR(255)        NOT NULL,
  blocked                  BOOLEAN             NOT NULL,
  lastSignInDateTime       TIMESTAMP WITH TIME ZONE,
  PRIMARY KEY (id)
);

-- rollback drop table if exists admins;

-- changeset titov:11 dbms:postgresql runInTransaction:true
CREATE TABLE
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
(id, email           , token                                                                                       , lastSignInIpAddress, blocked, lastSignInDateTime) VALUES
(1 , '${admin_email}', MD5('${admin_email}' || MD5('${admin_pass_salt}'|| '${admin_pass}' || '${admin_pass_salt}')), '127.0.0.1'        , false  , NULL);

INSERT INTO adminAuthorities
(adminId, role  ) VALUES
(1     , 'ADMIN');

-- rollback delete from admins;
-- rollback delete from adminAuthorities;

-- changeset titov:13 dbms:postgresql runInTransaction:true
ALTER SEQUENCE users_id_seq RESTART WITH 2;

-- changeset titov:14 dbms:postgresql runInTransaction:true
ALTER SEQUENCE admins_id_seq RESTART WITH 2;

-- changeset titov:15 dbms:postgresql runInTransaction:true
ALTER SEQUENCE emailAddresses_id_seq RESTART WITH 2;

-- changeset titov:16 dbms:postgresql runInTransaction:true
ALTER SEQUENCE admins_id_seq RESTART WITH 2;

-- changeset titov:17 dbms:postgresql runInTransaction:true
ALTER SEQUENCE adminAuthorities_id_seq RESTART WITH 2;

-- changeset titov:18 dbms:postgresql runInTransaction:true
CREATE TABLE
  clients (
  id                       BIGSERIAL           NOT NULL,
  uID                      VARCHAR(255)        NOT NULL,
  clientType               VARCHAR(255)        NOT NULL,
  platformVersion          VARCHAR(255),
  userId                   bigint              NOT NULL,
  pushToken                VARCHAR(255),
  version                  BIGINT              NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT userId_uID UNIQUE (userId, uID),
  CONSTRAINT clients_user FOREIGN KEY (userId) REFERENCES users (id) MATCH SIMPLE ON DELETE CASCADE
);

-- rollback drop table if exists clients;

-- changeset titov:19 dbms:postgresql runInTransaction:true
CREATE TABLE
  pushNotifications (
  id                         BIGSERIAL           NOT NULL,
  pushMessage                VARCHAR(255)        NOT NULL,
  queryNameForFetchReceivers VARCHAR(255)        NOT NULL,
  version                    BIGINT              NOT NULL,
  PRIMARY KEY (id)
);

-- rollback drop table if exists pushNotifications;

-- changeset titov:20 dbms:postgresql runInTransaction:true
CREATE TABLE
  tasks (
  id                         BIGSERIAL           NOT NULL,
  lastTaskRunDateTime        TIMESTAMP     WITH TIME ZONE,
  lastTaskCompletionDateTime TIMESTAMP     WITH TIME ZONE,
  maxRetriesOnError          INT                 NOT NULL,
  schedule                   VARCHAR(255)        NOT NULL,
  maxRepeatCount             BIGINT              NOT NULL,
  repeatCount                BIGINT              NOT NULL,
  version                    BIGINT              NOT NULL,
  PRIMARY KEY (id)
);

-- rollback drop table if exists tasks;

-- changeset titov:21 dbms:postgresql runInTransaction:true
CREATE TABLE
  pushNotificationTasks (
  id                         BIGINT              NOT NULL,
  pushNotificationId         BIGINT              NOT NULL,
  userFiltrationQuery        VARCHAR(255)        NOT NULL,
  CONSTRAINT pushNotificationTasks_task FOREIGN KEY (id) REFERENCES tasks (id) MATCH SIMPLE ON DELETE CASCADE
);

-- rollback drop table if exists tasks;

-- changeset titov:22 dbms:postgresql runInTransaction:true
CREATE TABLE
  works (
  id                    BIGSERIAL                  NOT NULL,
  workItemId            BIGINT                     NOT NULL,
  taskId                BIGINT                     NOT NULL,
  workStatus            VARCHAR(255)               NOT NULL,
  forScheduleDateTime   TIMESTAMP WITH TIME ZONE   NOT NULL,
  creationDateTime      TIMESTAMP WITH TIME ZONE   NOT NULL,
  retriesOnError        INT                        NOT NULL,
  version               BIGINT                     NOT NULL,
  CONSTRAINT works_task FOREIGN KEY (taskId) REFERENCES tasks (id) MATCH SIMPLE
);

-- rollback drop table if exists works;

-- changeset titov:23 dbms:postgresql runInTransaction:true
CREATE TABLE
processingWorkItems (
taskId                BIGINT                     NOT NULL,
workItemId            BIGINT                     NOT NULL,
CONSTRAINT processingWorkItems_task FOREIGN KEY (taskId) REFERENCES tasks (id) MATCH SIMPLE
);

-- rollback drop table if exists processingWorkItems;

