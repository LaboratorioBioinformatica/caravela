package br.usp.iq.lbi.caravela.domain;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.dto.search.TaxonCounterTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;

@RequestScoped
public class TaxonomySearchImpl implements TaxonomySearch {
	
	
	@Inject private TaxonDAO taxonDAO;
	@Inject private ContigDAO contigDao;
	
	public List<Contig> SearchContigBySampleTaxonomyIdAndTaxonCovarage(Sample sample, Long taxonomyId, Double taxonCoverage){
		return contigDao.findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonCovarageDesc(sample, taxonomyId, taxonCoverage);
	}
	
	public List<Contig> SearchContigBySampleTaxonomyIdAndExclusiveTaxonCovarage(Sample sample, Long taxonomyId, Double taxonCoverage){
		return contigDao.findContigBySampleAndTaxonomyIdAndTaxonCovarageOrderByTaxonExclusiveCovarageDesc(sample, taxonomyId, taxonCoverage);
	}
	
	
	public List<TaxonCounterTO> searchTaxonCounterTOBySampleAndScientificName(Sample sample, String scientificName, Double taxonCoverage) {
		List<TaxonCounterTO> taxonCounterTO = taxonDAO.findTaxonsBySampleAndScientificName(sample, scientificName, taxonCoverage);
		return taxonCounterTO;
	}
	
	public List<TaxonCounterTO> searchTaxonCounterTOBySampleScientificNameAndExclusiveTaxonCoverage(Sample sample, String scientificName, Double taxonCoverage) {
		List<TaxonCounterTO> taxonCounterTO = taxonDAO.findTaxonsBySampleAndScientificNameAndExclusiveCoverage(sample, scientificName, taxonCoverage);
		return taxonCounterTO;
	}


}
