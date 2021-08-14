package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductMapping.
 */
@Entity
@Table(name = "product_mapping")
public class ProductMapping extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_mapping_manage_id")
    private Long productMappingManageId;

    @Column(name = "type")
    private String type;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JoinColumn(name = "product_mapping_manage_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productMappings" }, allowSetters = true)
    private ProductMappingManage productMappingManage;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(
        value = {
            "productDiscounts",
            "productMappings",
            "productOptions",
            "productAddOptions",
            "productInputOptions",
            "productFaqs",
            "productAnnounces",
            "productAddImages",
            "productLabels",
            "productTemplates",
            "productCategories",
            "packageDescriptions",
            "productNoticeManage",
            "productClazzAuthor",
            "productStore",
        },
        allowSetters = true
    )
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public ProductMappingManage getProductMappingManage() {
        return this.productMappingManage;
    }

    public void setProductMappingManage(ProductMappingManage productMappingManage) {
        this.productMappingManage = productMappingManage;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductMappingManageId() {
        return productMappingManageId;
    }

    public void setProductMappingManageId(Long productMappingManageId) {
        this.productMappingManageId = productMappingManageId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductMapping)) {
            return false;
        }
        return id != null && id.equals(((ProductMapping) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductMapping{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", productMappingManageId='" + getProductMappingManageId() + "'" +
            ", type='" + getType() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
