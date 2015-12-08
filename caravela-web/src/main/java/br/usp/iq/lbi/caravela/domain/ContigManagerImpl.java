package br.usp.iq.lbi.caravela.domain;

import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Feature;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;
import lbi.usp.br.caravela.dto.ContigConverter;
import lbi.usp.br.caravela.dto.ContigTO;

@RequestScoped
public class ContigManagerImpl implements ContigManager {
	
	@Inject private ContigDAO contigDAO;
	@Inject private FeatureDAO featureDAO;
	@Inject private ReadDAO readDAO;
	@Inject private ReadManager readManager;
	
	
	public List<Read> searchReadOnContigByContigId(Long contigId){
		Contig contig = contigDAO.load(contigId);
		
		List<Read> reads = readDAO.loadAllReadsByContig(contig);
		for (Read read : reads) {
			if(read.hasTaxon()){
				HashMap<String, Taxon> lineagem = readManager.loadLineagem(read);
				read.setLineagem(lineagem);
			}
		}
		return reads;
	}
	
	
	public ContigTO searchContigById(Long contigId){
		Contig contig = contigDAO.load(contigId);
		List<Feature> features = featureDAO.loadAllFeaturesByContig(contig);
		
		List<Read> reads = readDAO.loadAllReadsByContig(contig);
		
		for (Read read : reads) {
			if(read.hasTaxon()){
				HashMap<String, Taxon> lineagem = readManager.loadLineagem(read);
				read.setLineagem(lineagem);
			}
		}
		
		ContigConverter contigConverter = new ContigConverter();
		ContigTO contigTO = contigConverter.toContigTO(contig, features, reads);
				
		return contigTO;
	}
	
}
