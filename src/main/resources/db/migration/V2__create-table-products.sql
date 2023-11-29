CREATE TABLE IF NOT EXISTS products(
    id uuid not null,
    title varchar(100) not null,
    description varchar(255),
    price numeric(10,2) not null,
    stock integer not null,
    brand varchar(100) not null,
    category varchar(100) not null,
    image varchar(255),
    primary key(id)
    )