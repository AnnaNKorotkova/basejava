create table resume
(
    uuid      char(36) not null constraint resume_pk primary key,
    full_name text     not null
);

alter table resume owner to postgres;

create table contact
(
    id          serial not null,
    resume_uuid char(36) references resume (uuid) on delete cascade,
    type        text   not null,
    values      text   not null
);

alter table contact owner to postgres;

create unique index contact_uuid_type_index on contact (resume_uuid, type);

create table section
(
    id serial,
    resume_uuid CHAR(36)
        constraint section_resume_uuid_fk
            references resume
            on delete cascade,
    type text not null,
    text_section text not null
);

create unique index section_id_uindex
    on section (id);

alter table section
    add constraint section_pk
        primary key (id);

