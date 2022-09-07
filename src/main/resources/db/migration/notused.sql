create table dur
(
    dur_id           bigint not null,
    age_taboo        varchar(255),
    combined_taboo   varchar(255),
    pregnant_taboo   varchar(255),
    medicine_info_id bigint,
    primary key (dur_id)
) engine=InnoDB

create table ingredient
(
    ingredient_id    bigint not null,
    created_at       datetime(6) not null,
    updated_at       datetime(6) not null,
    created_by       bigint not null,
    updated_by       bigint not null,
    ingredient_name  varchar(255),
    medicine_info_id bigint,
    primary key (ingredient_id)
) engine=InnoDB

create table kpic
(
    kpic_id          bigint not null,
    created_at       datetime(6) not null,
    updated_at       datetime(6) not null,
    created_by       bigint not null,
    updated_by       bigint not null,
    kpic             varchar(255),
    medicine_info_id bigint,
    primary key (kpic_id)
) engine=InnoDB

create table medicine
(
    medicine_id             bigint not null,
    created_at              datetime(6) not null,
    updated_at              datetime(6) not null,
    created_by              bigint not null,
    updated_by              bigint not null,
    detail_administer_count integer,
    detail_medicine_effect  varchar(255),
    detail_medicine_name    varchar(255),
    detail_prescribe_count  varchar(255),
    detail_treat_date       integer,
    detail_treat_type       varchar(255),
    prescription_id         bigint,
    primary key (medicine_id)
) engine=InnoDB

create table medicine_info
(
    medicine_info_id     bigint not null,
    created_at           datetime(6) not null,
    updated_at           datetime(6) not null,
    created_by           bigint not null,
    updated_by           bigint not null,
    drug_administer_path varchar(255),
    drug_making_company  varchar(255),
    drug_medicine_group  varchar(255),
    drug_pay_info        varchar(255),
    drug_product_name    varchar(255),
    drug_sales_company   varchar(255),
    drug_shape           varchar(255),
    drug_single_yn       varchar(255),
    drug_special_yn      varchar(255),
    medicine_id          bigint,
    primary key (medicine_info_id)
) engine=InnoDB

create table member
(
    member_id                  bigint      not null auto_increment,
    callback_data              varchar(255),
    callback_type              varchar(255),
    callback_id                varchar(255),
    carrier                    varchar(10) not null,
    email                      varchar(40) not null,
    jumin                      varchar(8)  not null,
    member_role                varchar(16) not null,
    my_data_detail_update_time datetime(6),
    my_data_update_time        datetime(6),
    name                       varchar(30) not null,
    nick_name                  varchar(30) not null,
    phone_number               varchar(16) not null,
    profile_image_url          varchar(150),
    primary key (member_id)
) engine=InnoDB

create table prescription
(
    prescription_id     bigint not null,
    created_at          datetime(6) not null,
    updated_at          datetime(6) not null,
    created_by          bigint not null,
    updated_by          bigint not null,
    administer_interval integer,
    daily_count         integer,
    end_date            integer,
    medicine_count      varchar(255),
    prescribe_cnt       varchar(255),
    total_day_count     integer,
    treat_date          integer,
    treat_dsnm          varchar(255),
    treat_medicalnm     varchar(255),
    treat_type          varchar(255),
    treat_dsgb          varchar(255),
    visit_count         varchar(255),
    member_id           bigint,
    primary key (prescription_id)
) engine=InnoDB

alter table member
    add constraint UK_mbmcqelty0fbrvxp1q58dn57t unique (email)

alter table member
    add constraint UK_f3xpkeiwuq8kwkt45lkvanwsd unique (nick_name)

alter table dur
    add constraint FK30p20iu4vbfyc393fmh2cgoco
        foreign key (medicine_info_id)
            references medicine_info (medicine_info_id)

alter table ingredient
    add constraint FK4q0bk0kodpvfnkkm4r8f6nh8u
        foreign key (medicine_info_id)
            references medicine_info (medicine_info_id)

alter table kpic
    add constraint FKbfcrwyleht7iesyfpccruwopj
        foreign key (medicine_info_id)
            references medicine_info (medicine_info_id)

alter table medicine
    add constraint FK3c5uaisn2e5sdx9gonhjf61x5
        foreign key (prescription_id)
            references prescription (prescription_id)

alter table medicine_info
    add constraint FKl3t5agnf0wj3elgp46qhnwux5
        foreign key (medicine_id)
            references medicine (medicine_id)

alter table prescription
    add constraint FK9af8js63ensswx63j2v1fdvde
        foreign key (member_id)
            references member (member_id)