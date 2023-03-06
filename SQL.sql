CREATE DATABASE script;
use script;

CREATE TABLE todo (
   id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  date date NOT NULL,
  user_id int NOT NULL,
  urgency_id int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user(id),
  FOREIGN KEY (urgency_id) REFERENCES urgency(id)
);

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ;

CREATE TABLE urgency (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ;
