
CREATE  TABLE users (
  username VARCHAR(50) NOT NULL ,
  password VARCHAR(500) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE user_roles (
  user_role_id int(12) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  role varchar(50) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
);

CREATE TABLE authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);

