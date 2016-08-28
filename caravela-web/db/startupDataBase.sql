CREATE USER 'caravela-user'@'localhost' IDENTIFIED BY 'c@r@vela';

/* create database */ 
create schema caravela;

/* giving privileges to user METAZOO on database METAZOO */  
GRANT ALL PRIVILEGES ON caravela.* TO 'caravela-user'@'localhost';
flush privileges;

USE caravela;


-- create table taxon 
create table taxon (
	id INT NOT NULL AUTO_INCREMENT,
	taxonomy_id INT NOT NULL,
	taxonomy_parent_id INT NOT NULL,
	rank VARCHAR(50) NOT NULL,
	scientific_name VARCHAR(200) NOT NULL,
	PRIMARY KEY(id), 
	INDEX (taxonomy_id), INDEX (taxonomy_parent_id), INDEX (scientific_name)
);

-- create table treatment
create table treatment (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(500) NULL,
	PRIMARY KEY(id)
);

-- create table sample
create table sample (
	id INT NOT NULL AUTO_INCREMENT,
	treatment_id INT NOT NULL,
	name VARCHAR(100) NOT NULL,
	sample_status VARCHAR(50) NOT NULL,
	description VARCHAR(500) NULL,
	PRIMARY KEY(id),
	INDEX (treatment_id) 
);

-- create table sample files
create table sample_file (
	id INT NOT NULL AUTO_INCREMENT,
	sample_id INT NOT NULL,
	file_type VARCHAR(50) NOT NULL,
	file_status VARCHAR(50) NOT NULL,
	file_path VARCHAR(300) NOT NULL,
	PRIMARY KEY(id),
	INDEX (sample_id) 
);


-- create table sequence from sequence (shoud be named read but it name is reserved in mysql)
create table sequence (
	id INT NOT NULL AUTO_INCREMENT,
	reference VARCHAR(150) NOT NULL,
	sample_id INT NOT NULL,
	contig_id INT NOT NULL,
	taxon_id INT NULL,
	taxon_score DOUBLE NULL,
	pair INT NOT NULL,
	sequence TEXT null,
	lenth INT NOT null,
	start_alignment INT NOT NULL,
	end_alignment INT NOT NULL,
	cigar VARCHAR(50) NOT NULL,
	flag_alignment INT NOT NULL,
	PRIMARY KEY(id),
	INDEX (contig_id), INDEX (taxon_id)
);

-- create table contig sequence
create table contig (
	id INT NOT NULL AUTO_INCREMENT,
	sample_id INT NOT NULL,
	reference VARCHAR(250) NOT NULL,
	sequence MEDIUMTEXT NOT null,
	size INT NOT NULL,
	number_of_reads_classified INT NOT null,
	number_of_reads INT NOT null,
	number_of_features INT NOT null,
	tii DOUBLE NOT NULL,
	PRIMARY KEY(id), 
	INDEX (sample_id), INDEX(reference), INDEX (tii)
);



-- create table contig statistic by tii
create table contig_statistic_by_tii (
	id INT NOT NULL AUTO_INCREMENT,
	sample_id INT NOT NULL,
	contig_id INT NOT NULL,
	rank VARCHAR(50) NOT NULL,
	boundary INT NOT null,
	unclassified DOUBLE NOT NULL,
	undefined DOUBLE NOT NULL,
	ictcr DOUBLE NOT NULL,
	ivct DOUBLE NOT NULL,
	PRIMARY KEY(id),
	INDEX (sample_id), INDEX(contig_id)
);


-- create table taxon on contig sequence
create table taxon_on_contig (
	id INT NOT NULL AUTO_INCREMENT,
	sample_id INT NOT NULL,
	contig_id INT NOT NULL,
	rank VARCHAR(50) NOT NULL,
	taxon_id INT NOT NULL,
	covarage DOUBLE NOT NULL,
	PRIMARY KEY(id),
	INDEX (sample_id), INDEX(contig_id), INDEX(taxon_id)
);

-- create table classified read by context
create table classified_read_by_context (
	id INT NOT NULL AUTO_INCREMENT,
	sample_id INT NOT NULL,
	contig_id INT NOT NULL,
	read_id INT NOT NULL,
	taxon_id INT NOT NULL,
	PRIMARY KEY(id), 
	INDEX (sample_id), INDEX (contig_id)
);



create table feature (
	id INT NOT NULL AUTO_INCREMENT,
	contig_id INT NOT NULL,
	feature_type  VARCHAR(20) NOT NULL,
	taxon_id INT NULL,
	start_alignment INT NOT NULL,
	end_alignment INT NOT NULL,
	strand INT NOT NULL,
	PRIMARY KEY(id),
	INDEX (contig_id) 
);

create table gene_product (
	id INT NOT NULL AUTO_INCREMENT,
	feature_id INT NOT NULL,
	product VARCHAR(500) NOT NULL,
	source  VARCHAR(100) NOT NULL,
	PRIMARY KEY(id),
	INDEX (feature_id), INDEX (product) 
);

create table system_user (
	id INT NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(300) NOT NULL,
	name VARCHAR(300) NOT NULL,
	password  VARCHAR(100) NOT NULL,
	PRIMARY KEY(id) 
);


