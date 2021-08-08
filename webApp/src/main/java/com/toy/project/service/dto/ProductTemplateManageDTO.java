package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductTemplateManage} entity.
 */
public class ProductTemplateManageDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    @Size(max = 50)
    private String illegalUsageType;

    private String illegalUsageUrl;

    @Size(max = 50)
    private String exchangeType;

    private String exchangeUrl;

    private String shippingReleaseType;

    @Size(max = 50)
    private String separateShippingPriceType;

    @Size(max = 50)
    private String bundleShippingType;

    private Long defaultShippingPrice;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

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

    public String getIllegalUsageType() {
        return illegalUsageType;
    }

    public void setIllegalUsageType(String illegalUsageType) {
        this.illegalUsageType = illegalUsageType;
    }

    public String getIllegalUsageUrl() {
        return illegalUsageUrl;
    }

    public void setIllegalUsageUrl(String illegalUsageUrl) {
        this.illegalUsageUrl = illegalUsageUrl;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public String getExchangeUrl() {
        return exchangeUrl;
    }

    public void setExchangeUrl(String exchangeUrl) {
        this.exchangeUrl = exchangeUrl;
    }

    public String getShippingReleaseType() {
        return shippingReleaseType;
    }

    public void setShippingReleaseType(String shippingReleaseType) {
        this.shippingReleaseType = shippingReleaseType;
    }

    public String getSeparateShippingPriceType() {
        return separateShippingPriceType;
    }

    public void setSeparateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public String getBundleShippingType() {
        return bundleShippingType;
    }

    public void setBundleShippingType(String bundleShippingType) {
        this.bundleShippingType = bundleShippingType;
    }

    public Long getDefaultShippingPrice() {
        return defaultShippingPrice;
    }

    public void setDefaultShippingPrice(Long defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductTemplateManageDTO)) {
            return false;
        }

        ProductTemplateManageDTO productTemplateManageDTO = (ProductTemplateManageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productTemplateManageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductTemplateManageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", illegalUsageType='" + getIllegalUsageType() + "'" +
            ", illegalUsageUrl='" + getIllegalUsageUrl() + "'" +
            ", exchangeType='" + getExchangeType() + "'" +
            ", exchangeUrl='" + getExchangeUrl() + "'" +
            ", shippingReleaseType='" + getShippingReleaseType() + "'" +
            ", separateShippingPriceType='" + getSeparateShippingPriceType() + "'" +
            ", bundleShippingType='" + getBundleShippingType() + "'" +
            ", defaultShippingPrice=" + getDefaultShippingPrice() +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
