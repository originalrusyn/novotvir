-- liquibase formatted sql
-- changeset titov:1 dbms:postgresql runInTransaction:true
CREATE
TABLE
  users (
  id                       BIGSERIAL           NOT NULL,
  name                     VARCHAR(255) UNIQUE NOT NULL,
  email                    VARCHAR(255)        NOT NULL,
  token                    VARCHAR(255)        NOT NULL,
  facebookId               VARCHAR(255),
  lastSignInIpAddress      VARCHAR(255)        NOT NULL,
  activationToken          VARCHAR(255)        NOT NULL,
  activated                boolean             NOT NULL,
  blocked                  boolean             NOT NULL,
  lastWebSignInTimestamp   timestamp with time zone,
  PRIMARY KEY (id),
  CONSTRAINT name_facebookId UNIQUE (name, facebookId)
);

-- rollback drop table if exists users;

-- changeset titov:2 dbms:postgresql runInTransaction:true
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

-- changeset titov:3 dbms:postgresql runInTransaction:true
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

-- changeset titov:4 dbms:postgresql runInTransaction:true splitStatements:false endDelimiter:#
create function userLogProcedure() returns trigger as $$
begin
insert into userLogs(timestamp, userId, body) select current_timestamp, new.id, row_to_json(u.*) from users u where u.id=new.id;
return new;
end;
$$ LANGUAGE 'plpgsql';

-- rollback drop function if exists userLogProcedure;

-- changeset titov:5 dbms:postgresql runInTransaction:true
create trigger userLogTrigger after insert or update on users for each row execute PROCEDURE userLogProcedure();

-- rollback drop trigger if exists userLogTrigger;

-- changeset titov:6 dbms:postgresql runInTransaction:true
INSERT INTO users
(id, name            , email           , token                                                                           , facebookId, lastSignInIpAddress, activationToken , activated, blocked, lastWebSignInTimestamp) VALUES
(1 , '${admin_email}', '${admin_email}', MD5('${admin_email}' || MD5('${pass_salt}'|| '${admin_pass}' || '${pass_salt}')), NULL      , '127.0.0.1'        , '${admin_email}', true     , false  , NULL);

INSERT INTO authorities
(userId, role  ) VALUES
(1     , 'USER'),
(1     , 'DEV');

-- rollback delete from users;
-- rollback delete from authorities;

-- changeset titov:7 dbms:postgresql runInTransaction:true
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

-- changeset titov:8 dbms:postgresql runInTransaction:true
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

-- changeset titov:9 dbms:postgresql runInTransaction:true
INSERT INTO admins
(id, email           , token                                                                                       , lastSignInIpAddress, blocked, lastWebSignInTimestamp) VALUES
(1 , '${admin_email}', MD5('${admin_email}' || MD5('${admin_pass_salt}'|| '${admin_pass}' || '${admin_pass_salt}')), '127.0.0.1'        , false  , NULL);

INSERT INTO adminAuthorities
(adminId, role  ) VALUES
(1     , 'ADMIN');

-- rollback delete from admins;
-- rollback delete from adminAuthorities;

-- changeset titov:10 dbms:postgresql runInTransaction:true
alter SEQUENCE users_id_seq RESTART WITH 2;

-- changeset titov:11 dbms:postgresql runInTransaction:true
alter SEQUENCE admins_id_seq RESTART WITH 2;
