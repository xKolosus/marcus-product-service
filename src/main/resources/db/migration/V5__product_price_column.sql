alter table product add column if not exists amount decimal(19, 2) not null default 0;
alter table product add column if not exists currency varchar not null default 'EUR';
alter table product add check (currency in ('USD', 'EUR', 'GBP'));
alter table product add column stock int8 not null default 0;
-- we will setting a sample price
update product set amount = 15;