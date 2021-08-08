package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.ProductDiscount} entity.
 */
public class ProductDiscountDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String discountDevice;

    private Long discountPrice;

    private String discountPriceUnit;

    private Boolean useDiscountDate;

    private Instant discountDateFrom;

    private Instant discountDateTo;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private String reducePrice;

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

    public String getDiscountDevice() {
        return discountDevice;
    }

    public void setDiscountDevice(String discountDevice) {
        this.discountDevice = discountDevice;
    }

    public Long getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Long discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountPriceUnit() {
        return discountPriceUnit;
    }

    public void setDiscountPriceUnit(String discountPriceUnit) {
        this.discountPriceUnit = discountPriceUnit;
    }

    public Boolean getUseDiscountDate() {
        return useDiscountDate;
    }

    public void setUseDiscountDate(Boolean useDiscountDate) {
        this.useDiscountDate = useDiscountDate;
    }

    public Instant getDiscountDateFrom() {
        return discountDateFrom;
    }

    public void setDiscountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
    }

    public Instant getDiscountDateTo() {
        return discountDateTo;
    }

    public void setDiscountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
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

    public String getReducePrice() {
        return reducePrice;
    }

    public void setReducePrice(String reducePrice) {
        this.reducePrice = reducePrice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDiscountDTO)) {
            return false;
        }

        ProductDiscountDTO productDiscountDTO = (ProductDiscountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDiscountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "ProductDiscountDTO{" +
            "id=" + id +
            ", discountDevice='" + discountDevice + '\'' +
            ", discountPrice=" + discountPrice +
            ", discountPriceUnit='" + discountPriceUnit + '\'' +
            ", useDiscountDate=" + useDiscountDate +
            ", discountDateFrom=" + discountDateFrom +
            ", discountDateTo=" + discountDateTo +
            ", activated=" + activated +
            ", createdBy='" + createdBy + '\'' +
            ", reducePrice='" + reducePrice + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", productId=" + productId +
            '}';
    }
}
