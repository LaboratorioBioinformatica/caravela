
alter table report_contig add column sample_id INT after id;
update report_contig rc set sample_id = (select rs.sample_id from report_sample rs where rc.reportSample_id = rs.id);
alter table report_contig drop column reportSample_id;
alter table report_contig modify column sample_id INT NOT NULL;
CREATE INDEX sample_id ON report_contig (sample_id);
rename table report_contig to contig_statistic_by_tii;



alter table report_taxon_on_contig add column sample_id INT after id;
update report_taxon_on_contig rtoc set sample_id = (select rs.sample_id from report_sample rs where rtoc.reportSample_id = rs.id);
alter table report_taxon_on_contig drop column reportSample_id;
alter table report_taxon_on_contig modify column sample_id INT NOT NULL;
CREATE INDEX sample_id ON report_taxon_on_contig (sample_id);
rename table report_taxon_on_contig to taxon_on_contig;


alter table report_classified_read_by_context add column sample_id INT after id;
update report_classified_read_by_context rcrbc set sample_id = (select rs.sample_id from report_sample rs where rcrbc.reportSample_id = rs.id);
alter table report_classified_read_by_context drop column reportSample_id;
alter table report_classified_read_by_context modify column sample_id INT NOT NULL;
CREATE INDEX sample_id ON report_classified_read_by_context (sample_id);
rename table report_classified_read_by_context to classified_read_by_context;


alter table sequence add column cigar VARCHAR(50) after end_alignment;
alter table contig modify column sequence MEDIUMTEXT NOT NULL;


drop table report_sample;



-- create index 
CREATE INDEX sample_id ON contig (sample_id);
CREATE INDEX size ON contig (size);
CREATE INDEX tii ON contig (tii);

CREATE INDEX sample_id ON report_sample (sample_id);
CREATE INDEX reportSample_id ON report_contig (reportSample_id);
CREATE INDEX contig_id ON report_contig (contig_id);

CREATE INDEX reportSample_id ON report_taxon_on_contig (reportSample_id);
CREATE INDEX contig_id ON report_taxon_on_contig (contig_id);
CREATE INDEX taxon_id ON report_taxon_on_contig (taxon_id);

INDEX (reportSample_id), INDEX(contig_id), INDEX(taxon_id)



