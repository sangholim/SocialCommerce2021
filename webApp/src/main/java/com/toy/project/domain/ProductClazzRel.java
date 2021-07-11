package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductClazzRel.
 */
@Entity
@Table(name = "product_clazz_rel")
public class ProductClazzRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "clazz_id")
    private Long clazzId;

    @Column(name = "use_calculation")
    private Boolean useCalculation;

    @Column(name = "calculation")
    private String calculation;

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
    @JoinColumn(name = "product_id", updatable = false, insertable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "productClazzRels" }, allowSetters = true)
    @JoinColumn(name = "clazz_id", updatable = false, insertable = false)
    private Clazz clazz;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductClazzRel id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public Boolean getUseCalculation() {
        return this.useCalculation;
    }

    public ProductClazzRel useCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
        return this;
    }

    public void setUseCalculation(Boolean useCalculation) {
        this.useCalculation = useCalculation;
    }

    public ProductClazzRel calculation(String calculation) {
        this.calculation = calculation;
        return this;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Instant getCalculationDateFrom() {
        return this.calculationDateFrom;
    }

    public ProductClazzRel calculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
        return this;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return this.calculationDateTo;
    }

    public ProductClazzRel calculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
        return this;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductClazzRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductClazzRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductClazzRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductClazzRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductClazzRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductClazzRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Clazz getClazz() {
        return this.clazz;
    }

    public ProductClazzRel clazz(Clazz clazz) {
        this.setClazz(clazz);
        return this;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductClazzRel)) {
            return false;
        }
        return id != null && id.equals(((ProductClazzRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductClazzRel{" +
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
