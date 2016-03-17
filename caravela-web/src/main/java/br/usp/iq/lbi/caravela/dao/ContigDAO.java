package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

public interface ContigDAO extends DAO<Contig> {
	Long CountByContigBySampleAndTiiGreatherThan(Sample sample, Double tii);
	List<Contig> FindByContigBySample(Sample sample, Integer maxResult);
	List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer maxResult);
	List<Contig> FindByContigBySampleAndTiiGreatherThan(Sample sample, Double tii, Integer startPosition, Integer maxResult);

}
