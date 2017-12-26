package br.usp.iq.lbi.caravela.domain;

import br.usp.iq.lbi.caravela.dao.ContigDAO;
import br.usp.iq.lbi.caravela.dao.FeatureDAO;
import br.usp.iq.lbi.caravela.dao.ReadDAO;
import br.usp.iq.lbi.caravela.dto.ContigConverter;
import br.usp.iq.lbi.caravela.dto.ContigReportTO;
import br.usp.iq.lbi.caravela.dto.ContigStatisticTO;
import br.usp.iq.lbi.caravela.dto.ContigTO;
import br.usp.iq.lbi.caravela.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestScoped
public class ContigManagerImpl implements ContigManager {

	private static final Logger logger = LoggerFactory.getLogger(ContigManagerImpl.class);

	private static final Long NUMBER_OF_CONTIG_PER_PAGE = 100000l;
	@Inject private ContigDAO contigDAO;
	@Inject private FeatureDAO featureDAO;
	@Inject private ReadDAO readDAO;
	@Inject private ReadManager readManager;
	@Inject private Paginator paginator;


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

	@Override
	public List<ContigReportTO> searchAllContigBySample(Sample sample) {

		final Long totalOfContigs = contigDAO.countAllContigsBySample(sample);

		logger.info("totalOfContigs: {}", totalOfContigs);

		List<ContigReportTO> contigReportFullList = new ArrayList<>();
		final List<IntervalPage> pages = paginator.getPages(totalOfContigs, NUMBER_OF_CONTIG_PER_PAGE);
		for (IntervalPage page : pages){
			final Integer start = page.getStart();
			logger.info("start: {} - max: {}", start, NUMBER_OF_CONTIG_PER_PAGE);
			final List<Contig> contigs = contigDAO.findAllContigsBySample(sample, start, NUMBER_OF_CONTIG_PER_PAGE.intValue());

			contigReportFullList.addAll(convert(contigs));

		}

		return contigReportFullList;

	}

	private List<ContigReportTO> convert(List<Contig> contigs) {

		List<ContigReportTO> contigReportTOList = new ArrayList<>();
		for (Contig contig : contigs) {
			contigReportTOList.add(
					createContigReportTO(contig)
			);

		}

		return contigReportTOList;
	}

	private ContigReportTO createContigReportTO(Contig contig) {
		return new ContigReportTO(
            contig.getReference(),
            contig.getSize(),
            contig.getTaxonomicIdentificationIndex(),
            statisticsOfConrig(contig, TaxonomicRank.FAMILY),
            statisticsOfConrig(contig, TaxonomicRank.GENUS),
            statisticsOfConrig(contig, TaxonomicRank.SPECIES),
            contig.getNumberOfReads(),
            contig.getNumberOfReadsClassified(),
            contig.getNumberOfFeatures()
    	);
	}

	private ContigStatisticTO statisticsOfConrig(Contig contig, TaxonomicRank rank) {
		return new ContigStatisticTO(getCT(contig, rank), getCTV(contig, rank), getBorders(contig, rank));
	}

	private Double getCTV(Contig contig, TaxonomicRank rank) {
		return contig.getIndexOfVerticalConsistencyTaxonomic(rank);
	}

	private String getBorders(Contig contig, TaxonomicRank rank) {
		return contig.getNumberOfBorders(rank).toString();
	}

	private Double getCT(Contig contig, TaxonomicRank rank) {
		return contig.getIndexOfConsistencyTaxonomicByCountReads(rank);
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
