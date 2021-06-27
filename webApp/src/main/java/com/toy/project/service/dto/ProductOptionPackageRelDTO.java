package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductOptionPackageRel} entity.
 */
public class ProductOptionPackageRelDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ProductOptionDTO productOption;

    private OptionPackageDTO optionPackage;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ProductOptionDTO getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOptionDTO productOption) {
        this.productOption = productOption;
    }

    public OptionPackageDTO getOptionPackage() {
        return optionPackage;
    }

    public void setOptionPackage(OptionPackageDTO optionPackage) {
        this.optionPackage = optionPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductOptionPackageRelDTO)) {
            return false;
        }

        ProductOptionPackageRelDTO productOptionPackageRelDTO = (ProductOptionPackageRelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productOptionPackageRelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductOptionPackageRelDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", productOption=" + getProductOption() +
            ", optionPackage=" + getOptionPackage() +
            "}";
    }
}
