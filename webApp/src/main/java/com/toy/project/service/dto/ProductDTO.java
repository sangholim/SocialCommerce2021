package com.toy.project.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String calculation;

    private Instant calculationDateFrom;

    private Instant calculationDateTo;

    private Integer price;

    private String allPriceUnit;

    private String discount;

    private String discountPrice;

    private String discountUnit;

    private Instant discountDateFrom;

    private Instant discountDateTo;

    private Boolean isInstallment;

    private Integer installmentMonth;

    private Boolean isSell;

    private Instant sellDateFrom;

    private Instant sellDateTo;

    private Integer minPurchaseAmount;

    private Integer manPurchaseAmount;

    private String mainImageFileUrl;

    @Lob
    private String addImageFileUrl;

    private String mainVideoFileUrl;

    private String descriptionFileUrl;

    private String shippingType;

    private String separateShippingPriceType;

    private Integer defaultShippingPrice;

    private Integer freeShippingPrice;

    private Integer jejuShippingPrice;

    private Integer difficultShippingPrice;

    private Integer refundShippingPrice;

    private Integer exchangeShippingPrice;

    private String exchangeShippingFileUrl;

    private Boolean isView;

    private Instant viewReservationDate;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Instant getCalculationDateFrom() {
        return calculationDateFrom;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return calculationDateTo;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAllPriceUnit() {
        return allPriceUnit;
    }

    public void setAllPriceUnit(String allPriceUnit) {
        this.allPriceUnit = allPriceUnit;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountUnit() {
        return discountUnit;
    }

    public void setDiscountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
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

    public Boolean getIsInstallment() {
        return isInstallment;
    }

    public void setIsInstallment(Boolean isInstallment) {
        this.isInstallment = isInstallment;
    }

    public Integer getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public Boolean getIsSell() {
        return isSell;
    }

    public void setIsSell(Boolean isSell) {
        this.isSell = isSell;
    }

    public Instant getSellDateFrom() {
        return sellDateFrom;
    }

    public void setSellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
    }

    public Instant getSellDateTo() {
        return sellDateTo;
    }

    public void setSellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
    }

    public Integer getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Integer getManPurchaseAmount() {
        return manPurchaseAmount;
    }

    public void setManPurchaseAmount(Integer manPurchaseAmount) {
        this.manPurchaseAmount = manPurchaseAmount;
    }

    public String getMainImageFileUrl() {
        return mainImageFileUrl;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public String getAddImageFileUrl() {
        return addImageFileUrl;
    }

    public void setAddImageFileUrl(String addImageFileUrl) {
        this.addImageFileUrl = addImageFileUrl;
    }

    public String getMainVideoFileUrl() {
        return mainVideoFileUrl;
    }

    public void setMainVideoFileUrl(String mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
    }

    public String getDescriptionFileUrl() {
        return descriptionFileUrl;
    }

    public void setDescriptionFileUrl(String descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getSeparateShippingPriceType() {
        return separateShippingPriceType;
    }

    public void setSeparateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public Integer getDefaultShippingPrice() {
        return defaultShippingPrice;
    }

    public void setDefaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Integer getFreeShippingPrice() {
        return freeShippingPrice;
    }

    public void setFreeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public Integer getJejuShippingPrice() {
        return jejuShippingPrice;
    }

    public void setJejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public Integer getDifficultShippingPrice() {
        return difficultShippingPrice;
    }

    public void setDifficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public Integer getRefundShippingPrice() {
        return refundShippingPrice;
    }

    public void setRefundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public Integer getExchangeShippingPrice() {
        return exchangeShippingPrice;
    }

    public void setExchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
    }

    public String getExchangeShippingFileUrl() {
        return exchangeShippingFileUrl;
    }

    public void setExchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
    }

    public Boolean getIsView() {
        return isView;
    }

    public void setIsView(Boolean isView) {
        this.isView = isView;
    }

    public Instant getViewReservationDate() {
        return viewReservationDate;
    }

    public void setViewReservationDate(Instant viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
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
            ", code='" + getCode() + "'" +
            ", calculation='" + getCalculation() + "'" +
            ", calculationDateFrom='" + getCalculationDateFrom() + "'" +
            ", calculationDateTo='" + getCalculationDateTo() + "'" +
            ", price=" + getPrice() +
            ", allPriceUnit='" + getAllPriceUnit() + "'" +
            ", discount='" + getDiscount() + "'" +
            ", discountPrice='" + getDiscountPrice() + "'" +
            ", discountUnit='" + getDiscountUnit() + "'" +
            ", discountDateFrom='" + getDiscountDateFrom() + "'" +
            ", discountDateTo='" + getDiscountDateTo() + "'" +
            ", isInstallment='" + getIsInstallment() + "'" +
            ", installmentMonth=" + getInstallmentMonth() +
            ", isSell='" + getIsSell() + "'" +
            ", sellDateFrom='" + getSellDateFrom() + "'" +
            ", sellDateTo='" + getSellDateTo() + "'" +
            ", minPurchaseAmount=" + getMinPurchaseAmount() +
            ", manPurchaseAmount=" + getManPurchaseAmount() +
            ", mainImageFileUrl='" + getMainImageFileUrl() + "'" +
            ", addImageFileUrl='" + getAddImageFileUrl() + "'" +
            ", mainVideoFileUrl='" + getMainVideoFileUrl() + "'" +
            ", descriptionFileUrl='" + getDescriptionFileUrl() + "'" +
            ", shippingType='" + getShippingType() + "'" +
            ", separateShippingPriceType='" + getSeparateShippingPriceType() + "'" +
            ", defaultShippingPrice=" + getDefaultShippingPrice() +
            ", freeShippingPrice=" + getFreeShippingPrice() +
            ", jejuShippingPrice=" + getJejuShippingPrice() +
            ", difficultShippingPrice=" + getDifficultShippingPrice() +
            ", refundShippingPrice=" + getRefundShippingPrice() +
            ", exchangeShippingPrice=" + getExchangeShippingPrice() +
            ", exchangeShippingFileUrl='" + getExchangeShippingFileUrl() + "'" +
            ", isView='" + getIsView() + "'" +
            ", viewReservationDate='" + getViewReservationDate() + "'" +
            ", activated='" + getActivated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
