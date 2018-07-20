package br.usp.iq.lbi.caravela.statistics;

import java.io.Serializable;
import java.util.Objects;

public class Taxonomy implements Serializable {

    private String reference;
    private String rank;
    private String scientificName;
    private Long NCBITaxonomyId;

    public Taxonomy(String reference, String rank, String scientificName, Long NCBITaxonomyId) {
        this.reference = reference.split("\\/")[0];
        this.rank = rank;
        this.scientificName = scientificName;
        this.NCBITaxonomyId = NCBITaxonomyId;
    }

    public String getReference() {
        return reference;
    }

    public Long getNCBITaxonomyId() {
        return NCBITaxonomyId;
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxonomy taxonomy = (Taxonomy) o;
        return Objects.equals(reference, taxonomy.reference) &&
                Objects.equals(NCBITaxonomyId, taxonomy.NCBITaxonomyId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reference, NCBITaxonomyId);
    }

    @Override
    public String toString() {
        return "Taxonomy{" +
                "reference='" + reference + '\'' +
                ", rank='" + rank + '\'' +
                ", scientificName='" + scientificName + '\'' +
                ", NCBITaxonomyId=" + NCBITaxonomyId +
                '}';
    }
}
