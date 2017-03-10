package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.dto.ReadOnContigTO;
import br.usp.iq.lbi.caravela.dto.TaxonTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Mapping;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonomicAssignment;

@RequestScoped
public class ContigTOProcessorImpl implements ContigTOProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(ContigTOProcessorImpl.class);
	
	@Inject private ContigDAO contigDAO;
	@Inject private ReadDAO readDAO;
	@Inject private FeatureCreator featureCreator;
	@Inject private NCBITaxonFinder ncbiTaxonFinder;
	

	public Contig convert(Sample sample, ContigTO contigTO) {
		
		Contig contig = new Contig(sample, contigTO.getReference(), contigTO.getSequence(), contigTO.getSize(), 
				contigTO.getNumberOfreads(), contigTO.getNumberOfReadsClassified(), contigTO.getNumberOfFeatures(), 
				contigTO.getTaxonomicIdentificationIndex());
		
		logger.info("Contig created: " + contig.getReference());
		
		contigDAO.save(contig);
		
		logger.info("Contig saved: " + contig.getReference());
		
		featureCreator.createList(contig, contigTO.getFeatures());
		
		List<ReadOnContigTO> readsOnCotig = contigTO.getReadsOnCotig();
		List<Read> readsOnContigSaved = createAndSaveReadsAndTaxons(sample, contig, readsOnCotig);
		contig.addReads(readsOnContigSaved);
		return contig;

	}

	public List<Read> createAndSaveReadsAndTaxons(Sample sample, Contig contig, List<ReadOnContigTO> readsOnCotig ){

		List<Read> reads = new ArrayList<Read>();
		
		if(readsOnCotig != null && !readsOnCotig.isEmpty()){
			
			
			for (ReadOnContigTO readOnContigTO : readsOnCotig) {
				Mapping mapping = new Mapping(readOnContigTO.getStartAlignment(), readOnContigTO.getEndAlignment(), readOnContigTO.getCigar(), readOnContigTO.getFlag());
				Read read = new Read(readOnContigTO.getReference(), sample, contig, readOnContigTO.getSequence(), readOnContigTO.getSequenceLenth(), readOnContigTO.getPair(), mapping);
				reads.add(read);
				
				TaxonTO taxonTO = readOnContigTO.getTaxon();
				if(taxonTO != null){
					Long taxonomyId = taxonTO.getTaxonomyId(); 
					
					//TODO ISTO DEVE ESTAR EM MEMÓRIA!
					Taxon taxon = ncbiTaxonFinder.searchTaxonByNCBITaxonomyId(taxonomyId);
					
					if(taxon != null){
						TaxonomicAssignment taxonomicAssignment = new TaxonomicAssignment(taxon, taxonTO.getScore());
						read.toAssigTaxon(taxonomicAssignment);
						
					} else {
						logger.warn("Taxonomy id: " + taxonomyId + " not found!");
					}
					
				}
				
			}
			
		}
		
		if( ! reads.isEmpty()) {
			Integer numberOfReadsToSave = reads.size();
			for (Read read : reads) {
				readDAO.addBatch(read, numberOfReadsToSave);
			}
			
			
		}
		return reads;
		
	}

}
