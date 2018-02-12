package br.usp.iq.lbi.caravela.dto.report;

public class TaxonomicContigReportTO {

    private Long id;
    private Long sampleId;
    private String reference;
    private Integer size;
    private Integer NumberOfReadsClassified;
    private Integer NumberOfReads;
    private Integer NumberOfFeatures;
    private Double taxonomicIdentificationGeneral;

    public TaxonomicContigReportTO(Long id, Long sampleId, String reference, Integer size, Integer numberOfReadsClassified, Integer numberOfReads, Integer numberOfFeatures, Double taxonomicIdentificationGeneral) {
        this.id = id;
        this.sampleId = sampleId;
        this.reference = reference;
        this.size = size;
        NumberOfReadsClassified = numberOfReadsClassified;
        NumberOfReads = numberOfReads;
        NumberOfFeatures = numberOfFeatures;
        this.taxonomicIdentificationGeneral = taxonomicIdentificationGeneral;
    }

    public Long getId() {
        return id;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public String getReference() {
        return reference;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getNumberOfReadsClassified() {
        return NumberOfReadsClassified;
    }

    public Integer getNumberOfReads() {
        return NumberOfReads;
    }

    public Integer getNumberOfFeatures() {
        return NumberOfFeatures;
    }

    public Double getTaxonomicIdentificationGeneral() {
        return taxonomicIdentificationGeneral;
    }
}
