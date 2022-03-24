drop table if exists hibernate_sequence
    Hibernate:

drop table if exists tb_address
    Hibernate:

drop table if exists tb_customer
    Hibernate:

create table hibernate_sequence (
    next_val bigint
) engine=InnoDB
Hibernate:

    insert into hibernate_sequence values ( 1 )
Hibernate:

create table tb_address (
                            address_id bigint not null,
                            location varchar(255),
                            customer_id bigint,
                            primary key (address_id)
) engine=InnoDB
Hibernate:

create table tb_customer (
                             customer_id bigint not null,
                             age integer not null,
                             username varchar(255),
                             primary key (customer_id)
) engine=InnoDB
Hibernate:

alter table tb_address
    add constraint FKt748w6ukdh6x4926sbd0bbjlg
        foreign key (customer_id)
            references tb_customer (customer_id)


    insert into tb_customer (customer_id, username, age)values (1,'user1',30);
insert into tb_customer (customer_id, username, age)values (2,'user2',31);
insert into tb_customer (customer_id, username, age)values (3,'user3',32);
insert into tb_customer (customer_id, username, age)values (4,'user4',33);
insert into tb_customer (customer_id, username, age)values (5,'user5',34);
insert into tb_customer (customer_id, username, age)values (6,'user6',35);
insert into tb_customer (customer_id, username, age)values (7,'user7',36);
insert into tb_customer (customer_id, username, age)values (8,'user8',37);
insert into tb_customer (customer_id, username, age)values (9,'user9',38);
insert into tb_customer (customer_id, username, age)values (10,'user10',39);

insert into tb_address (address_id, location, customer_id) values (1,'location1',1);
insert into tb_address (address_id, location, customer_id) values (2,'location2',2);
insert into tb_address (address_id, location, customer_id) values (3,'location3',3);
insert into tb_address (address_id, location, customer_id) values (4,'location4',4);
insert into tb_address (address_id, location, customer_id) values (5,'location5',5);
insert into tb_address (address_id, location, customer_id) values (6,'location6',6);
insert into tb_address (address_id, location, customer_id) values (7,'location7',7);
insert into tb_address (address_id, location, customer_id) values (8,'location8',8);
insert into tb_address (address_id, location, customer_id) values (9,'location9',9);
insert into tb_address (address_id, location, customer_id) values (10,'location10',10);

update hibernate_sequence
set next_val = 50
where 1=1;

commit;