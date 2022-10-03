CREATE DATABASE social;

CREATE TABLE USERS (
	id bigserial not null primary key,
	name varchar (100) not null,
	age integer not null
);

CREATE TABLE POSTS (
	id bigserial not null primary key,
	post_text varchar(150) not null
	date_time timestamp
	user_id bigint not null references USERS(id)
)

