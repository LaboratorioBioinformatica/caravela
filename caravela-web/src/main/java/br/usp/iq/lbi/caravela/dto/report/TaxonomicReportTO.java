package br.usp.iq.lbi.caravela.dto.report;


public class TaxonomicReportTO {

    private Long contigId;
    private Long sampleId;
    private String contigReference;
    private Integer contigSize;
    private Integer numberOfReadsClassified;
    private Integer numberOfReads;
    private Integer numberOfFeatures;
    private Double ITG;
    private Double CT;
    private Double CTV;
    private Integer borders;
    private String sequenceReference;
    private Long taxonId;
    private Long ncbiTaxonomyId;
    private String ncbiTaxonomyRank;
    private String ncbiScientificName;

    public TaxonomicReportTO(String contigReference, String ncbiScientificName, Double ITG, String readReference, Double CT, Double CTV, Integer borders){
        this.contigReference = contigReference;
        this.ncbiScientificName = ncbiScientificName;
        this.sequenceReference = readReference;
        this.ITG = ITG;
        this.CT = CT;
        this.CTV = CTV;
        this.borders = borders;
        
    }


    public Long getContigId() {
        return contigId;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public String getContigReference() {
        return contigReference;
    }

    public Integer getContigSize() {
        return contigSize;
    }

    public Integer getNumberOfReadsClassified() {
        return numberOfReadsClassified;
    }

    public Integer getNumberOfReads() {
        return numberOfReads;
    }

    public Integer getNumberOfFeatures() {
        return numberOfFeatures;
    }

    public Double getITG() {
        return ITG;
    }

    public String getSequenceReference() {
        return sequenceReference;
    }

    public Long getTaxonId() {
        return taxonId;
    }

    public Long getNcbiTaxonomyId() {
        return ncbiTaxonomyId;
    }

    public String getNcbiTaxonomyRank() {
        return ncbiTaxonomyRank;
    }

    public String getNcbiScientificName() {
        return ncbiScientificName;
    }
    public Double getCT() {
        return CT;
    }

    public Double getCTV() {
        return CTV;
    }

    public Integer getBorders() {
        return borders;
    }
}
