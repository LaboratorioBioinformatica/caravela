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

		StringBuilder reportFileName = createReportName(sample);


		File file = new File(reportFileName.toString());

		DecimalFormat decimal = new DecimalFormat("#.###");

		Download download = null;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(createHeaderReadsClassifiedByContexReport());
			fw.write(LINE_SEPARATOR);

			for (TaxonomicReportTO taxonomicReportTO : taxonomicReportBySampleList) {

				String line = new StringBuilder()
                        .append(taxonomicReportTO.getSequenceReference())
						.append(TAB)
						.append(taxonomicReportTO.getNcbiTaxonomyId())
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
						.append(TAB)
						.append(taxonomicReportTO.getFlagAlignment())
						.append(TAB)
						.append(taxonomicReportTO.getCigar())
						.append(LINE_SEPARATOR)
                        .toString();

				fw.write(line);
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

	private StringBuilder createReportName(Sample sample) {

		String sampleName = sample.getName().replace(" ", "-");

		return new StringBuilder()
				.append("/tmp/report-")
				.append(sample.getStudy().getId())
				.append("-")
				.append(sample.getId())
				.append("-")
				.append(sampleName)
				.append("-genus.tsv");
	}

	private String createHeaderReadsClassifiedByContexReport() {
		return new StringBuilder()
				.append("READ REFERENCE").append(TAB)
				.append("NCBI TAXONOMY ID").append(TAB)
				.append("SCIENTIFIC NAME").append(TAB)
				.append("CT|GE").append(TAB)
				.append("CTV|GE").append(TAB)
				.append("BORDERS|GE").append(TAB)
				.append("CONTIG REFERENCE").append(TAB)
				.append("READ FLAG ALIGNMENT").append(TAB)
				.append("READ CIGAR").append(TAB)
				.toString();				
	}

}
