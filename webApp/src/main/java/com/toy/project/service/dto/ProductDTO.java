package com.toy.project.service.dto;

import com.toy.project.domain.enumeration.DifficultType;
import com.toy.project.domain.enumeration.ProductType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.toy.project.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String name;

    private DifficultType difficulty;

    private String thumbnail;

    private String owner;

    private Instant regdate;

    private Integer priceRegular;

    private Integer isUseDiscount;

    private String discountUnit;

    private Integer discountValue;

    private Instant discountStartdate;

    private Integer discountInterval;

    private String video;

    private Instant startdate;

    private String prepareResource;

    private String introduceResource;

    private String shippingResource;

    private String refundResource;

    private String changeResource;

    private String code;

    private Integer installmentMonth;

    private ProductType type;

    private String number;

    private Integer popularCount;

    private Integer reviewCount;

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

    public DifficultType getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultType difficulty) {
        this.difficulty = difficulty;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Instant getRegdate() {
        return regdate;
    }

    public void setRegdate(Instant regdate) {
        this.regdate = regdate;
    }

    public Integer getPriceRegular() {
        return priceRegular;
    }

    public void setPriceRegular(Integer priceRegular) {
        this.priceRegular = priceRegular;
    }

    public Integer getIsUseDiscount() {
        return isUseDiscount;
    }

    public void setIsUseDiscount(Integer isUseDiscount) {
        this.isUseDiscount = isUseDiscount;
    }

    public String getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
    }

    public Integer getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Integer discountValue) {
        this.discountValue = discountValue;
    }

    public Instant getDiscountStartdate() {
        return discountStartdate;
    }

    public void setDiscountStartdate(Instant discountStartdate) {
        this.discountStartdate = discountStartdate;
    }

    public Integer getDiscountInterval() {
        return discountInterval;
    }

    public void setDiscountInterval(Integer discountInterval) {
        this.discountInterval = discountInterval;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Instant getStartdate() {
        return startdate;
    }

    public void setStartdate(Instant startdate) {
        this.startdate = startdate;
    }

    public String getPrepareResource() {
        return prepareResource;
    }

    public void setPrepareResource(String prepareResource) {
        this.prepareResource = prepareResource;
    }

    public String getIntroduceResource() {
        return introduceResource;
    }

    public void setIntroduceResource(String introduceResource) {
        this.introduceResource = introduceResource;
    }

    public String getShippingResource() {
        return shippingResource;
    }

    public void setShippingResource(String shippingResource) {
        this.shippingResource = shippingResource;
    }

    public String getRefundResource() {
        return refundResource;
    }

    public void setRefundResource(String refundResource) {
        this.refundResource = refundResource;
    }

    public String getChangeResource() {
        return changeResource;
    }

    public void setChangeResource(String changeResource) {
        this.changeResource = changeResource;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPopularCount() {
        return popularCount;
    }

    public void setPopularCount(Integer popularCount) {
        this.popularCount = popularCount;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", owner='" + getOwner() + "'" +
            ", regdate='" + getRegdate() + "'" +
            ", priceRegular=" + getPriceRegular() +
            ", isUseDiscount=" + getIsUseDiscount() +
            ", discountUnit='" + getDiscountUnit() + "'" +
            ", discountValue=" + getDiscountValue() +
            ", discountStartdate='" + getDiscountStartdate() + "'" +
            ", discountInterval=" + getDiscountInterval() +
            ", video='" + getVideo() + "'" +
            ", startdate='" + getStartdate() + "'" +
            ", prepareResource='" + getPrepareResource() + "'" +
            ", introduceResource='" + getIntroduceResource() + "'" +
            ", shippingResource='" + getShippingResource() + "'" +
            ", refundResource='" + getRefundResource() + "'" +
            ", changeResource='" + getChangeResource() + "'" +
            ", code='" + getCode() + "'" +
            ", installmentMonth=" + getInstallmentMonth() +
            ", type='" + getType() + "'" +
            ", number='" + getNumber() + "'" +
            ", popularCount=" + getPopularCount() +
            ", reviewCount=" + getReviewCount() +
            "}";
    }
}
