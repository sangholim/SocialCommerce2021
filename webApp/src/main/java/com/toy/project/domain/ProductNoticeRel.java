package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductNoticeRel.
 */
@Entity
@Table(name = "product_notice_rel")
public class ProductNoticeRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
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

    @ManyToOne
    @JsonIgnoreProperties(value = { "productNoticeRels" }, allowSetters = true)
    private ProductNotice productNotice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductNoticeRel id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductNoticeRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductNoticeRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductNoticeRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductNoticeRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductNoticeRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductNoticeRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductNotice getProductNotice() {
        return this.productNotice;
    }

    public ProductNoticeRel productNotice(ProductNotice productNotice) {
        this.setProductNotice(productNotice);
        return this;
    }

    public void setProductNotice(ProductNotice productNotice) {
        this.productNotice = productNotice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductNoticeRel)) {
            return false;
        }
        return id != null && id.equals(((ProductNoticeRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductNoticeRel{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
