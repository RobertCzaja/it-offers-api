create table "salary" (
    offer_id uuid not null,
    salary_currency varchar(3),
    salary_employment_type varchar(20),
    salary_from integer,
    salary_to integer,
    is_original boolean,
    primary key (offer_id, salary_currency)
);

alter table if exists "salary"
    add constraint fk_salary_offer_id_offer_id
    foreign key (offer_id)
    references offer;