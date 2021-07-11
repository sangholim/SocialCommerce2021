package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ProductCategoryRel.
 */
@Entity
@Table(name = "product_category_rel")
public class ProductCategoryRel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_category_id")
    private Long productCategoryId;

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
    @JoinColumn(name = "product_category_id", updatable = false, insertable = false)
    @JsonIgnoreProperties(value = { "productCategoryRels" }, allowSetters = true)
    private ProductCategory productCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategoryRel id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public ProductCategoryRel activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductCategoryRel createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public ProductCategoryRel createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public ProductCategoryRel lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public ProductCategoryRel lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductCategoryRel product(Product product) {
        this.setProduct(product);
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public ProductCategoryRel productCategory(ProductCategory productCategory) {
        this.setProductCategory(productCategory);
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductCategoryRel)) {
            return false;
        }
        return id != null && id.equals(((ProductCategoryRel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategoryRel{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", productCategoryId='" + getProductCategoryId() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
