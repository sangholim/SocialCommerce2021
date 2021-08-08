package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link com.toy.project.domain.ProductOption} entity.
 */
public class ProductOptionDTO implements Serializable {

    private Long id;

    private String name;

    private String packageName;

    private String designName;

    private String colorCode;

    private String colorName;

    private Boolean usePackageDescription;

    @Lob
    private String packageDescription;

    private Boolean displayRecommendPackage;

    private String packageThumbnailUrl;

    private MultipartFile packageThumbnail;

    private String price;

    private Long quantity;

    @Size(max = 50)
    private String status;

    private String optionCode;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDesignName() {
        return designName;
    }

    public void setDesignName(String designName) {
        this.designName = designName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Boolean getUsePackageDescription() {
        return usePackageDescription;
    }

    public void setUsePackageDescription(Boolean usePackageDescription) {
        this.usePackageDescription = usePackageDescription;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Boolean getDisplayRecommendPackage() {
        return displayRecommendPackage;
    }

    public void setDisplayRecommendPackage(Boolean displayRecommendPackage) {
        this.displayRecommendPackage = displayRecommendPackage;
    }

    public String getPackageThumbnailUrl() {
        return packageThumbnailUrl;
    }

    public void setPackageThumbnailUrl(String packageThumbnailUrl) {
        this.packageThumbnailUrl = packageThumbnailUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOptionCode() {
        return optionCode;
    }

    public void setOptionCode(String optionCode) {
        this.optionCode = optionCode;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public MultipartFile getPackageThumbnail() {
        return packageThumbnail;
    }

    public void setPackageThumbnail(MultipartFile packageThumbnail) {
        this.packageThumbnail = packageThumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOptionDTO)) {
            return false;
        }

        ProductOptionDTO productOptionDTO = (ProductOptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productOptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "ProductOptionDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", packageName='" +
            packageName +
            '\'' +
            ", designName='" +
            designName +
            '\'' +
            ", colorCode='" +
            colorCode +
            '\'' +
            ", colorName='" +
            colorName +
            '\'' +
            ", usePackageDescription=" +
            usePackageDescription +
            ", packageDescription='" +
            packageDescription +
            '\'' +
            ", displayRecommendPackage=" +
            displayRecommendPackage +
            ", packageThumbnailUrl='" +
            packageThumbnailUrl +
            '\'' +
            ", packageThumbnail=" +
            packageThumbnail +
            ", price='" +
            price +
            '\'' +
            ", quantity=" +
            quantity +
            ", status='" +
            status +
            '\'' +
            ", optionCode='" +
            optionCode +
            '\'' +
            ", activated=" +
            activated +
            ", createdBy='" +
            createdBy +
            '\'' +
            ", createdDate=" +
            createdDate +
            ", lastModifiedBy='" +
            lastModifiedBy +
            '\'' +
            ", lastModifiedDate=" +
            lastModifiedDate +
            ", productId=" +
            productId +
            '}'
        );
    }
}