create table philodist (
	id INT NOT NULL AUTO_INCREMENT,
	feature_id INT NOT NULL,
	gene_oid BIGINT NOT NULL,
	taxon_oid BIGINT NOT NULL,
	identity DOUBLE NOT NULL,
	lineage VARCHAR(300) NOT NULL,
	PRIMARY KEY(id),
	INDEX (feature_id) 
);

create table feature_annotation (
	id INT NOT NULL AUTO_INCREMENT,
	feature_id INT NOT NULL,
	feature_annotation_type VARCHAR(10) NOT NULL,
	name VARCHAR(50) NOT NULL,
	identity DOUBLE NOT NULL,
	align_length INT NULL, 
	query_start INT NOT NULL,
	query_end INT NOT NULL,
	subject_start INT NOT NULL,
	subject_end INT NOT NULL,
	evalue DOUBLE NOT NULL,
	bit_score DOUBLE NULL,
	PRIMARY KEY(id),
	INDEX (feature_id), INDEX (name)  
);



insert into system_user values (id, "gianlucamajor@gmail.com", "Gianluca", "gianluca1010");

insert into treatment values (id, "ZC3b", "Compostagem ZC3b");
insert into treatment values (id, "ZC4", "Compostagem ZC4");


select id from treatment where name = "ZC3b" into @treatmentId;
insert into sample values(id, @treatmentId, "ZC3b-day-01", "CREATED", "Compostagem ZC3b day 01 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-30", "CREATED", "Compostagem ZC3b day 30 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-64", "CREATED", "Compostagem ZC3b day 64 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-78", "CREATED", "Compostagem ZC3b day 78 - Miseq");
insert into sample values(id, @treatmentId, "ZC3b-day-99", "CREATED", "Compostagem ZC3b day 99 - Miseq");


select id from treatment where name = "ZC4" into @treatmentId;
insert into sample values(id, @treatmentId, "ZC4-day-00", "CREATED", "Compostagem ZC4 day 00");
insert into sample values(id, @treatmentId, "ZC4-day-01", "CREATED", "Compostagem ZC4 day 01");
insert into sample values(id, @treatmentId, "ZC4-day-03", "CREATED", "Compostagem ZC4 day 03");
insert into sample values(id, @treatmentId, "ZC4-day-07", "CREATED", "Compostagem ZC4 day 07");
insert into sample values(id, @treatmentId, "ZC4-day-15", "CREATED", "Compostagem ZC4 day 15");
insert into sample values(id, @treatmentId, "ZC4-day-30", "CREATED", "Compostagem ZC4 day 30");
insert into sample values(id, @treatmentId, "ZC4-day-64", "CREATED", "Compostagem ZC4 day 64");
insert into sample values(id, @treatmentId, "ZC4-day-67", "CREATED", "Compostagem ZC4 day 67");
insert into sample values(id, @treatmentId, "ZC4-day-78", "CREATED", "Compostagem ZC4 day 78");
insert into sample values(id, @treatmentId, "ZC4-day-99", "CREATED", "Compostagem ZC4 day 99");


-- ZC3 B SAMPLES FILES

select id from sample where name = "ZC3b-day-01" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc3b-day-01.json");

select id from sample where name = "ZC3b-day-30" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc3b-day-30.json");

select id from sample where name = "ZC3b-day-64" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc3b-day-64.json");

select id from sample where name = "ZC3b-day-78" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc3b-day-78.json");

select id from sample where name = "ZC3b-day-99" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc3b-day-99.json");


-- ZC4 SAMPLES FILES

select id from sample where name = "ZC4-day-00" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-00.json");

select id from sample where name = "ZC4-day-01" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-01.json");

select id from sample where name = "ZC4-day-03" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-03.json");

select id from sample where name = "ZC4-day-07" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-07.json");

select id from sample where name = "ZC4-day-15" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-15.json");

select id from sample where name = "ZC4-day-30" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-30.json");

select id from sample where name = "ZC4-day-64" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-64.json");

select id from sample where name = "ZC4-day-67" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-67.json");

select id from sample where name = "ZC4-day-78" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-78.json");

select id from sample where name = "ZC4-day-99" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/caravela/zc4-day-99.json");


-- sample from deyvid
select id from treatment where name = "ZC4" into @treatmentId;
insert into sample values(id, @treatmentId, "ZC4-day-01-deyvid", "CREATED", "Compostagem ZC4 day 01 - from deyvid");

select id from sample where name = "ZC4-day-01-deyvid" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/pier/zc4-day-01-deyvid-caravela.json");

-- sample to taxonomer and mira

select id from treatment where name = "ZC4" into @treatmentId;
insert into sample values(id, @treatmentId, "ZC4-day-01-taxonomer-deyvid", "CREATED", "Compostagem ZC4 day 01 - taxonomer - from deyvid");

select id from sample where name = "ZC4-day-01-taxonomer-deyvid" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/pier/zc4-day-01-deyvid-taxonomer-caravela.json");

-- sample to taxonomer and soap 

select id from treatment where name = "ZC4" into @treatmentId;
insert into sample values(id, @treatmentId, "ZC4-day-01-taxonomer-soap", "CREATED", "Compostagem ZC4 day 01 - taxonomer - from soap");

select id from sample where name = "ZC4-day-01-taxonomer-soap" into @sampleId;
insert into sample_file values(id, @sampleId, "ALL_JSON", "UPLOADED", "/data/pier/zc4-day-01-soap-taxonomer-caravela.json");
