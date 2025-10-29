    create table tbl_purchase
    (
        post_id                  bigint primary key,
        purchase_product_price   int             default 1000,
        purchase_limit_time      int          not null,
        purchase_product_count   int          not null,
        purchase_country         varchar(255) not null,
        purchase_delivery_method delivery_method default 'direct',
        constraint fk_purchase_post foreign key (post_id)
            references tbl_post (id)
    );
