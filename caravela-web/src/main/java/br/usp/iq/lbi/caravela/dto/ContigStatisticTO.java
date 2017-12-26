package br.usp.iq.lbi.caravela.dto;

public class ContigStatisticTO {

    //CT
    private Double taxonomicConsistency;
    //CTV
    private Double taxonomicVerticalConsistency;

    private String numberOfBorder;

    public ContigStatisticTO(Double taxonomicConsistency, Double taxonomicVerticalConsistency, String numberOfBorder) {
        this.taxonomicConsistency = taxonomicConsistency;
        this.taxonomicVerticalConsistency = taxonomicVerticalConsistency;
        this.numberOfBorder = numberOfBorder;
    }

    public Double getTaxonomicConsistency() {
        return taxonomicConsistency;
    }

    public Double getTaxonomicVerticalConsistency() {
        return taxonomicVerticalConsistency;
    }

    public String getNumberOfBorder() {
        return numberOfBorder;
    }
}

