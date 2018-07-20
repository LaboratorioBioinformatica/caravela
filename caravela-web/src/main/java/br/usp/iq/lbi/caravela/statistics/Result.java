package br.usp.iq.lbi.caravela.statistics;

import java.util.Objects;

public class Result {
    private String reference;
    private Long NCBITaxonomiyId;
    private String scientificName;
    private Float CTGenus;
    private Float CTVGenus;
    private Integer border;
    private String contigReference;
    private Integer flagAlignment;
    private String cigar;

    public Result(String reference, Long NCBITaxonomiyId, String scientificName, Float CTGenus, Float CTVGenus, Integer border, String contigReference) {
        this.reference = reference;
        this.NCBITaxonomiyId = NCBITaxonomiyId;
        this.scientificName = scientificName;
        this.CTGenus = CTGenus;
        this.CTVGenus = CTVGenus;
        this.border = border;
        this.contigReference = contigReference;
    }

    public Result(String reference, Long NCBITaxonomiyId, String scientificName, Float CTGenus, Float CTVGenus,
                  Integer border, String contigReference, Integer flagAlignment, String cigar) {
        this.reference = reference;
        this.NCBITaxonomiyId = NCBITaxonomiyId;
        this.scientificName = scientificName;
        this.CTGenus = CTGenus;
        this.CTVGenus = CTVGenus;
        this.border = border;
        this.contigReference = contigReference;
        this.flagAlignment = flagAlignment;
        this.cigar = cigar;
    }

    public String getReference() {
        return reference;
    }

    public Long getNCBITaxonomiyId() {
        return NCBITaxonomiyId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public Float getCTGenus() {
        return CTGenus;
    }

    public Float getCTVGenus() {
        return CTVGenus;
    }

    public Integer getBorder() {
        return border;
    }

    public String getContigReference() {
        return contigReference;
    }

    public Integer getFlagAlignment() {
        return flagAlignment;
    }

    public String getCigar() {
        return cigar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(reference, result.reference) &&
                Objects.equals(NCBITaxonomiyId, result.NCBITaxonomiyId) &&
                Objects.equals(scientificName, result.scientificName) &&
                Objects.equals(CTGenus, result.CTGenus) &&
                Objects.equals(CTVGenus, result.CTVGenus) &&
                Objects.equals(border, result.border) &&
                Objects.equals(contigReference, result.contigReference);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reference, NCBITaxonomiyId, scientificName, CTGenus, CTVGenus, border, contigReference);
    }

    @Override
    public String toString() {
        return "Result{" +
                "reference='" + reference + '\'' +
                ", NCBITaxonomiyId=" + NCBITaxonomiyId +
                ", scientificName='" + scientificName + '\'' +
                ", CTGenus=" + CTGenus +
                ", CTVGenus=" + CTVGenus +
                ", border=" + border +
                ", contigReference='" + contigReference + '\'' +
                '}';
    }
}
