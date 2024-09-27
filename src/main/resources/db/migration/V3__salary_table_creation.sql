create table "salary" (
    id uuid not null,
    offer_id uuid not null,
    currency varchar(3),
    employment_type varchar(20),
    amount_from integer,
    amount_to integer,
    is_original boolean,
    primary key (id)
);

alter table if exists "salary"
    add constraint fk_salary_offer_id_offer_id
    foreign key (offer_id)
    references offer;

alter table if exists "salary"
    add constraint unique_salary_record
    unique (offer_id, currency, employment_type);
