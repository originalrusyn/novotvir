-- liquibase formatted sql
-- changeset titov:1 dbms:postgresql runInTransaction:true
CREATE
TABLE
  users (
  id                 BIGSERIAL              NOT NULL,
  name               VARCHAR(255) UNIQUE NOT NULL,
  email              VARCHAR(255)        NOT NULL,
  token              VARCHAR(255)        NOT NULL,
  facebookId         VARCHAR(255),
  lastSignInIpAddress      VARCHAR(255)        NOT NULL,
  lastWebSignInTimestamp timestamp with time zone,
  PRIMARY KEY (id),
  CONSTRAINT name_facebookId UNIQUE (name, facebookId)
);

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

-- changeset titov:4 dbms:postgresql runInTransaction:true splitStatements:false endDelimiter:#
create function userLogProcedure() returns trigger as $$
begin
insert into userLogs(timestamp, userId, body) select current_timestamp, new.id, row_to_json(u.*) from users u where u.id=new.id;
return new;
end;
$$ LANGUAGE 'plpgsql';

-- changeset titov:5 dbms:postgresql runInTransaction:true
create trigger userLogTrigger after insert or update on users for each row execute PROCEDURE userLogProcedure();

-- changeset titov:6 dbms:postgresql runInTransaction:true
INSERT INTO users
(id, name      , email     , token                                                   , facebookId, lastSignInIpAddress, lastWebSignInTimestamp) VALUES
(1 , 'dev@i.ua', 'dev@i.ua', MD5( extract(epoch from now()) || 'dev@i.ua' || 'token'), NULL     , '127.0.0.1'        , NULL);

INSERT INTO authorities
(userId, role) VALUES
  (1, 'USER'),
  (1, 'DEV');
