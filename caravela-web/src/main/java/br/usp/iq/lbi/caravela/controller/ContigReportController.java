package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.DownloadBuilder;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.observer.download.ZipDownload;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

@Controller
public class ContigReportController {
	
	private static final String TAB = "\t";
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	
	protected ContigReportController(){
		this(null, null, null, null);
	}
	
	@Inject
	public ContigReportController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO){
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
	}
	

	@Get
	@Path("/contig/report/{sampleId}")
	public Download report(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		Double tiiGreaterOrEqualsThan = 0.5;
		Integer numberOfFeaturesGreaterOrEqualsThan = 0;
		Integer numberOfBoundariesLessOrEqualsThan = 0;
		Double indexOfConsistencyTaxonomicByCountReadsGreaterOrEqualsThan = 0.40;
		Double indexOfVerticalConsistencyTaxonomicGreaterOrEqualsThan = 0.70;
		TaxonomicRank species = TaxonomicRank.SPECIES;
		TaxonomicRank genus = TaxonomicRank.GENUS;
		TaxonomicRank family = TaxonomicRank.FAMILY;
				
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, tiiGreaterOrEqualsThan, numberOfFeaturesGreaterOrEqualsThan, genus, numberOfBoundariesLessOrEqualsThan, indexOfConsistencyTaxonomicByCountReadsGreaterOrEqualsThan, indexOfVerticalConsistencyTaxonomicGreaterOrEqualsThan);
		
		File file = new File("/tmp/ContigsTaxonomicallyGoodResolved.tsv");
		
		DecimalFormat decimal = new DecimalFormat("#.###");
		
		FileDownload download = null;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeders());
			fw.write(System.lineSeparator());
			for (Contig contig : contigList) {
				StringBuilder line = null;
				line = new StringBuilder();
				line.append(contig.getReference())
				.append(TAB)
				.append(getICTCR(decimal, contig, species)).append(TAB).append(getIVCT(decimal, contig, species)).append(TAB).append(getBorders(contig, species))
				.append(TAB)
				.append(getICTCR(decimal, contig, genus)).append(TAB).append(getIVCT(decimal, contig, genus)).append(TAB).append(getBorders(contig, genus))
				.append(TAB)
				.append(getICTCR(decimal, contig, family)).append(TAB).append(getIVCT(decimal, contig, family)).append(TAB).append(getBorders(contig, family))
				.append(TAB)
				.append(decimal.format(contig.getTaxonomicIdentificationIndex()))
				.append(TAB)
				.append(contig.getNumberOfReads())
				.append(TAB)
				.append(contig.getNumberOfReadsClassified())
				.append(TAB)
				.append(contig.getNumberOfFeatures())
				
				.append(System.lineSeparator());
				fw.write(line.toString());
			}
			fw.close();
			
			download = DownloadBuilder.of(file)
				    .withContentType("text/plain")
				    .downloadable()
				    .build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return download;
	}
	
	private String createHeders(){
		return new StringBuilder().append("REFERENCE").append(TAB)
		.append("ICTCR|SP").append(TAB)
		.append("IVCT|SP").append(TAB)
		.append("BORDERS|SP").append(TAB)
		.append("ICTCR|GE").append(TAB)
		.append("IVCT|GE").append(TAB)
		.append("BORDERS|GE").append(TAB)
		.append("ICTCR|FA").append(TAB)
		.append("IVCT|FA").append(TAB)
		.append("BORDERS|FA").append(TAB)
		.append("GTII").append(TAB)
		.append("NR").append(TAB)
		.append("NRC").append(TAB)
		.append("FEATURES").toString();							
	}

	private String getIVCT(DecimalFormat decimal, Contig contig, TaxonomicRank rank) {
		return decimal.format(contig.getIndexOfVerticalConsistencyTaxonomic(rank));
	}

	private String getBorders(Contig contig, TaxonomicRank rank) {
		return contig.getNumberOfBorders(rank).toString();
	}

	private String getICTCR(DecimalFormat decimal, Contig contig, TaxonomicRank rank) {
		return decimal.format(contig.getIndexOfConsistencyTaxonomicByCountReads(rank));
	}

}
