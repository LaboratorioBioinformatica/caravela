package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

public interface SampleReporter {
	
	void reportChimericPotentialFromContig(Sample sample, Double tii, String rank);
	void createReportByContig(Sample sample, String rank, Contig contig);
	List<ClassifiedReadByContex> classifiedReadsByContex(Sample sample, String rank, Contig contig);

}
