# --- !Ups

CREATE TABLE user(
    id int auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255) unique,
    password varchar(255),
    picture varchar(255),
    details text,
    primary key (id)
);

CREATE TABLE post(
    id int auto_increment,
    text text,
    likes int,
    user_id int,
    primary key (id),
    foreign key (user_id) references user(id) on delete cascade
);

CREATE TABLE love(
    user_id int,
    post_id int,
    foreign key (user_id) references user(id) on delete cascade,
    foreign key (post_id) references post(id) on delete cascade,
     primary key(user_id, post_id)
);

CREATE TABLE  friendship (
    sender_id int,
    accepter_id int,
    request_status varchar(255),
    primary key (sender_id, accepter_id),
    foreign key (sender_id) references user(id) on delete cascade,
    foreign key (accepter_id) references user(id) on delete cascade

);


# --- !Downs




