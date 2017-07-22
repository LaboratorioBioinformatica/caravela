package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.DownloadBuilder;
import br.com.caelum.vraptor.observer.download.FileDownload;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dto.ContigFilterParametersTO;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonOnContig;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

@Controller
public class ContigReportController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContigReportController.class);

	private static final String LINE_SEPARATOR = System.lineSeparator();
	private static final String TAB = "\t";
	private final Result result;
	private final Validator validator;
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

	protected ContigReportController() {
		this(null, null, null, null, null);
	}

	@Inject
	public ContigReportController(Result result, WebUser webUser, SampleDAO sampleDAO, ContigDAO contigDAO, Validator validator) {
		this.result = result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.contigDAO = contigDAO;
		this.validator = validator;
	}

	@Get
	@Path("/report/taxonOnContig/by/contig/{contigId}")
	public Download taxonOnContigReportByContigId(Long contigId) {
		
		logger.info("Report Taxa On Contig to contig id: " + contigId);
		
		Contig contig = contigDAO.load(contigId);
		contig.getAllTaxaOnContig();
		DecimalFormat decimal = new DecimalFormat("#.###");
		Download download = null;
		
		StringBuffer fileNameStringBuffer = new StringBuffer();
		fileNameStringBuffer.append("/tmp/").append("Report_TaxaOnContig")
			.append("_").append(contig.getReference()).append(".tsv");
		
		logger.debug("report file name: " + fileNameStringBuffer);
		
		
		File file = new File(fileNameStringBuffer.toString());

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderTaxaOnContig());
			fw.write(LINE_SEPARATOR);

			List<TaxonOnContig> allTaxaOnContig = contig.getAllTaxaOnContig();
			for (TaxonOnContig taxonOnContig : allTaxaOnContig) {
				Taxon taxon = taxonOnContig.getTaxon();
				logger.info("Taxonomy id: " + taxon.getTaxonomyId());
				
				StringBuilder line = new StringBuilder();
				line.append(contig.getReference())
					.append(TAB).append(taxonOnContig.getRank())
					.append(TAB).append(taxonOnContig.getTaxon().getTaxonomyId())
					.append(TAB).append(taxonOnContig.getTaxon().getScientificName())
					.append(TAB).append(taxonOnContig.getNumberOfReads())
					.append(TAB).append(decimal.format(taxonOnContig.getCoverage()))
					.append(TAB).append(decimal.format(taxonOnContig.getUniqueCoverage()))
					.append(TAB).append(taxonOnContig.getNumberOfUniqueBases())
					.append(TAB).append(LINE_SEPARATOR);
				fw.write(line.toString());
			}

			fw.close();

			download = DownloadBuilder.of(file).withContentType("text/plain").downloadable().build();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return download;
	}

	@Post
	@Path("/report/taxonOnContig/by/sample")
	public Download taxonOnContigReportBySample(Long sampleId, ContigFilterParametersTO contigFilterParameters) {
		
		Double ct = contigFilterParameters.getCt();
		Double ctv = contigFilterParameters.getCtv();
		Double itg = contigFilterParameters.getItg();
		Integer numberOfFeatures = contigFilterParameters.getNumberOfFeatures();
		TaxonomicRank taxonomicRank = contigFilterParameters.getTaxonomicRank();
		Integer numberOfBorders = contigFilterParameters.getNumberOfBorders();
		
		System.out.println(itg);
		System.out.println(numberOfFeatures);
		System.out.println(taxonomicRank);
		System.out.println(numberOfBorders);
		System.out.println(ct);
		System.out.println(ctv);
		Sample sample = sampleDAO.load(sampleId);
		
		List<Contig> contigList = contigDAO.FindByContigBySample(sample, itg, numberOfFeatures, taxonomicRank, numberOfBorders, ct, ctv);
	
		StringBuffer outputFileNameStringBuffer = new StringBuffer();
		outputFileNameStringBuffer.append("/tmp/report_taxonsOnContig_by_sample")
			.append("SampleId_").append(sampleId).append("_")
			.append("itg_").append(itg).append("_")
			.append("numberOfFeatures_").append(numberOfFeatures).append("_")
			.append("taxonomicRank_").append(taxonomicRank).append("_")
			.append("numberOfBorders_").append(numberOfBorders).append("_")
			.append("ct_").append(ct).append("_")
			.append("ctv_").append(ctv).append(".tsv");
		
		File file = new File(outputFileNameStringBuffer.toString());

		DecimalFormat decimal = new DecimalFormat("#.###");

		Download download = null;

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderTaxaOnContig());
			fw.write(LINE_SEPARATOR);
			
			for (Contig contig : contigList) {
				
				List<TaxonOnContig> allTaxaOnContig = contig.getAllTaxaOnContig();
				for (TaxonOnContig taxonOnContig : allTaxaOnContig) {
					StringBuilder line = new StringBuilder();
					line.append(contig.getReference())
						.append(TAB).append(taxonOnContig.getRank())
						.append(TAB).append(taxonOnContig.getTaxon().getTaxonomyId())
						.append(TAB).append(taxonOnContig.getTaxon().getScientificName())
						.append(TAB).append(taxonOnContig.getNumberOfReads())
						.append(TAB).append(decimal.format(taxonOnContig.getCoverage()))
						.append(TAB).append(decimal.format(taxonOnContig.getUniqueCoverage()))
						.append(TAB).append(taxonOnContig.getNumberOfUniqueBases())
						.append(TAB).append(LINE_SEPARATOR);
					fw.write(line.toString());
				}
			}

			fw.close();

			download = DownloadBuilder.of(file).withContentType("text/plain").downloadable().build();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return download;

	}

	@Get
	@Path("/contig/report/{sampleId}")
	public Download report(Long sampleId) {
		
		logger.info("Report Contigs by sample id: " + sampleId);
		FileDownload download = null;
		
		try {
			
			Sample sample = sampleDAO.load(sampleId);
			List<Contig> contigList = contigDAO.FindContigBySampleOrderByContigSizeAndITGDesc(sample);
			
			StringBuffer fileNameStringBuffer = new StringBuffer();
			fileNameStringBuffer.append("/tmp/").append("Report_Contigs")
				.append("_").append(replaceSpacesToUnderline(sample.getName())).append(".tsv");
			
			logger.debug("report file name: " + fileNameStringBuffer);

			File file = new File(fileNameStringBuffer.toString());

			DecimalFormat decimal = new DecimalFormat("#.###");
			
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderToReportContigs());
			fw.write(LINE_SEPARATOR);
			for (Contig contig : contigList) {
				StringBuilder line = new StringBuilder();
				line.append(contig.getReference()).append(TAB).append(contig.getSize()).append(TAB)
						.append(getICTCR(decimal, contig, SPECIES)).append(TAB)
						.append(getIVCT(decimal, contig, SPECIES)).append(TAB).append(getBorders(contig, SPECIES))
						.append(TAB).append(getICTCR(decimal, contig, GENUS)).append(TAB)
						.append(getIVCT(decimal, contig, GENUS)).append(TAB).append(getBorders(contig, GENUS))
						.append(TAB).append(getICTCR(decimal, contig, FAMILY)).append(TAB)
						.append(getIVCT(decimal, contig, FAMILY)).append(TAB).append(getBorders(contig, FAMILY))
						.append(TAB).append(decimal.format(contig.getTaxonomicIdentificationIndex())).append(TAB)
						.append(contig.getNumberOfReads()).append(TAB).append(contig.getNumberOfReadsClassified())
						.append(TAB).append(contig.getNumberOfFeatures())

						.append(LINE_SEPARATOR);
				fw.write(line.toString());
			}
			fw.close();

			download = DownloadBuilder.of(file).withContentType("text/plain").downloadable().build();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			validator.add(new SimpleMessage("report.contig",  "Error to create contig report.", Severity.ERROR));
		}

		validator.onErrorForwardTo(SampleController.class).view();
		return download;
		
	}

	private String replaceSpacesToUnderline(String name) {
		return name.replaceAll("\\s+", "_");
	}

	private String createHeaderToReportContigs() {
		return new StringBuilder().append("REFERENCE").append(TAB).append("SIZE").append(TAB).append("CT|SP")
				.append(TAB).append("CTV|SP").append(TAB).append("BORDERS|SP").append(TAB).append("CT|GE")
				.append(TAB).append("CTV|GE").append(TAB).append("BORDERS|GE").append(TAB).append("CT|FA")
				.append(TAB).append("CTV|FA").append(TAB).append("BORDERS|FA").append(TAB).append("ITG").append(TAB)
				.append("NR").append(TAB).append("NRC").append(TAB).append("FEATURES").toString();
	}

	private String createHeaderTaxaOnContig() {
		return new StringBuilder().append("REFERENCE")
				.append(TAB).append("RANK")
				.append(TAB).append("TAXONOMY ID")
				.append(TAB).append("SCIENTIFIC NAME")
				.append(TAB).append("NUMBER OF READS")
				.append(TAB).append("COVERAGE")
				.append(TAB).append("UNIQUE COVERAGE")
				.append(TAB).append("NUMBER OF UNIQUE BASES")
				.append(TAB).toString();
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
