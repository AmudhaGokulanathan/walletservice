--drop table if exists player_account_info CASCADE ;
--drop table if exists transaction_info CASCADE ;
 --create table player_account_info (id bigint not null, account_no integer, balance double, name varchar(255), primary key (id));
 --create table transaction_info (transaction_id varchar(255) not null, amount double, transaction_date date, transaction_type varchar(255), player_id bigint, primary key (transaction_id));
-- alter table transaction_info add constraint FKlkd108w2ktskisvx15wfbs501 foreign key (player_id) references player_account_info;


insert into player_account_info (id,name,account_no,balance,version) 
values (1,'Anu',12345,100.00,0);
insert into player_account_info (id,name,account_no,balance,version) 
values (2,'Goki',23456,100.00,1);

insert into transaction_info (transaction_id,amount,transaction_date,transaction_type,player_id)
values ('101',10,current_date(),'credit',1);

insert into transaction_info (transaction_id,amount,transaction_date,transaction_type,player_id)
values ('102',10,current_date(),'credit',1);

commit;


