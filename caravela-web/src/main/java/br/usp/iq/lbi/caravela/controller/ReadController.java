package br.usp.iq.lbi.caravela.controller;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.sun.org.glassfish.external.arc.Taxonomy;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.dto.ReadOnContigTO;
import br.usp.iq.lbi.caravela.dto.TaxonTO;

@Controller
public class ReadController {
	
	private final Result result;
	private final WebUser webUser;
	private final ReadDAO readDAO;
	
	public ReadController() {
		this(null, null, null);
		
	}
	
	@Inject
	public ReadController(Result result, WebUser webUser, ReadDAO readDAO) {
		this.result = result;
		this.webUser = webUser;
		this.readDAO = readDAO;
	}
	
	@Get
	@Path("/read/{readId}")
	public void searchRead(Long readId){
		
		try {
			Read read = readDAO.load(readId);
			TaxonTO taxonTO = null;
			
			if(read.hasTaxon()){
				Taxon taxon = read.getTaxon();
				taxonTO = new TaxonTO.Builder().setScientificName(taxon.getScientificName()).setTaxonomyId(taxon.getTaxonomyId()).setHank(taxon.getRank()).build();
			}
			
			ReadOnContigTO readOnContigTO = new ReadOnContigTO(read.getReference(), read.getSequence(), read.getStartAlignment(), read.getEndAlignment(), read.getCigar(), read.getFlagAlignment(), read.getPair(), taxonTO);
			result.use(Results.json()).withoutRoot().from(readOnContigTO).include("taxon").serialize();
			
		} catch (NoResultException nre) {
			result.use(Results.json());
		}
		
		
	}

}
