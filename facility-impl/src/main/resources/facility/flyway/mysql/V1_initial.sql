create table fct_facility (
	id binary(16) not null,
	category_id varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	name varchar(50),
	work_schedule_category_id varchar(50),
	primary key (id)
) engine=InnoDB;

create table fct_facility_process_type (
	id binary(16) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	defective_variation_rate decimal(7,5),
	facility_id binary(16),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	process_type_id varchar(50),
	speed_variation_rate decimal(7,5),
	primary key (id)
) engine=InnoDB;

create table fct_facility_schedule (
	id binary(16) not null,
	begin_date_time datetime,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	duration_minutes bigint not null,
	end_date_time datetime,
	facility_id binary(16),
	flexible bit not null,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	process_id binary(16),
	primary key (id)
) engine=InnoDB;

create index IDX7x166231qg16jdcsh7610nbyo
	on fct_facility_process_type (facility_id);

alter table fct_facility_process_type
	add constraint UKov74n2vsw1xm5gibd86lwkwn2 unique (facility_id,process_type_id);

create index IDXgkkgqs82jh5wsc0yiym75ypu5
	on fct_facility_schedule (facility_id);
