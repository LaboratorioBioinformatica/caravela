package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

public interface ContigDAO extends DAO<Contig> {
	List<Contig> findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonCovarageDesc(Sample sample, Long taxonomyId, Double taxonCovarage);
	Long CountByContigBySampleAndTiiGreatherThan(Sample sample, Double tii);
	List<Contig> FindByContigBySample(Sample sample, Double tii, Integer numberOfFeatures, Integer numberOfBoundaries, Double unclassified, Double undefined, Integer firstResult, Integer maxResult);
	List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer maxResult);
	List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer startPosition, Integer maxResult);

}
