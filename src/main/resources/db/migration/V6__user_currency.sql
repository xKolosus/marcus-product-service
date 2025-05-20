alter table "user" add column currency varchar not null default 'EUR';
alter table "user" add check (currency in ('USD', 'EUR', 'GBP'));