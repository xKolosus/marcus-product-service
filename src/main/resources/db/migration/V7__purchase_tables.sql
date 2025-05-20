create table if not exists purchase(
    id uuid primary key,
    user_id uuid references "user"(id)
);

create table if not exists purchase_detail(
    purchase_id uuid,
    product_id uuid,
    primary key(purchase_id, product_id),
    constraint fk_purchase_id foreign key (purchase_id) references purchase(id),
    constraint fk_product_id foreign key (product_id) references product(id)
);