package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductLabelRel.
 */
@Entity
@Table(name = "product_label_rel")
public class ProductLabelRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_display_date")
    private Boolean isDisplayDate;

    @Column(name = "display_date_from")
    private Instant displayDateFrom;

    @Column(name = "display_date_to")
    private Instant displayDateTo;

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
    @JsonIgnoreProperties(value = { "productLabelRels" }, allowSetters = true)
    private ProductLabel productLabel;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductLabelRel id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getIsDisplayDate() {
        return this.isDisplayDate;
    }

    public ProductLabelRel isDisplayDate(Boolean isDisplayDate) {
        this.isDisplayDate = isDisplayDate;
        return this;
    }

    public void setIsDisplayDate(Boolean isDisplayDate) {
        this.isDisplayDate = isDisplayDate;
    }

    public Instant getDisplayDateFrom() {
        return this.displayDateFrom;
    }

    public ProductLabelRel displayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
        return this;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return this.displayDateTo;
    }

    public ProductLabelRel displayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
        return this;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductLabelRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductLabelRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductLabelRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductLabelRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductLabelRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductLabelRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductLabel getProductLabel() {
        return this.productLabel;
    }

    public ProductLabelRel productLabel(ProductLabel productLabel) {
        this.setProductLabel(productLabel);
        return this;
    }

    public void setProductLabel(ProductLabel productLabel) {
        this.productLabel = productLabel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductLabelRel)) {
            return false;
        }
        return id != null && id.equals(((ProductLabelRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductLabelRel{" +
            "id=" + getId() +
            ", isDisplayDate='" + getIsDisplayDate() + "'" +
            ", displayDateFrom='" + getDisplayDateFrom() + "'" +
            ", displayDateTo='" + getDisplayDateTo() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
