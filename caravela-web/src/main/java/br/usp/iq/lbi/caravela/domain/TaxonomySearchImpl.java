package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;

@RequestScoped
public class TaxonomySearchImpl implements TaxonomySearch {
	
	@Inject private ReadDAO readDAO;
	
	
	public List<Contig> searchListOfContigBySampleAndScientificName(Sample sample, String scientificName){
		List<Read> readsFromSampleAndScientificName = readDAO.findReadsBySampleAndScientificName(sample, scientificName);
		
		List<Contig> contigList = new ArrayList<Contig>();
		for (Read read : readsFromSampleAndScientificName) {
			contigList.add(read.getContig());
		}
		return contigList;
	}
	

}
