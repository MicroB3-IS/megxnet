set statement_timeout = 0;
set client_encoding = 'UTF8';
set standard_conforming_strings = off;
set check_function_bodies = false;
set client_min_messages = warning;
set escape_string_warning = off;


create schema auth;
alter schema auth OWNER to postgres;
set search_path = auth, pg_catalog;
set default_tablespace = '';
set default_with_oids = false;

create table users(
    logname text not null,
    first_name text,
    initials text,
    last_name text,
    description text,
    join_date timestamp with time zone not null,
    pass text,
    diabled boolean not null,
    constraint pk_users primary key (logname)
);

create table roles(
    label text not null,
    description text,
    constraint pk_roles primary key (label)
);

create table consumers(
    key text not null,
    secret text not null,
    name text not null,
    description text,
    oob boolean not null default false,
    trusted boolean not null default false,
    expiration timestamp with time zone not null,
    logname text not null,
    callback_url text,
    constraint pk_consumers primary key (key),
    constraint fk_consumers_users foreign key (logname)
            references users(logname) on update cascade
                                      on delete cascade
);

create table access_tokens(
    token text not null,
    secret text not null,
    verifier text,
    callback_url text,
    consumer_key text not null,
    user_log text not null,
    token_created timestamp with time zone,
    constraint pk_access_tokens primary key (token),
    constraint fk_access_tokens_consumers foreign key (consumer_key)
                                references consumers(key) on update cascade
                                                          on delete cascade,
    constraint fk_access_tokens_users foreign key (user_log) 
                                references users(logname) on update cascade
                                                            on delete cascade
                    
);

create table permissions(
    label text not null,
    description text,
    constraint pk_permissions primary key (label)
);

create table web_resource_permissions(
    url_path text not null,
    http_method text not null,
    role text not null,
    constraint pk_web_resource_permissions 
            primary key (url_path, http_method),
    constraint fk_web_resource_permissions_roles
        foreign key (role) references roles(label) on update cascade
                                                   on delete cascade
);

-- ref permissions M---M roles 
create table has_permissions(
    role text not null,
    permission text not null,
    constraint pk_has_permissions primary key (role, permission),
    constraint fk_has_permissions_permissions 
            foreign key (permission) references permissions(label)
                    on update cascade
                    on delete cascade,
                    
    constraint fk_has_permissions_roles foreign key (role)
        references roles(label) on update cascade
                                on delete cascade
);
