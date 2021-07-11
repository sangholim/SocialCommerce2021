package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductViewRel.
 */
@Entity
@Table(name = "product_view_rel")
public class ProductViewRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_view_id")
    private Long productViewId;

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
    @JsonIgnoreProperties(value = { "productViewRels" }, allowSetters = true)
    @JoinColumn(name = "product_view_id", updatable = false, insertable = false)
    private ProductView productView;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductViewRel id(Long id) {
        this.id = id;
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductViewId() {
        return productViewId;
    }

    public void setProductViewId(Long productViewId) {
        this.productViewId = productViewId;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductViewRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductViewRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductViewRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductViewRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductViewRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductViewRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductView getProductView() {
        return this.productView;
    }

    public ProductViewRel productView(ProductView productView) {
        this.setProductView(productView);
        return this;
    }

    public void setProductView(ProductView productView) {
        this.productView = productView;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductViewRel)) {
            return false;
        }
        return id != null && id.equals(((ProductViewRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductViewRel{" +
            "id=" + getId() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
