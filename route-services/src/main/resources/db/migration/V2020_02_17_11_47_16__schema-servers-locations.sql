create table ServerLocations
(
    id       varchar(64) primary key default newid(),
    location varchar(64) unique,
    hostUrl  varchar(100) not null
);

insert into ServerLocations(location, hostUrl)
values ('server1', 'http://localhost:4040'),
       ('server2', 'http://localhost:4041');
