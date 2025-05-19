CREATE TABLE IF NOT EXISTS "user"(
    id uuid primary key,
    name varchar not null,
    surname varchar not null,
    password varchar not null,
    email varchar not null,
    role varchar not null
);

INSERT INTO "user"(id, name, surname, password, email, role) values (gen_random_uuid(), 'Supercosmin', 'Administrator', '$2a$10$XMQ.i2EAigq3XolXvWeF3.FK6v5f/zLD.888HrvT1tDXB8s38pXwu', 'cosminch03@gmail.com', 'ADMIN');