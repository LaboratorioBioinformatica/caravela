-- to enable create file from mysql to any directory (/tmp/ for example) is needs disable secure_file_priv on mysql.
-- To do it: on /etc/mysql/mysql.conf.d/mysqld.cnf add secure_file_priv="" and estart mysql.

DELIMITER //
    CREATE PROCEDURE reportTaxonClassifiedByContext(IN sampleId INTEGER, IN rankValue VARCHAR(30))
        BEGIN
            select c.id as contig_id,
            c.sample_id as sample_id,
            c.reference as contig_reference,
            c.size as contig_size,
            c.number_of_reads_classified as number_of_reads_classified,
            c.number_of_reads as number_of_reads,
            c.number_of_features as number_of_features,
            c.tii as ITG,
            csbt.ictcr as CT,
            csbt.ivct as CTV,
            csbt.boundary as boundary,
            s.reference as read_reference,
            rc.taxon_id as taxon_id,
            t.rank as ncbi_taxonomy_rank,
            t.taxonomy_id as ncbi_taxonomy_id,
            t.scientific_name as ncbi_scientific_name
            from
            contig c,
            classified_read_by_context rc,
            contig_statistic_by_tii csbt,
            taxon t,
            sequence s
            where
            c.sample_id = sampleId
            and c.id=csbt.contig_id
            and csbt.rank=rankValue
            and c.id=rc.contig_id
            and t.id = rc.taxon_id
            and rc.read_id = s.id;
    END//
DELIMITER ;
