package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductLabel.
 */
@Entity
@Table(name = "product_label")
public class ProductLabel extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_label_manage_id")
    private Long productLabelManageId;

    @Column(name = "type")
    private String type;

    @Column(name = "use_display_date")
    private Boolean useDisplayDate;

    @Column(name = "display_date_from")
    private Instant displayDateFrom;

    @Column(name = "display_date_to")
    private Instant displayDateTo;

    @Column(name = "activated")
    private Boolean activated;

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

    @ManyToOne
    @JoinColumn(name = "product_label_manage_id", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = { "productLabels" }, allowSetters = true)
    private ProductLabelManage productLabelManage;

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

    public Boolean getUseDisplayDate() {
        return useDisplayDate;
    }

    public void setUseDisplayDate(Boolean useDisplayDate) {
        this.useDisplayDate = useDisplayDate;
    }

    public Instant getDisplayDateFrom() {
        return this.displayDateFrom;
    }

    public void setDisplayDateFrom(Instant displayDateFrom) {
        this.displayDateFrom = displayDateFrom;
    }

    public Instant getDisplayDateTo() {
        return this.displayDateTo;
    }

    public void setDisplayDateTo(Instant displayDateTo) {
        this.displayDateTo = displayDateTo;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductLabelManage getProductLabelManage() {
        return this.productLabelManage;
    }

    public void setProductLabelManage(ProductLabelManage productLabelManage) {
        this.productLabelManage = productLabelManage;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductLabelManageId() {
        return productLabelManageId;
    }

    public void setProductLabelManageId(Long productLabelManageId) {
        this.productLabelManageId = productLabelManageId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductLabel)) {
            return false;
        }
        return id != null && id.equals(((ProductLabel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductLabel{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", productLabelManageId='" + getProductLabelManageId() + "'" +
            ", type='" + getType() + "'" +
            ", useDisplayDate='" + getUseDisplayDate() + "'" +
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
