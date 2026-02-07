create table topicos(
    id bigint not null auto_increment,
    titulo varchar(200) not null,
    mensaje varchar(500) not null,
    fecha_creacion datetime not null,
    status varchar(20) not null,
    autor varchar(100) not null,
    curso varchar(100) not null,
    primary key(id),
    constraint uq_topico unique (titulo, mensaje)
);
