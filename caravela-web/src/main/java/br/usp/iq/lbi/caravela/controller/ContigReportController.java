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
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonOnContig;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

@Controller
public class ContigReportController {
	
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private static final String TAB = "\t";
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ContigDAO contigDAO;
	
	
	private Double TII_GREATER_OR_EQUALS_THAN = 0.5;
	private Integer NUMBER_OF_FEATURES_GREATER_OR_EQUALS_THAN = 0;
	private Integer NUMBER_OF_BOUNDARIES_LESS_OR_EQUALS_THAN = 0;
	private Double INDEX_OF_CONSISTENCY_TAXONOMIC_BY_COUNT_READS_GREATER_OR_EQUALS_THAN = 0.40;
	private Double INDEX_OF_VERTICAL_CONSISTENCY_TAXONOMIC_GREATER_OR_EQUALS_THAN = 0.70;

	private TaxonomicRank SPECIES = TaxonomicRank.SPECIES;
	private TaxonomicRank GENUS = TaxonomicRank.GENUS;
	private TaxonomicRank FAMILY = TaxonomicRank.FAMILY;
	
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
	@Path("/contig/taxonOnContig/report/{sampleId}")
	public Download taxonOnContigReport(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, TII_GREATER_OR_EQUALS_THAN, NUMBER_OF_FEATURES_GREATER_OR_EQUALS_THAN, GENUS, NUMBER_OF_BOUNDARIES_LESS_OR_EQUALS_THAN, INDEX_OF_CONSISTENCY_TAXONOMIC_BY_COUNT_READS_GREATER_OR_EQUALS_THAN, INDEX_OF_VERTICAL_CONSISTENCY_TAXONOMIC_GREATER_OR_EQUALS_THAN);
		
		File file = new File("/tmp/TaxaOnContigFromContigsTaxonomicallyGoodResolved.tsv");
		
		DecimalFormat decimal = new DecimalFormat("#.###");
		
		Download download = null;
		
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderTaxaOnContigFromContigsTaxonomicallyGoodResolved());
			fw.write(LINE_SEPARATOR);
			
			for (Contig contig : contigList) {
				List<TaxonOnContig> allTaxaOnContig = contig.getAllTaxaOnContig();
				for (TaxonOnContig taxonOnContig : allTaxaOnContig) {
					StringBuilder line = new StringBuilder();
					line.append(contig.getReference()).append(TAB)
					.append(taxonOnContig.getNumberOfReads()).append(TAB)
					.append(decimal.format(taxonOnContig.getCoverage())).append(TAB)
					.append(taxonOnContig.getRank()).append(TAB)
					.append(taxonOnContig.getTaxon().getScientificName()).append(TAB)
					.append(LINE_SEPARATOR);
					fw.write(line.toString());
				}
			}
			
			fw.close();
			
			download = DownloadBuilder.of(file)
				    .withContentType("text/plain")
				    .downloadable()
				    .build();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return download;
		
	}

	@Get
	@Path("/contig/report/{sampleId}")
	public Download report(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		
				
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, TII_GREATER_OR_EQUALS_THAN, NUMBER_OF_FEATURES_GREATER_OR_EQUALS_THAN, GENUS, NUMBER_OF_BOUNDARIES_LESS_OR_EQUALS_THAN, INDEX_OF_CONSISTENCY_TAXONOMIC_BY_COUNT_READS_GREATER_OR_EQUALS_THAN, INDEX_OF_VERTICAL_CONSISTENCY_TAXONOMIC_GREATER_OR_EQUALS_THAN);
		
		File file = new File("/tmp/ContigsTaxonomicallyGoodResolved.tsv");
		
		DecimalFormat decimal = new DecimalFormat("#.###");
		
		FileDownload download = null;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderToContigsTaxonomicallyGoodResolved());
			fw.write(LINE_SEPARATOR);
			for (Contig contig : contigList) {
				StringBuilder line = new StringBuilder();
				line.append(contig.getReference())
				.append(TAB)
				.append(contig.getSize())
				.append(TAB)
				.append(getICTCR(decimal, contig, SPECIES)).append(TAB).append(getIVCT(decimal, contig, SPECIES)).append(TAB).append(getBorders(contig, SPECIES))
				.append(TAB)
				.append(getICTCR(decimal, contig, GENUS)).append(TAB).append(getIVCT(decimal, contig, GENUS)).append(TAB).append(getBorders(contig, GENUS))
				.append(TAB)
				.append(getICTCR(decimal, contig, FAMILY)).append(TAB).append(getIVCT(decimal, contig, FAMILY)).append(TAB).append(getBorders(contig, FAMILY))
				.append(TAB)
				.append(decimal.format(contig.getTaxonomicIdentificationIndex()))
				.append(TAB)
				.append(contig.getNumberOfReads())
				.append(TAB)
				.append(contig.getNumberOfReadsClassified())
				.append(TAB)
				.append(contig.getNumberOfFeatures())
				
				.append(LINE_SEPARATOR);
				fw.write(line.toString());
			}
			fw.close();
			
			download = DownloadBuilder.of(file)
				    .withContentType("text/plain")
				    .downloadable()
				    .build();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return download;
	}
	
	private String createHeaderToContigsTaxonomicallyGoodResolved(){
		return new StringBuilder().append("REFERENCE").append(TAB)
		.append("SIZE").append(TAB)
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
	
	private String createHeaderTaxaOnContigFromContigsTaxonomicallyGoodResolved(){
		return new StringBuilder().append("REFERENCE").append(TAB)
		.append("NUMBER OF READS").append(TAB)
		.append("COVERAGE").append(TAB)
		.append("RANK").append(TAB)
		.append("SCIENTIFIC NAME").append(TAB)
		.toString();							
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
