

create table users(
    id bigint auto_increment,
    name varchar(50),
    balance int,
    primary key (id)
);


create table user_transaction(
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    foreign key (user_id) references users(id) on delete cascade
);


insert into users(name, balance)
    values
    ('Sam', 15000),
    ('Mike', 10000),
    ('June', 80000),
    ('Eli', 90000),
    ('Aminu', 90000);

