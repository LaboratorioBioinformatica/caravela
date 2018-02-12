package br.usp.iq.lbi.caravela.controller;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.DownloadBuilder;
import br.usp.iq.lbi.caravela.controller.auth.WebUser;
import br.usp.iq.lbi.caravela.dao.ClassifiedReadByContextDAO;
import br.usp.iq.lbi.caravela.dao.SampleDAO;
import br.usp.iq.lbi.caravela.dto.report.TaxonomicReportTO;
import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.Contig;
import br.usp.iq.lbi.caravela.model.Read;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.Taxon;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

@Controller
public class TaxonomicReportController {
	
	private static final String LINE_SEPARATOR = System.lineSeparator();
	private static final String TAB = "\t";
	private final Result result;
	private WebUser webUser;
	private final SampleDAO sampleDAO;
	private final ClassifiedReadByContextDAO classifiedReadByContextDAO;
	
	public TaxonomicReportController() {
		this(null, null, null, null);
	}
	
	@Inject
	public TaxonomicReportController(Result result, WebUser webUser, SampleDAO sampleDAO, ClassifiedReadByContextDAO classifiedReadByContextDAO) {
		this.result =result;
		this.webUser = webUser;
		this.sampleDAO = sampleDAO;
		this.classifiedReadByContextDAO = classifiedReadByContextDAO;
	}


	@Path("/taxonomic/classified/report/sample/{sampleId}")
	public Download taxonomicReport(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);

		List<TaxonomicReportTO> taxonomicReportBySampleList = classifiedReadByContextDAO.findTaxonomicReportBySample(sample);

		StringBuilder reportFileName = new StringBuilder()
				.append("/tmp/report-")
				.append(sample.getId())
				.append("-")
				.append(sample.getStudy().getId())
				.append("-genus.tsv");


		File file = new File(reportFileName.toString());

		DecimalFormat decimal = new DecimalFormat("#.###");

		Download download = null;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderReadsClassifiedByContexReport());
			fw.write(LINE_SEPARATOR);

			for (TaxonomicReportTO taxonomicReportTO : taxonomicReportBySampleList) {

				StringBuilder line = new StringBuilder();
				line
						.append(taxonomicReportTO.getSequenceReference())
						.append(TAB)
						.append(taxonomicReportTO.getNcbiScientificName())
						.append(TAB)
						.append(decimal.format(taxonomicReportTO.getCT()))
						.append(TAB)
						.append(decimal.format(taxonomicReportTO.getCTV()))
						.append(TAB)
						.append(taxonomicReportTO.getBorders())
						.append(TAB)
						.append(taxonomicReportTO.getContigReference())
						.append(LINE_SEPARATOR);
				fw.write(line.toString());
			}
			fw.close();
			download = DownloadBuilder.of(file)
					.withContentType("text/plain")
					.downloadable()
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}


		return download;
	}



	@Path("/taxonomic/readsNoTaxonClassifiedByContex/report/by/sample/{sampleId}")
	public Download report(Long sampleId){
		Sample sample = sampleDAO.load(sampleId);
		List<ClassifiedReadByContex> readsNoTaxonClassifiedByContex = classifiedReadByContextDAO.findBySample(sample);
		
		File file = new File("/tmp/ReadsNoTaxonClassifiedByContex-genus.tsv");
		
		DecimalFormat decimal = new DecimalFormat("#.###");
		
		Download download = null;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderReadsClassifiedByContexReport());
			fw.write(LINE_SEPARATOR);
			
			for (ClassifiedReadByContex readByContext : readsNoTaxonClassifiedByContex) {
				Contig contig = readByContext.getContig();
				Read read = readByContext.getRead();
				Taxon taxon = readByContext.getTaxon();
				
				StringBuilder line = new StringBuilder();
				line.append(read.getReference())
				.append(TAB)
				.append(taxon.getTaxonomyId())
				.append(TAB)
				.append(taxon.getScientificName())
				.append(TAB)
				.append(decimal.format(contig.getIndexOfConsistencyTaxonomicByCountReads(TaxonomicRank.GENUS)))
				.append(TAB)
				.append(decimal.format(contig.getIndexOfVerticalConsistencyTaxonomic(TaxonomicRank.GENUS)))
				.append(TAB)
				.append(decimal.format(contig.getNumberOfBorders(TaxonomicRank.GENUS)))
				.append(TAB)
				.append(contig.getReference())
				.append(LINE_SEPARATOR);
				fw.write(line.toString());
			}
			fw.close();
			download = DownloadBuilder.of(file)
				    .withContentType("text/plain")
				    .downloadable()
				    .build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return download;
	}

	private String createHeaderReadsClassifiedByContexReport() {
		return new StringBuilder()
				.append("READ REFERENCE").append(TAB)
				.append("SCIENTIFIC NAME").append(TAB)
				.append("CT|GE").append(TAB)
				.append("CTV|GE").append(TAB)
				.append("BORDERS|GE").append(TAB)
				.append("CONTIG REFERENCE").append(TAB)
				.toString();				
	}

}
