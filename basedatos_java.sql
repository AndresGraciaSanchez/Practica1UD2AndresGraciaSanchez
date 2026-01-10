create database if not exists tienda_software;
--
use tienda_software;
--

create table if not exists desarrolladores (
    iddesarrollador int auto_increment primary key,
    nombre varchar(50) not null,
    email varchar(100) not null,
    pais varchar(50),
    fecharegistro date
);
--

create table if not exists categorias (
    idcategoria int auto_increment primary key,
    nombre varchar(50) not null,
    descripcion varchar(200),
    nivel varchar(30)
);
--

create table if not exists licencias (
    idlicencia int auto_increment primary key,
    nombre varchar(50) not null,
    tipo varchar(50),
    url varchar(200),
    costesoporte float
);
--

create table if not exists software (
    idsoftware int auto_increment primary key,
    nombre varchar(100) not null,
    version varchar(20) not null,
    precio float not null,
    fechalanzamiento date,
    activo boolean not null,
    iddesarrollador int not null,
    idcategoria int not null,
    idlicencia int not null
);
--

alter table software
add foreign key (iddesarrollador) references desarrolladores(iddesarrollador),
add foreign key (idcategoria) references categorias(idcategoria),
add foreign key (idlicencia) references licencias(idlicencia);
--

create function existeSoftware(f_nombre varchar(100))
returns bit
begin
    declare i int;
    set i = 0;
    while (i < (select max(idsoftware) from software)) do
        if ((select nombre from software
             where idsoftware = (i + 1)) like f_nombre)
        then return 1;
        end if;
        set i = i + 1;
    end while;
    return 0;
end;
--

create function existeDesarrollador(f_nombre varchar(50))
returns bit
begin
    declare i int;
    set i = 0;
    while (i < (select max(iddesarrollador) from desarrolladores)) do
        if ((select nombre from desarrolladores
             where iddesarrollador = (i + 1)) like f_nombre)
        then return 1;
        end if;
        set i = i + 1;
    end while;
    return 0;
end;
--

create function existeCategoria(f_nombre varchar(50))
returns bit
begin
    declare i int;
    set i = 0;
    while (i < (select max(idcategoria) from categorias)) do
        if ((select nombre from categorias
             where idcategoria = (i + 1)) like f_nombre)
        then return 1;
        end if;
        set i = i + 1;
    end while;
    return 0;
end;
--

create function existeLicencia(f_nombre varchar(50))
returns bit
begin
    declare i int;
    set i = 0;
    while (i < (select max(idlicencia) from licencias)) do
        if ((select nombre from licencias
             where idlicencia = (i + 1)) like f_nombre)
        then return 1;
        end if;
        set i = i + 1;
    end while;
    return 0;
end;
--
