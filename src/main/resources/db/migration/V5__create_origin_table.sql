create table "origin" (
    id varchar(30) not null,
    scrapping_id uuid not null,
    provider varchar(20) not null,
    primary key (id)
);

alter table if exists "origin"
    add constraint unique_origin_record unique (id, scrapping_id, provider);

alter table if exists "offer"
    add origin_id varchar(30) default null;

alter table if exists "offer"
    add constraint fk_offer_origin_id_origin_id
    foreign key (origin_id)
    references origin(id)
    on delete cascade;