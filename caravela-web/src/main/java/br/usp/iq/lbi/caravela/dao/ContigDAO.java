package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

public interface ContigDAO extends DAO<Contig> {
	List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonCovarageDesc(Sample sample, Long taxonomyId, Double taxonCovarage);
	List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonExclusiveCovarageDesc(Sample sample, Long taxonomyId, Double taxonCoverage);
	Long countByContigBySampleAndTiiGreatherThan(Sample sample, Double tii);
	List<Contig> findByContigListTBRBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic, Integer firstResult, Integer maxResult);
	List<Contig> FindByContigBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic);
	List<Contig> findByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer startPosition, Integer maxResult);
	Contig findContigBySampleAndContigReference(Sample sample, String contigReference);
	List<Contig> findAllContigsBySample(Sample sample, Integer start, Integer max);
	Long countAllContigsBySample(Sample sample);
	
	List<Contig> findByContigListPQBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic, Integer firstResult, Integer maxResult);
	

}
