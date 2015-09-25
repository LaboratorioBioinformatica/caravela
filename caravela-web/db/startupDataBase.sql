CREATE USER 'caravela-user'@'localhost' IDENTIFIED BY 'c@r@vela';

/* create database */ 
create schema caravela;

/* giving privileges to user METAZOO on database METAZOO */  
GRANT ALL PRIVILEGES ON caravela.* TO 'caravela-user'@'localhost';
flush privileges;

USE caravela;


-- create table treatment
create table treatment (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(500) NOT NULL,
	PRIMARY KEY(id)
);

-- create table sample
create table sample (
	id INT NOT NULL AUTO_INCREMENT,
	treatment_id INT NOT NULL,
	name VARCHAR(100) NOT NULL,
	sample_status VARCHAR(50) NOT NULL,
	description VARCHAR(500) NOT NULL,
	PRIMARY KEY(id)
);

-- create table sample files
create table sample_file (
	id INT NOT NULL AUTO_INCREMENT,
	sample_id INT NOT NULL,
	file_type VARCHAR(50) NOT NULL,
	file_status VARCHAR(50) NOT NULL,
	file_path VARCHAR(300) NOT NULL,
	PRIMARY KEY(id)
);


insert into treatment values (id, "ZC3b", "Compostagem ZC3b");


select id from treatment where name = "ZC3b" into @treatmentId;
insert into sample values(id, @treatmentId, "ZC3b-day-01", "CREATED", "Compostagem ZC3b day 01 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-30", "CREATED", "Compostagem ZC3b day 30 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-64", "CREATED", "Compostagem ZC3b day 64 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-78", "CREATED", "Compostagem ZC3b day 78 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-99", "CREATED", "Compostagem ZC3b day 99 - Miseq");


select id from sample where name = "ZC3b-day-01" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc3b-day-01.json");