package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

public interface ContigDAO extends DAO<Contig> {
	List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonCovarageDesc(Sample sample, Long taxonomyId, Double taxonCovarage);
	List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonExclusiveCovarageDesc(Sample sample, Long taxonomyId, Double taxonCoverage);
	Long CountByContigBySampleAndTiiGreatherThan(Sample sample, Double tii);
	List<Contig> FindByContigListTBRBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic, Integer firstResult, Integer maxResult);
	List<Contig> FindByContigBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic);
	List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer maxResult);
	List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer startPosition, Integer maxResult);
	Contig findContigBySampleAndContigReference(Sample sample, String contigReference);
	List<Contig> FindContigBySampleOrderByContigSizeAndITGDesc(Sample sample);
	
	List<Contig> FindByContigListPQBySample(Sample sample, Double tii, Integer numberOfFeatures, TaxonomicRank taxonomicRank, Integer numberOfBoundaries, Double indexOfConsistencyTaxonomicByCountReads, Double indexOfVerticalConsistencyTaxonomic, Integer firstResult, Integer maxResult);
	

}
