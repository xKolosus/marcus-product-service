CREATE TABLE IF NOT EXISTS category(
    id uuid primary key,
    name varchar,
    description varchar,
    enabled bool default false
)