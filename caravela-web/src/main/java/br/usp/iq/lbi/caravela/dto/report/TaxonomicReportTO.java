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
    private Integer flagAlignment;
    private String cigar;


    public TaxonomicReportTO(String contigReference, Long ncbiTaxonomyId, String ncbiScientificName, Double ITG,
                             String readReference, Double CT, Double CTV, Integer borders, Integer flagAlignment, String cigar){
        this.contigReference = contigReference;
        this.ncbiTaxonomyId = ncbiTaxonomyId;
        this.ncbiScientificName = ncbiScientificName;
        this.sequenceReference = readReference;
        this.ITG = ITG;
        this.CT = CT;
        this.CTV = CTV;
        this.borders = borders;
        this.flagAlignment = flagAlignment;
        this.cigar = cigar;
        
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

    public Integer getFlagAlignment() {
        return flagAlignment;
    }

    public String getCigar() {
        return cigar;
    }

}
