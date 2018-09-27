create table fct_facility (
	id varchar(255) not null,
	category_id varchar(255),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	name varchar(50),
	work_schedule_category_id varchar(255),
	primary key (id)
) engine=InnoDB;

create table fct_facility_process_type (
	id varchar(255) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	defective_variation_prate decimal(7,5),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	process_type_id varchar(255),
	speed_variation_prate decimal(7,5),
	facility_id varchar(255),
	primary key (id)
) engine=InnoDB;

create table fct_facility_schedule (
	id varchar(255) not null,
	begin_date_time datetime,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	duration_minutes bigint not null,
	end_date_time datetime,
	flexible bit not null,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	process_id varchar(255),
	facility_id varchar(255),
	primary key (id)
) engine=InnoDB;

create index FCT_FACILITY_PROCESS_TYPE_FACILITY_ID_PROCESS_TYPE_ID_IDX
	on fct_facility_process_type (facility_id,process_type_id);

create index FCT_FACILITY_SCHEDULE_FACILITY_ID_IDX
	on fct_facility_schedule (facility_id);

alter table fct_facility_process_type
	add constraint FKik4ish1wjg9iatsjru75oywu0 foreign key (facility_id)
	references fct_facility (id);

alter table fct_facility_schedule
	add constraint FKpxpy51jw4jfvk7g3katqk8oy5 foreign key (facility_id)
	references fct_facility (id);
