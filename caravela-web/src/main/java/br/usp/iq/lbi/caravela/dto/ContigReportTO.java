package br.usp.iq.lbi.caravela.dto;

public class ContigReportTO {

    private String reference;
    private Integer size;
    private Double taxonomicIdentificationGeneral;
    private ContigStatisticTO family;
    private ContigStatisticTO genus;
    private ContigStatisticTO species;
    private Integer numberOfReads;
    private Integer numberOfReadsClassified;
    private Integer numberOfFeatures;


    public ContigReportTO(String reference, Integer size, Double taxonomicIdentificationGeneral, ContigStatisticTO family, ContigStatisticTO genus, ContigStatisticTO species, Integer numberOfReads, Integer numberOfReadsClassified, Integer numberOfFeatures) {
        this.reference = reference;
        this.size = size;
        this.taxonomicIdentificationGeneral = taxonomicIdentificationGeneral;
        this.family = family;
        this.genus = genus;
        this.species = species;
        this.numberOfReads = numberOfReads;
        this.numberOfReadsClassified = numberOfReadsClassified;
        this.numberOfFeatures = numberOfFeatures;
    }

    public String getReference() {
        return reference;
    }

    public Integer getSize() {
        return size;
    }

    public Double getTaxonomicIdentificationGeneral() {
        return taxonomicIdentificationGeneral;
    }

    public Double getFamilyCT(){
        return family.getTaxonomicConsistency();
    }

    public Double getFamilyCTV(){
        return family.getTaxonomicVerticalConsistency();
    }

    public String getFamilyBorder(){
        return family.getNumberOfBorder();
    }

    public Double getGenusCT(){
        return genus.getTaxonomicConsistency();
    }

    public Double getGenusCTV(){
        return genus.getTaxonomicVerticalConsistency();
    }

    public String getGenusBorder(){
        return genus.getNumberOfBorder();
    }

    public Double getSpeciesCT(){
        return species.getTaxonomicConsistency();
    }

    public Double getSpeciesCTV(){
        return species.getTaxonomicVerticalConsistency();
    }

    public String getSpeciesBorder(){
        return species.getNumberOfBorder();
    }

    public Integer getNumberOfReads() {
        return numberOfReads;
    }

    public Integer getNumberOfReadsClassified() {
        return numberOfReadsClassified;
    }

    public Integer getNumberOfFeatures() {
        return numberOfFeatures;
    }
}
