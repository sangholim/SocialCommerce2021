package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProductOption.
 */
@Entity
@Table(name = "product_option")
public class ProductOption extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "design_name")
    private String designName;

    @Column(name = "color_code")
    private String colorCode;

    @Column(name = "color_name")
    private String colorName;

    @Column(name = "use_package_description")
    private Boolean usePackageDescription;

    @Lob
    @Column(name = "package_description")
    private String packageDescription;

    @Column(name = "display_recommend_package")
    private Boolean displayRecommendPackage;

    @Column(name = "package_thumbnail_url")
    private String packageThumbnailUrl;

    @Column(name = "price")
    private String price;

    @Column(name = "quantity")
    private Long quantity;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "option_code")
    private String optionCode;

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

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDesignName() {
        return this.designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorName() {
        return this.colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Boolean getUsePackageDescription() {
        return this.usePackageDescription;
    }

    public void setUsePackageDescription(Boolean usePackageDescription) {
        this.usePackageDescription = usePackageDescription;
    }

    public String getPackageDescription() {
        return this.packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Boolean getDisplayRecommendPackage() {
        return this.displayRecommendPackage;
    }

    public void setDisplayRecommendPackage(Boolean displayRecommendPackage) {
        this.displayRecommendPackage = displayRecommendPackage;
    }

    public String getPackageThumbnailUrl() {
        return this.packageThumbnailUrl;
    }

    public void setPackageThumbnailUrl(String packageThumbnailUrl) {
        this.packageThumbnailUrl = packageThumbnailUrl;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOptionCode() {
        return this.optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOption)) {
            return false;
        }
        return id != null && id.equals(((ProductOption) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOption{" +
            "id=" + getId() +
            ", productId='" + getProductId() + "'" +
            ", name='" + getName() + "'" +
            ", packageName='" + getPackageName() + "'" +
            ", designName='" + getDesignName() + "'" +
            ", colorCode='" + getColorCode() + "'" +
            ", colorName='" + getColorName() + "'" +
            ", usePackageDescription='" + getUsePackageDescription() + "'" +
            ", packageDescription='" + getPackageDescription() + "'" +
            ", displayRecommendPackage='" + getDisplayRecommendPackage() + "'" +
            ", packageThumbnailUrl='" + getPackageThumbnailUrl() + "'" +
            ", price='" + getPrice() + "'" +
            ", quantity=" + getQuantity() +
            ", status='" + getStatus() + "'" +
            ", optionCode='" + getOptionCode() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
