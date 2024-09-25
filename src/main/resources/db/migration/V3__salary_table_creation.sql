create table "salary" (
    offer_id uuid not null,
    currency varchar(3),
    employment_type varchar(20),
    amount_from integer,
    amount_to integer,
    is_original boolean,
    primary key (offer_id, currency)
);

alter table if exists "salary"
    add constraint fk_salary_offer_id_offer_id
    foreign key (offer_id)
    references offer;