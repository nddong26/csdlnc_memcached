
p table test_db.customer;
create table test_db.customer
(
  customer_id int not null,
  customer_name varchar(200) null,
  sex int(1) null,
  status int(1) null,
  constraint customer_customer_id_uindex
    unique (customer_id)
)  ENGINE=MEMORY;

alter table test_db.customer
  add primary key (customer_id);

drop table test_db.order_detail;
create table test_db.order_detail
(
  order_detail_id bigint not null,
  order_id bigint not null,
  product_id int not null,
  product_price double null,
  constraint order_detail_order_detail_id_uindex
    unique (order_detail_id)
)  ENGINE=MEMORY;

create index order_idx
  on test_db.order_detail (order_id);

alter table test_db.order_detail
  add primary key (order_detail_id);

drop table test_db.orders;
create table test_db.orders
(
  order_id bigint not null,
  customer_id int(10) null,
  buy_date date null,
  status int(1) null,
  constraint order_order_id_uindex
    unique (order_id)
)  ENGINE=MEMORY;

alter table test_db.orders
  add primary key (order_id);


drop table test_db.product;
create table test_db.product
(
  product_id int not null,
  product_name varchar(200) null,
  product_price double null,
  description varchar(1000) null,
  status int(1) null,
  constraint product_product_id_uindex
    unique (product_id)
)  ENGINE=MEMORY;

alter table test_db.product
  add primary key (product_id);


