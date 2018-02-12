package br.usp.iq.lbi.caravela.dao;

import java.util.List;

import br.usp.iq.lbi.caravela.dto.report.TaxonomicReportTO;
import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.Sample;

public interface ClassifiedReadByContextDAO extends DAO<ClassifiedReadByContex> {
	Integer removeBySample(Long sampleId);
	List<ClassifiedReadByContex> findBySample(Sample sample);
	List<TaxonomicReportTO> findTaxonomicReportBySample(Sample sample);

}
