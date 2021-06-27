package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductShippingRel.
 */
@Entity
@Table(name = "product_shipping_rel")
public class ProductShippingRel extends AbstractAuditingEntity implements Serializable {

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
    @JsonIgnoreProperties(value = { "productShippingRels" }, allowSetters = true)
    private ProductShipping productShipping;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductShippingRel id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductShippingRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductShippingRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductShippingRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductShippingRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductShippingRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductShippingRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductShipping getProductShipping() {
        return this.productShipping;
    }

    public ProductShippingRel productShipping(ProductShipping productShipping) {
        this.setProductShipping(productShipping);
        return this;
    }

    public void setProductShipping(ProductShipping productShipping) {
        this.productShipping = productShipping;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductShippingRel)) {
            return false;
        }
        return id != null && id.equals(((ProductShippingRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductShippingRel{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
