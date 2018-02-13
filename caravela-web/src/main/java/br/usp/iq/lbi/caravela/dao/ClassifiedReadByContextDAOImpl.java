package br.usp.iq.lbi.caravela.dao;

import br.usp.iq.lbi.caravela.dto.report.TaxonomicReportTO;
import br.usp.iq.lbi.caravela.model.ClassifiedReadByContex;
import br.usp.iq.lbi.caravela.model.Sample;
import br.usp.iq.lbi.caravela.model.TaxonomicRank;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ClassifiedReadByContextDAOImpl extends DAOImpl<ClassifiedReadByContex> implements ClassifiedReadByContextDAO {


    private static final Integer CONTIG_ID = 0;
	private static final Integer SAMPLE_ID = 1;
	private static final Integer CONTIG_REFERENCE = 2;
	private static final Integer CONTIG_SIZE = 3;
	private static final Integer NUMBER_OF_READS_CLASSIFIED = 4;
	private static final Integer NUMBER_OF_READS = 5;
	private static final Integer NUMBER_OF_FEATURES = 6;
	private static final Integer ITG = 7;
	private static final Integer CT = 8;
	private static final Integer CTV = 9;
	private static final Integer BOUNDARY = 10;
	private static final Integer READ_REFERENCE = 11;
	private static final Integer TAXON_ID = 12;
	private static final Integer NCBI_TAXONOMY_RANK = 13;
	private static final Integer NCBI_TAXONOMY_ID = 14;
	private static final Integer NCBI_SCIENTIFIC_NAME = 15;

    public static final String STORE_PROCEDURE_CLASSIFIED_BY_CONTEXT = "reportTaxonClassifiedByContext";


    @Inject
	public ClassifiedReadByContextDAOImpl(EntityManager entityManager) {
		super(entityManager);
	}
	
	public Integer removeBySample(Long sampleId){
		Query query = entityManager.createQuery("delete from ClassifiedReadByContex crbyc where crbyc.sample.id=:sampleId");
		query.setParameter("sampleId", sampleId);
		return query.executeUpdate();
	}
	
	public List<ClassifiedReadByContex> findBySample(Sample sample){
		TypedQuery<ClassifiedReadByContex> query = entityManager.createQuery("SELECT c FROM ClassifiedReadByContex c WHERE c.sample=:sample", ClassifiedReadByContex.class);
		List<ClassifiedReadByContex> classifiedReadByContexList = query.setParameter("sample", sample).getResultList();
		return classifiedReadByContexList;
		
		
	}



	public List<TaxonomicReportTO> findTaxonomicReportBySample(Sample sample){

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery(STORE_PROCEDURE_CLASSIFIED_BY_CONTEXT)
				.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
				.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
				.setParameter(1, sample.getId())
				.setParameter(2, TaxonomicRank.GENUS.toString());


		query.execute();

		List<TaxonomicReportTO> resultList = new ArrayList<>();
		List<Object[]> taxaClassifiedByContex = query.getResultList();

		taxaClassifiedByContex.forEach(
				resultLine -> {
					resultList.add(new TaxonomicReportTO(
							resultLine[CONTIG_REFERENCE].toString(),
							resultLine[NCBI_SCIENTIFIC_NAME].toString(),
							Double.parseDouble(resultLine[ITG].toString()),
							resultLine[READ_REFERENCE].toString(),
							Double.parseDouble(resultLine[CT].toString()),
							Double.parseDouble(resultLine[CTV].toString()),
							Integer.parseInt(resultLine[BOUNDARY].toString())
							)
					);
				}
		);

		return resultList;
	}

}
