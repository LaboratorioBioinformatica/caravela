package br.usp.iq.lbi.caravela.domain;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Mapping;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonomicAssignment;
import lbi.usp.br.caravela.dto.ContigTO;
import lbi.usp.br.caravela.dto.FeatureTO;
import lbi.usp.br.caravela.dto.GeneProductTO;
import lbi.usp.br.caravela.dto.ReadOnContigTO;
import lbi.usp.br.caravela.dto.TaxonTO;

@RequestScoped
public class ContigTOProcessorImpl implements ContigTOProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(ContigTOProcessorImpl.class);
	
	@Inject private ContigDAO contigDAO;
	@Inject private FeatureDAO featureDAO;
	@Inject private ReadDAO readDAO;
	@Inject private NCBITaxonManager ncbiTaxonManager;
	

	public Contig convert(Sample sample, ContigTO contigTO) {
		
		Contig contig = new Contig(sample, contigTO.getReference(), contigTO.getSequence(), 
				contigTO.getNumberOfreads(), contigTO.getNumberOfReadsClassified(), contigTO.getNumberOfFeatures(), 
				contigTO.getTaxonomicIdentificationIndex());
		
		contigDAO.save(contig);
		
		List<Feature> features = createFeatures(contig, contigTO.getFeatures());
		
		if( ! features.isEmpty()){
			Integer batchSize = features.size();
			System.out.println("number of features:" + batchSize);
			for (Feature feature : features) {
				featureDAO.addBatch(feature, batchSize );
			}
		}
		
		List<ReadOnContigTO> readsOnCotig = contigTO.getReadsOnCotig();
		createAndSaveReadsAndTaxons(sample, contig, readsOnCotig);
		
		return contig;

	}

	public void  createAndSaveReadsAndTaxons(Sample sample, Contig contig, List<ReadOnContigTO> readsOnCotig ){

		List<Read> reads = new ArrayList<Read>();
		
		if(readsOnCotig != null && !readsOnCotig.isEmpty()){
			
//			Map<Long, Taxon> allTaxon = ncbiTaxonManager.SearchAllTaxon();
			
			for (ReadOnContigTO readOnContigTO : readsOnCotig) {
				Mapping mapping = new Mapping(readOnContigTO.getStartAlignment(), readOnContigTO.getEndAlignment(), readOnContigTO.getFlag());
				Read read = new Read(readOnContigTO.getReference(), sample, contig, readOnContigTO.getSequence(), readOnContigTO.getPair(), mapping);
				reads.add(read);
				
				TaxonTO taxonTO = readOnContigTO.getTaxon();
				if(taxonTO != null){
					Long taxonomyId = taxonTO.getTaxonomyId(); 
					
					//TODO ISTO DEVERIA ESTAR EM MEMÓRIA!
					Taxon taxon = ncbiTaxonManager.searchByTaxonomicId(taxonomyId);
					
					if(taxon != null){
						TaxonomicAssignment taxonomicAssignment = new TaxonomicAssignment(taxon, new Double(taxonTO.getScore()));
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
		
	}

	public List<Feature> createFeatures(Contig contig, List<FeatureTO> featureTOList) {
		
		List<Feature> featureList = new ArrayList<Feature>();
		
		if(featureTOList != null && ! featureTOList.isEmpty()) {
			
			for (FeatureTO featureTO : featureTOList) {
				String productName = null;
				String productSource = null;
				
				if(featureTO.hasGeneProduct()){
					 GeneProductTO geneProduct = featureTO.getGeneProduct();
					 productName = geneProduct.getProduct();
					 productSource = geneProduct.getSource();
				}
				
				Feature feature = new Feature(contig, featureTO.getSource(), featureTO.getType(), featureTO.getStart(), 
						featureTO.getEnd(), featureTO.getStrand(), productName, productSource);
				featureList.add(feature);
			}
		}
		
		return featureList;
	}

}
