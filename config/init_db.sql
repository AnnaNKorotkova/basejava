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