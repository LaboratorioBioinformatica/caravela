 DELIMITER //
 CREATE PROCEDURE getTotalOfContigsFromAllSamples()
   BEGIN
	   select s.name as sample_name, count(c.id) as total_of_contig from contig c, sample s where s.id = c.sample_id  group by sample_id;   
   END //
 DELIMITER ;
 
 
 DELIMITER //
 CREATE PROCEDURE getContigsTBRToAllSamples(IN rank VARCHAR(30))
   BEGIN
	   select s.name as sample_name, count(c.id) as total_of_contig_tbr from contig c, contig_statistic_by_tii cs, sample s where c.id = cs.contig_id and c.sample_id = s.id and c.size >= 500 and cs.rank = rank and c.tii >= 0.5 and cs.ictcr >=0.4 and cs.ivct >= 0.7 and cs.boundary =0 group by c.sample_id;   
   END //
 DELIMITER ;
 
 DELIMITER //
 CREATE PROCEDURE getContigsPQToAllSamples(IN rank VARCHAR(30))
   BEGIN
	   select s.name as sample_name, count(c.id) as total_of_contig_pq from contig c, contig_statistic_by_tii cs, sample s where c.id = cs.contig_id and c.sample_id = s.id and c.size >= 500 and cs.rank = rank and c.tii >= 0.5 and cs.ictcr <=0.2 and cs.ivct <= 0.4 and cs.boundary >=1 group by c.sample_id;   
   END //
 DELIMITER ;
 
 
   select s.name as sample_name, max(c.size) from contig c, contig_statistic_by_tii cs, sample s where c.id = cs.contig_id and c.sample_id = s.id and c.size >= 500 and cs.rank = "GENUS" and c.tii >= 0.5 and cs.ictcr >=0.4 and cs.ivct >= 0.7 and cs.boundary =0 group by c.sample_id;
   
   
   select s.name as sample_name, max(c.size) from contig c, contig_statistic_by_tii cs, sample s where c.id = cs.contig_id and c.sample_id = s.id and c.size >= 500 and cs.rank = "GENUS" and c.tii >= 0.5 and cs.ictcr <=0.2 and cs.ivct <= 0.4 and cs.boundary >=1 group by c.sample_id;