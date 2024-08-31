create table "category" (
    id uuid not null,
    created_at timestamp(6),
    name varchar(255) not null,
    primary key (id)
);

create table "company" (
    id uuid not null,
    city varchar(255),
    name varchar(255),
    street varchar(255),
    primary key (id)
);

create table "offer" (
    id uuid not null,
    remote_interview boolean,
    time varchar(255),
    workplace varchar(255),
    created_at timestamp(6),
    published_at timestamp(6),
    salary_currency varchar(255),
    salary_employment_type varchar(255),
    salary_from float(53),
    salary_to float(53),
    seniority varchar(255),
    slug varchar(255),
    title varchar(255),
    company_id uuid,
    primary key (id)
);

create table "offer_categories" (
    offer_id uuid not null,
    categories_id uuid not null,
    primary key (offer_id, categories_id)
);

alter table if exists "category"
drop constraint if exists UK46ccwnsi9409t36lurvtyljak;

alter table if exists "category"
    add constraint UK46ccwnsi9409t36lurvtyljak unique (name);

alter table if exists "offer"
    add constraint FKbmi8uq5jg84sgudvncfjbupw6
    foreign key (company_id)
    references company;

alter table if exists "offer_categories"
    add constraint FKfux6ncow5i3semahx7pe6xbap
    foreign key (categories_id)
    references category;

alter table if exists "offer_categories"
    add constraint FKtpy5a0lafab5lm34o9g20w9ha
    foreign key (offer_id)
    references offer;