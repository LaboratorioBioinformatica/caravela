package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import lbi.usp.br.caravela.dto.GeneProductTO;
import lbi.usp.br.caravela.dto.search.GeneProductCounterTO;
import lbi.usp.br.caravela.dto.search.TaxonCounterTO;
import lbi.usp.br.caravela.dto.search.TaxonomomySearchTO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.dao.TaxonDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;

@RequestScoped
public class TaxonomySearchImpl implements TaxonomySearch {
	
	@Inject private ReadDAO readDAO;
	@Inject private TaxonDAO taxonDAO;
	
	
	public TaxonomomySearchTO searchTaxonomicSearchTOBySampleAndScientificName(Sample sample, String scientificName){
		List<Read> readsFromSampleAndScientificName = readDAO.findReadsBySampleAndScientificName(sample, scientificName);
		Long totalNumberOfTaxonFound = taxonDAO.count(sample, scientificName);
		LinkedHashSet<Contig> ContigLinkedHashSet = new LinkedHashSet<Contig>();
		
		Hashtable<String, GeneProductCounterTO> geneProductCounterTOHashTable = new Hashtable<String, GeneProductCounterTO>();
		
		for (Read read : readsFromSampleAndScientificName) {
			Contig contig = read.getContig();
			createGeneProductCounterTOHashMap(contig, geneProductCounterTOHashTable);
			ContigLinkedHashSet.add(contig);
		}
		

		
		List<GeneProductCounterTO> geneProductCounterTOList = new ArrayList<GeneProductCounterTO>(geneProductCounterTOHashTable.values());
		Collections.sort(geneProductCounterTOList);
		
		return new TaxonomomySearchTO(ContigLinkedHashSet, geneProductCounterTOList, totalNumberOfTaxonFound);
		
	}
	
	public List<TaxonCounterTO> searchTaxonCounterTOBySampleAndScientificName(Sample sample, String scientificName) {
		List<TaxonCounterTO> taxonCounterTO = taxonDAO.findTaxonsBySampleAndScientificName(sample, scientificName);
		return taxonCounterTO;
	}


	private void createGeneProductCounterTOHashMap(Contig contig, Hashtable<String,GeneProductCounterTO> geneProductCounterTOHashTable) {
		
		List<Feature> features = contig.getFeatures();
		for (Feature feature : features) {
			String productSource = feature.getProductSource();
			if(productSource != null){
				
				GeneProductTO geneProduct = new GeneProductTO(feature.getProductName(), productSource);
				GeneProductCounterTO geneProductCounterTO = geneProductCounterTOHashTable.get(productSource);
				
				if(geneProductCounterTO == null){
					geneProductCounterTOHashTable.put(productSource, new GeneProductCounterTO(geneProduct));
				} else {
					geneProductCounterTO.addOne();
				}
			}
		}
	}


	
	

}
