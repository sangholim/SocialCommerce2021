package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductStoreRel.
 */
@Entity
@Table(name = "product_store_rel")
public class ProductStoreRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "use_calculation")
    private Boolean useCalculation;

    @Column(name = "calculation")
    private Integer calculation;

    @Column(name = "calculation_date_from")
    private Instant calculationDateFrom;

    @Column(name = "calculation_date_to")
    private Instant calculationDateTo;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "productCategoryRels",
            "productLabelRels",
            "productMappingRels",
            "productViewRels",
            "productNoticeRels",
            "productShippingRels",
            "productTemplateRels",
            "productOptionRels",
            "productClazzRels",
            "productStoreRels",
        },
        allowSetters = true
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productStoreRels" }, allowSetters = true)
    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductStoreRel id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getUseCalculation() {
        return this.useCalculation;
    }

    public ProductStoreRel useCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
        return this;
    }

    public void setUseCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
    }

    public Integer getCalculation() {
        return this.calculation;
    }

    public ProductStoreRel calculation(Integer calculation) {
        this.calculation = calculation;
        return this;
    }

    public void setCalculation(Integer calculation) {
        this.calculation = calculation;
    }

    public Instant getCalculationDateFrom() {
        return this.calculationDateFrom;
    }

    public ProductStoreRel calculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
        return this;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return this.calculationDateTo;
    }

    public ProductStoreRel calculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
        return this;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductStoreRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductStoreRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductStoreRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductStoreRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductStoreRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductStoreRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return this.store;
    }

    public ProductStoreRel store(Store store) {
        this.setStore(store);
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductStoreRel)) {
            return false;
        }
        return id != null && id.equals(((ProductStoreRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductStoreRel{" +
            "id=" + getId() +
            ", useCalculation='" + getUseCalculation() + "'" +
            ", calculation=" + getCalculation() +
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
