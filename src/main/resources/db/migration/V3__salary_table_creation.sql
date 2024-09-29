create table "salary" (
    id uuid not null,
    currency varchar(3),
    employment_type varchar(20),
    amount_from integer,
    amount_to integer,
    is_original boolean,
    primary key (id)
);

create table "offer_salary" (
    offer_id uuid not null,
    salary_id uuid not null,
    primary key (offer_id, salary_id)
);
