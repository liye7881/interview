create table t_services (service_id int IDENTITY primary key, name varchar(255) not null, version int)
CREATE INDEX idx_service_name on t_services (name)
create table t_users (user_id int IDENTITY primary key, name varchar(255) not null, version int)
CREATE INDEX idx_user_name on t_users (name)
create table t_user_service_rela (rela_id int IDENTITY primary key, service_id int, user_id int, version int)
CREATE INDEX idx_rela_user on t_user_service_rela (user_id)
CREATE INDEX idx_rela_service on t_user_service_rela (service_id)
insert into t_services (name, version) values ('service_0', 0)
insert into t_services (name, version) values ('service_1', 0)
insert into t_services (name, version) values ('service_2', 0)
insert into t_services (name, version) values ('service_3', 0)
insert into t_services (name, version) values ('service_4', 0)
insert into t_services (name, version) values ('service_5', 0)
insert into t_services (name, version) values ('service_6', 0)
insert into t_services (name, version) values ('service_7', 0)
insert into t_services (name, version) values ('service_8', 0)
insert into t_services (name, version) values ('service_9', 0)
insert into t_users (name, version) values ('user_0', 0)
insert into t_users (name, version) values ('user_1', 0)
insert into t_users (name, version) values ('user_2', 0)
insert into t_users (name, version) values ('user_3', 0)
insert into t_users (name, version) values ('user_4', 0)
insert into t_users (name, version) values ('user_5', 0)
insert into t_users (name, version) values ('user_6', 0)
insert into t_users (name, version) values ('user_7', 0)
insert into t_users (name, version) values ('user_8', 0)
insert into t_users (name, version) values ('user_9', 0)