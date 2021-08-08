package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Author.
 */
@Entity
@Table(name = "author")
public class Author extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "calculation")
    private Integer calculation;

    @Size(max = 50)
    @Column(name = "calculation_unit", length = 50)
    private String calculationUnit;

    @Column(name = "calculation_date_from")
    private Instant calculationDateFrom;

    @Column(name = "calculation_date_to")
    private Instant calculationDateTo;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties(value = { "products", "clazz", "author" }, allowSetters = true)
    private Set<ProductClazzAuthor> productClazzAuthors = new HashSet<>();

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties(value = { "productClazzAuthors", "clazzChapters", "author" }, allowSetters = true)
    private Set<Clazz> clazzes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalculation() {
        return this.calculation;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public String getCalculationUnit() {
        return this.calculationUnit;
    }

    public void setCalculationUnit(String calculationUnit) {
        this.calculationUnit = calculationUnit;
    }

    public Instant getCalculationDateFrom() {
        return this.calculationDateFrom;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return this.calculationDateTo;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<ProductClazzAuthor> getProductClazzAuthors() {
        return this.productClazzAuthors;
    }

    public void setProductClazzAuthors(Set<ProductClazzAuthor> productClazzAuthors) {
        if (this.productClazzAuthors != null) {
            this.productClazzAuthors.forEach(i -> i.setAuthor(null));
        }
        if (productClazzAuthors != null) {
            productClazzAuthors.forEach(i -> i.setAuthor(this));
        }
        this.productClazzAuthors = productClazzAuthors;
    }

    public Set<Clazz> getClazzes() {
        return this.clazzes;
    }

    public void setClazzes(Set<Clazz> clazzes) {
        if (this.clazzes != null) {
            this.clazzes.forEach(i -> i.setAuthor(null));
        }
        if (clazzes != null) {
            clazzes.forEach(i -> i.setAuthor(this));
        }
        this.clazzes = clazzes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Author)) {
            return false;
        }
        return id != null && id.equals(((Author) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Author{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", calculation=" + getCalculation() +
            ", calculationUnit='" + getCalculationUnit() + "'" +
            ", calculationDateFrom='" + getCalculationDateFrom() + "'" +
            ", calculationDateTo='" + getCalculationDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
