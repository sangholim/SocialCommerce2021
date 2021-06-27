package com.toy.project.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.toy.project.domain.Product} entity. This class is used
 * in {@link com.toy.project.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private StringFilter calculation;

    private InstantFilter calculationDateFrom;

    private InstantFilter calculationDateTo;

    private IntegerFilter price;

    private StringFilter allPriceUnit;

    private StringFilter discount;

    private StringFilter discountPrice;

    private StringFilter discountUnit;

    private InstantFilter discountDateFrom;

    private InstantFilter discountDateTo;

    private BooleanFilter isInstallment;

    private IntegerFilter installmentMonth;

    private BooleanFilter isSell;

    private InstantFilter sellDateFrom;

    private InstantFilter sellDateTo;

    private IntegerFilter minPurchaseAmount;

    private IntegerFilter manPurchaseAmount;

    private StringFilter mainImageFileUrl;

    private StringFilter mainVideoFileUrl;

    private StringFilter descriptionFileUrl;

    private StringFilter shippingType;

    private StringFilter separateShippingPriceType;

    private IntegerFilter defaultShippingPrice;

    private IntegerFilter freeShippingPrice;

    private IntegerFilter jejuShippingPrice;

    private IntegerFilter difficultShippingPrice;

    private IntegerFilter refundShippingPrice;

    private IntegerFilter exchangeShippingPrice;

    private StringFilter exchangeShippingFileUrl;

    private BooleanFilter isView;

    private InstantFilter viewReservationDate;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productCategoryRelId;

    private LongFilter productLabelRelId;

    private LongFilter productMappingRelId;

    private LongFilter productViewRelId;

    private LongFilter productNoticeRelId;

    private LongFilter productShippingRelId;

    private LongFilter productTemplateRelId;

    private LongFilter productOptionRelId;

    private LongFilter productClazzRelId;

    private LongFilter productStoreRelId;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.calculation = other.calculation == null ? null : other.calculation.copy();
        this.calculationDateFrom = other.calculationDateFrom == null ? null : other.calculationDateFrom.copy();
        this.calculationDateTo = other.calculationDateTo == null ? null : other.calculationDateTo.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.allPriceUnit = other.allPriceUnit == null ? null : other.allPriceUnit.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.discountPrice = other.discountPrice == null ? null : other.discountPrice.copy();
        this.discountUnit = other.discountUnit == null ? null : other.discountUnit.copy();
        this.discountDateFrom = other.discountDateFrom == null ? null : other.discountDateFrom.copy();
        this.discountDateTo = other.discountDateTo == null ? null : other.discountDateTo.copy();
        this.isInstallment = other.isInstallment == null ? null : other.isInstallment.copy();
        this.installmentMonth = other.installmentMonth == null ? null : other.installmentMonth.copy();
        this.isSell = other.isSell == null ? null : other.isSell.copy();
        this.sellDateFrom = other.sellDateFrom == null ? null : other.sellDateFrom.copy();
        this.sellDateTo = other.sellDateTo == null ? null : other.sellDateTo.copy();
        this.minPurchaseAmount = other.minPurchaseAmount == null ? null : other.minPurchaseAmount.copy();
        this.manPurchaseAmount = other.manPurchaseAmount == null ? null : other.manPurchaseAmount.copy();
        this.mainImageFileUrl = other.mainImageFileUrl == null ? null : other.mainImageFileUrl.copy();
        this.mainVideoFileUrl = other.mainVideoFileUrl == null ? null : other.mainVideoFileUrl.copy();
        this.descriptionFileUrl = other.descriptionFileUrl == null ? null : other.descriptionFileUrl.copy();
        this.shippingType = other.shippingType == null ? null : other.shippingType.copy();
        this.separateShippingPriceType = other.separateShippingPriceType == null ? null : other.separateShippingPriceType.copy();
        this.defaultShippingPrice = other.defaultShippingPrice == null ? null : other.defaultShippingPrice.copy();
        this.freeShippingPrice = other.freeShippingPrice == null ? null : other.freeShippingPrice.copy();
        this.jejuShippingPrice = other.jejuShippingPrice == null ? null : other.jejuShippingPrice.copy();
        this.difficultShippingPrice = other.difficultShippingPrice == null ? null : other.difficultShippingPrice.copy();
        this.refundShippingPrice = other.refundShippingPrice == null ? null : other.refundShippingPrice.copy();
        this.exchangeShippingPrice = other.exchangeShippingPrice == null ? null : other.exchangeShippingPrice.copy();
        this.exchangeShippingFileUrl = other.exchangeShippingFileUrl == null ? null : other.exchangeShippingFileUrl.copy();
        this.isView = other.isView == null ? null : other.isView.copy();
        this.viewReservationDate = other.viewReservationDate == null ? null : other.viewReservationDate.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productCategoryRelId = other.productCategoryRelId == null ? null : other.productCategoryRelId.copy();
        this.productLabelRelId = other.productLabelRelId == null ? null : other.productLabelRelId.copy();
        this.productMappingRelId = other.productMappingRelId == null ? null : other.productMappingRelId.copy();
        this.productViewRelId = other.productViewRelId == null ? null : other.productViewRelId.copy();
        this.productNoticeRelId = other.productNoticeRelId == null ? null : other.productNoticeRelId.copy();
        this.productShippingRelId = other.productShippingRelId == null ? null : other.productShippingRelId.copy();
        this.productTemplateRelId = other.productTemplateRelId == null ? null : other.productTemplateRelId.copy();
        this.productOptionRelId = other.productOptionRelId == null ? null : other.productOptionRelId.copy();
        this.productClazzRelId = other.productClazzRelId == null ? null : other.productClazzRelId.copy();
        this.productStoreRelId = other.productStoreRelId == null ? null : other.productStoreRelId.copy();
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getCalculation() {
        return calculation;
    }

    public StringFilter calculation() {
        if (calculation == null) {
            calculation = new StringFilter();
        }
        return calculation;
    }

    public void setCalculation(StringFilter calculation) {
        this.calculation = calculation;
    }

    public InstantFilter getCalculationDateFrom() {
        return calculationDateFrom;
    }

    public InstantFilter calculationDateFrom() {
        if (calculationDateFrom == null) {
            calculationDateFrom = new InstantFilter();
        }
        return calculationDateFrom;
    }

    public void setCalculationDateFrom(InstantFilter calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public InstantFilter getCalculationDateTo() {
        return calculationDateTo;
    }

    public InstantFilter calculationDateTo() {
        if (calculationDateTo == null) {
            calculationDateTo = new InstantFilter();
        }
        return calculationDateTo;
    }

    public void setCalculationDateTo(InstantFilter calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public IntegerFilter getPrice() {
        return price;
    }

    public IntegerFilter price() {
        if (price == null) {
            price = new IntegerFilter();
        }
        return price;
    }

    public void setPrice(IntegerFilter price) {
        this.price = price;
    }

    public StringFilter getAllPriceUnit() {
        return allPriceUnit;
    }

    public StringFilter allPriceUnit() {
        if (allPriceUnit == null) {
            allPriceUnit = new StringFilter();
        }
        return allPriceUnit;
    }

    public void setAllPriceUnit(StringFilter allPriceUnit) {
        this.allPriceUnit = allPriceUnit;
    }

    public StringFilter getDiscount() {
        return discount;
    }

    public StringFilter discount() {
        if (discount == null) {
            discount = new StringFilter();
        }
        return discount;
    }

    public void setDiscount(StringFilter discount) {
        this.discount = discount;
    }

    public StringFilter getDiscountPrice() {
        return discountPrice;
    }

    public StringFilter discountPrice() {
        if (discountPrice == null) {
            discountPrice = new StringFilter();
        }
        return discountPrice;
    }

    public void setDiscountPrice(StringFilter discountPrice) {
        this.discountPrice = discountPrice;
    }

    public StringFilter getDiscountUnit() {
        return discountUnit;
    }

    public StringFilter discountUnit() {
        if (discountUnit == null) {
            discountUnit = new StringFilter();
        }
        return discountUnit;
    }

    public void setDiscountUnit(StringFilter discountUnit) {
        this.discountUnit = discountUnit;
    }

    public InstantFilter getDiscountDateFrom() {
        return discountDateFrom;
    }

    public InstantFilter discountDateFrom() {
        if (discountDateFrom == null) {
            discountDateFrom = new InstantFilter();
        }
        return discountDateFrom;
    }

    public void setDiscountDateFrom(InstantFilter discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
    }

    public InstantFilter getDiscountDateTo() {
        return discountDateTo;
    }

    public InstantFilter discountDateTo() {
        if (discountDateTo == null) {
            discountDateTo = new InstantFilter();
        }
        return discountDateTo;
    }

    public void setDiscountDateTo(InstantFilter discountDateTo) {
        this.discountDateTo = discountDateTo;
    }

    public BooleanFilter getIsInstallment() {
        return isInstallment;
    }

    public BooleanFilter isInstallment() {
        if (isInstallment == null) {
            isInstallment = new BooleanFilter();
        }
        return isInstallment;
    }

    public void setIsInstallment(BooleanFilter isInstallment) {
        this.isInstallment = isInstallment;
    }

    public IntegerFilter getInstallmentMonth() {
        return installmentMonth;
    }

    public IntegerFilter installmentMonth() {
        if (installmentMonth == null) {
            installmentMonth = new IntegerFilter();
        }
        return installmentMonth;
    }

    public void setInstallmentMonth(IntegerFilter installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public BooleanFilter getIsSell() {
        return isSell;
    }

    public BooleanFilter isSell() {
        if (isSell == null) {
            isSell = new BooleanFilter();
        }
        return isSell;
    }

    public void setIsSell(BooleanFilter isSell) {
        this.isSell = isSell;
    }

    public InstantFilter getSellDateFrom() {
        return sellDateFrom;
    }

    public InstantFilter sellDateFrom() {
        if (sellDateFrom == null) {
            sellDateFrom = new InstantFilter();
        }
        return sellDateFrom;
    }

    public void setSellDateFrom(InstantFilter sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
    }

    public InstantFilter getSellDateTo() {
        return sellDateTo;
    }

    public InstantFilter sellDateTo() {
        if (sellDateTo == null) {
            sellDateTo = new InstantFilter();
        }
        return sellDateTo;
    }

    public void setSellDateTo(InstantFilter sellDateTo) {
        this.sellDateTo = sellDateTo;
    }

    public IntegerFilter getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public IntegerFilter minPurchaseAmount() {
        if (minPurchaseAmount == null) {
            minPurchaseAmount = new IntegerFilter();
        }
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(IntegerFilter minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public IntegerFilter getManPurchaseAmount() {
        return manPurchaseAmount;
    }

    public IntegerFilter manPurchaseAmount() {
        if (manPurchaseAmount == null) {
            manPurchaseAmount = new IntegerFilter();
        }
        return manPurchaseAmount;
    }

    public void setManPurchaseAmount(IntegerFilter manPurchaseAmount) {
        this.manPurchaseAmount = manPurchaseAmount;
    }

    public StringFilter getMainImageFileUrl() {
        return mainImageFileUrl;
    }

    public StringFilter mainImageFileUrl() {
        if (mainImageFileUrl == null) {
            mainImageFileUrl = new StringFilter();
        }
        return mainImageFileUrl;
    }

    public void setMainImageFileUrl(StringFilter mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public StringFilter getMainVideoFileUrl() {
        return mainVideoFileUrl;
    }

    public StringFilter mainVideoFileUrl() {
        if (mainVideoFileUrl == null) {
            mainVideoFileUrl = new StringFilter();
        }
        return mainVideoFileUrl;
    }

    public void setMainVideoFileUrl(StringFilter mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
    }

    public StringFilter getDescriptionFileUrl() {
        return descriptionFileUrl;
    }

    public StringFilter descriptionFileUrl() {
        if (descriptionFileUrl == null) {
            descriptionFileUrl = new StringFilter();
        }
        return descriptionFileUrl;
    }

    public void setDescriptionFileUrl(StringFilter descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
    }

    public StringFilter getShippingType() {
        return shippingType;
    }

    public StringFilter shippingType() {
        if (shippingType == null) {
            shippingType = new StringFilter();
        }
        return shippingType;
    }

    public void setShippingType(StringFilter shippingType) {
        this.shippingType = shippingType;
    }

    public StringFilter getSeparateShippingPriceType() {
        return separateShippingPriceType;
    }

    public StringFilter separateShippingPriceType() {
        if (separateShippingPriceType == null) {
            separateShippingPriceType = new StringFilter();
        }
        return separateShippingPriceType;
    }

    public void setSeparateShippingPriceType(StringFilter separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public IntegerFilter getDefaultShippingPrice() {
        return defaultShippingPrice;
    }

    public IntegerFilter defaultShippingPrice() {
        if (defaultShippingPrice == null) {
            defaultShippingPrice = new IntegerFilter();
        }
        return defaultShippingPrice;
    }

    public void setDefaultShippingPrice(IntegerFilter defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public IntegerFilter getFreeShippingPrice() {
        return freeShippingPrice;
    }

    public IntegerFilter freeShippingPrice() {
        if (freeShippingPrice == null) {
            freeShippingPrice = new IntegerFilter();
        }
        return freeShippingPrice;
    }

    public void setFreeShippingPrice(IntegerFilter freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public IntegerFilter getJejuShippingPrice() {
        return jejuShippingPrice;
    }

    public IntegerFilter jejuShippingPrice() {
        if (jejuShippingPrice == null) {
            jejuShippingPrice = new IntegerFilter();
        }
        return jejuShippingPrice;
    }

    public void setJejuShippingPrice(IntegerFilter jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public IntegerFilter getDifficultShippingPrice() {
        return difficultShippingPrice;
    }

    public IntegerFilter difficultShippingPrice() {
        if (difficultShippingPrice == null) {
            difficultShippingPrice = new IntegerFilter();
        }
        return difficultShippingPrice;
    }

    public void setDifficultShippingPrice(IntegerFilter difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public IntegerFilter getRefundShippingPrice() {
        return refundShippingPrice;
    }

    public IntegerFilter refundShippingPrice() {
        if (refundShippingPrice == null) {
            refundShippingPrice = new IntegerFilter();
        }
        return refundShippingPrice;
    }

    public void setRefundShippingPrice(IntegerFilter refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public IntegerFilter getExchangeShippingPrice() {
        return exchangeShippingPrice;
    }

    public IntegerFilter exchangeShippingPrice() {
        if (exchangeShippingPrice == null) {
            exchangeShippingPrice = new IntegerFilter();
        }
        return exchangeShippingPrice;
    }

    public void setExchangeShippingPrice(IntegerFilter exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
    }

    public StringFilter getExchangeShippingFileUrl() {
        return exchangeShippingFileUrl;
    }

    public StringFilter exchangeShippingFileUrl() {
        if (exchangeShippingFileUrl == null) {
            exchangeShippingFileUrl = new StringFilter();
        }
        return exchangeShippingFileUrl;
    }

    public void setExchangeShippingFileUrl(StringFilter exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
    }

    public BooleanFilter getIsView() {
        return isView;
    }

    public BooleanFilter isView() {
        if (isView == null) {
            isView = new BooleanFilter();
        }
        return isView;
    }

    public void setIsView(BooleanFilter isView) {
        this.isView = isView;
    }

    public InstantFilter getViewReservationDate() {
        return viewReservationDate;
    }

    public InstantFilter viewReservationDate() {
        if (viewReservationDate == null) {
            viewReservationDate = new InstantFilter();
        }
        return viewReservationDate;
    }

    public void setViewReservationDate(InstantFilter viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            lastModifiedDate = new InstantFilter();
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public LongFilter getProductCategoryRelId() {
        return productCategoryRelId;
    }

    public LongFilter productCategoryRelId() {
        if (productCategoryRelId == null) {
            productCategoryRelId = new LongFilter();
        }
        return productCategoryRelId;
    }

    public void setProductCategoryRelId(LongFilter productCategoryRelId) {
        this.productCategoryRelId = productCategoryRelId;
    }

    public LongFilter getProductLabelRelId() {
        return productLabelRelId;
    }

    public LongFilter productLabelRelId() {
        if (productLabelRelId == null) {
            productLabelRelId = new LongFilter();
        }
        return productLabelRelId;
    }

    public void setProductLabelRelId(LongFilter productLabelRelId) {
        this.productLabelRelId = productLabelRelId;
    }

    public LongFilter getProductMappingRelId() {
        return productMappingRelId;
    }

    public LongFilter productMappingRelId() {
        if (productMappingRelId == null) {
            productMappingRelId = new LongFilter();
        }
        return productMappingRelId;
    }

    public void setProductMappingRelId(LongFilter productMappingRelId) {
        this.productMappingRelId = productMappingRelId;
    }

    public LongFilter getProductViewRelId() {
        return productViewRelId;
    }

    public LongFilter productViewRelId() {
        if (productViewRelId == null) {
            productViewRelId = new LongFilter();
        }
        return productViewRelId;
    }

    public void setProductViewRelId(LongFilter productViewRelId) {
        this.productViewRelId = productViewRelId;
    }

    public LongFilter getProductNoticeRelId() {
        return productNoticeRelId;
    }

    public LongFilter productNoticeRelId() {
        if (productNoticeRelId == null) {
            productNoticeRelId = new LongFilter();
        }
        return productNoticeRelId;
    }

    public void setProductNoticeRelId(LongFilter productNoticeRelId) {
        this.productNoticeRelId = productNoticeRelId;
    }

    public LongFilter getProductShippingRelId() {
        return productShippingRelId;
    }

    public LongFilter productShippingRelId() {
        if (productShippingRelId == null) {
            productShippingRelId = new LongFilter();
        }
        return productShippingRelId;
    }

    public void setProductShippingRelId(LongFilter productShippingRelId) {
        this.productShippingRelId = productShippingRelId;
    }

    public LongFilter getProductTemplateRelId() {
        return productTemplateRelId;
    }

    public LongFilter productTemplateRelId() {
        if (productTemplateRelId == null) {
            productTemplateRelId = new LongFilter();
        }
        return productTemplateRelId;
    }

    public void setProductTemplateRelId(LongFilter productTemplateRelId) {
        this.productTemplateRelId = productTemplateRelId;
    }

    public LongFilter getProductOptionRelId() {
        return productOptionRelId;
    }

    public LongFilter productOptionRelId() {
        if (productOptionRelId == null) {
            productOptionRelId = new LongFilter();
        }
        return productOptionRelId;
    }

    public void setProductOptionRelId(LongFilter productOptionRelId) {
        this.productOptionRelId = productOptionRelId;
    }

    public LongFilter getProductClazzRelId() {
        return productClazzRelId;
    }

    public LongFilter productClazzRelId() {
        if (productClazzRelId == null) {
            productClazzRelId = new LongFilter();
        }
        return productClazzRelId;
    }

    public void setProductClazzRelId(LongFilter productClazzRelId) {
        this.productClazzRelId = productClazzRelId;
    }

    public LongFilter getProductStoreRelId() {
        return productStoreRelId;
    }

    public LongFilter productStoreRelId() {
        if (productStoreRelId == null) {
            productStoreRelId = new LongFilter();
        }
        return productStoreRelId;
    }

    public void setProductStoreRelId(LongFilter productStoreRelId) {
        this.productStoreRelId = productStoreRelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(calculation, that.calculation) &&
            Objects.equals(calculationDateFrom, that.calculationDateFrom) &&
            Objects.equals(calculationDateTo, that.calculationDateTo) &&
            Objects.equals(price, that.price) &&
            Objects.equals(allPriceUnit, that.allPriceUnit) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(discountPrice, that.discountPrice) &&
            Objects.equals(discountUnit, that.discountUnit) &&
            Objects.equals(discountDateFrom, that.discountDateFrom) &&
            Objects.equals(discountDateTo, that.discountDateTo) &&
            Objects.equals(isInstallment, that.isInstallment) &&
            Objects.equals(installmentMonth, that.installmentMonth) &&
            Objects.equals(isSell, that.isSell) &&
            Objects.equals(sellDateFrom, that.sellDateFrom) &&
            Objects.equals(sellDateTo, that.sellDateTo) &&
            Objects.equals(minPurchaseAmount, that.minPurchaseAmount) &&
            Objects.equals(manPurchaseAmount, that.manPurchaseAmount) &&
            Objects.equals(mainImageFileUrl, that.mainImageFileUrl) &&
            Objects.equals(mainVideoFileUrl, that.mainVideoFileUrl) &&
            Objects.equals(descriptionFileUrl, that.descriptionFileUrl) &&
            Objects.equals(shippingType, that.shippingType) &&
            Objects.equals(separateShippingPriceType, that.separateShippingPriceType) &&
            Objects.equals(defaultShippingPrice, that.defaultShippingPrice) &&
            Objects.equals(freeShippingPrice, that.freeShippingPrice) &&
            Objects.equals(jejuShippingPrice, that.jejuShippingPrice) &&
            Objects.equals(difficultShippingPrice, that.difficultShippingPrice) &&
            Objects.equals(refundShippingPrice, that.refundShippingPrice) &&
            Objects.equals(exchangeShippingPrice, that.exchangeShippingPrice) &&
            Objects.equals(exchangeShippingFileUrl, that.exchangeShippingFileUrl) &&
            Objects.equals(isView, that.isView) &&
            Objects.equals(viewReservationDate, that.viewReservationDate) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productCategoryRelId, that.productCategoryRelId) &&
            Objects.equals(productLabelRelId, that.productLabelRelId) &&
            Objects.equals(productMappingRelId, that.productMappingRelId) &&
            Objects.equals(productViewRelId, that.productViewRelId) &&
            Objects.equals(productNoticeRelId, that.productNoticeRelId) &&
            Objects.equals(productShippingRelId, that.productShippingRelId) &&
            Objects.equals(productTemplateRelId, that.productTemplateRelId) &&
            Objects.equals(productOptionRelId, that.productOptionRelId) &&
            Objects.equals(productClazzRelId, that.productClazzRelId) &&
            Objects.equals(productStoreRelId, that.productStoreRelId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            code,
            calculation,
            calculationDateFrom,
            calculationDateTo,
            price,
            allPriceUnit,
            discount,
            discountPrice,
            discountUnit,
            discountDateFrom,
            discountDateTo,
            isInstallment,
            installmentMonth,
            isSell,
            sellDateFrom,
            sellDateTo,
            minPurchaseAmount,
            manPurchaseAmount,
            mainImageFileUrl,
            mainVideoFileUrl,
            descriptionFileUrl,
            shippingType,
            separateShippingPriceType,
            defaultShippingPrice,
            freeShippingPrice,
            jejuShippingPrice,
            difficultShippingPrice,
            refundShippingPrice,
            exchangeShippingPrice,
            exchangeShippingFileUrl,
            isView,
            viewReservationDate,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productCategoryRelId,
            productLabelRelId,
            productMappingRelId,
            productViewRelId,
            productNoticeRelId,
            productShippingRelId,
            productTemplateRelId,
            productOptionRelId,
            productClazzRelId,
            productStoreRelId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (calculation != null ? "calculation=" + calculation + ", " : "") +
            (calculationDateFrom != null ? "calculationDateFrom=" + calculationDateFrom + ", " : "") +
            (calculationDateTo != null ? "calculationDateTo=" + calculationDateTo + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (allPriceUnit != null ? "allPriceUnit=" + allPriceUnit + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (discountPrice != null ? "discountPrice=" + discountPrice + ", " : "") +
            (discountUnit != null ? "discountUnit=" + discountUnit + ", " : "") +
            (discountDateFrom != null ? "discountDateFrom=" + discountDateFrom + ", " : "") +
            (discountDateTo != null ? "discountDateTo=" + discountDateTo + ", " : "") +
            (isInstallment != null ? "isInstallment=" + isInstallment + ", " : "") +
            (installmentMonth != null ? "installmentMonth=" + installmentMonth + ", " : "") +
            (isSell != null ? "isSell=" + isSell + ", " : "") +
            (sellDateFrom != null ? "sellDateFrom=" + sellDateFrom + ", " : "") +
            (sellDateTo != null ? "sellDateTo=" + sellDateTo + ", " : "") +
            (minPurchaseAmount != null ? "minPurchaseAmount=" + minPurchaseAmount + ", " : "") +
            (manPurchaseAmount != null ? "manPurchaseAmount=" + manPurchaseAmount + ", " : "") +
            (mainImageFileUrl != null ? "mainImageFileUrl=" + mainImageFileUrl + ", " : "") +
            (mainVideoFileUrl != null ? "mainVideoFileUrl=" + mainVideoFileUrl + ", " : "") +
            (descriptionFileUrl != null ? "descriptionFileUrl=" + descriptionFileUrl + ", " : "") +
            (shippingType != null ? "shippingType=" + shippingType + ", " : "") +
            (separateShippingPriceType != null ? "separateShippingPriceType=" + separateShippingPriceType + ", " : "") +
            (defaultShippingPrice != null ? "defaultShippingPrice=" + defaultShippingPrice + ", " : "") +
            (freeShippingPrice != null ? "freeShippingPrice=" + freeShippingPrice + ", " : "") +
            (jejuShippingPrice != null ? "jejuShippingPrice=" + jejuShippingPrice + ", " : "") +
            (difficultShippingPrice != null ? "difficultShippingPrice=" + difficultShippingPrice + ", " : "") +
            (refundShippingPrice != null ? "refundShippingPrice=" + refundShippingPrice + ", " : "") +
            (exchangeShippingPrice != null ? "exchangeShippingPrice=" + exchangeShippingPrice + ", " : "") +
            (exchangeShippingFileUrl != null ? "exchangeShippingFileUrl=" + exchangeShippingFileUrl + ", " : "") +
            (isView != null ? "isView=" + isView + ", " : "") +
            (viewReservationDate != null ? "viewReservationDate=" + viewReservationDate + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productCategoryRelId != null ? "productCategoryRelId=" + productCategoryRelId + ", " : "") +
            (productLabelRelId != null ? "productLabelRelId=" + productLabelRelId + ", " : "") +
            (productMappingRelId != null ? "productMappingRelId=" + productMappingRelId + ", " : "") +
            (productViewRelId != null ? "productViewRelId=" + productViewRelId + ", " : "") +
            (productNoticeRelId != null ? "productNoticeRelId=" + productNoticeRelId + ", " : "") +
            (productShippingRelId != null ? "productShippingRelId=" + productShippingRelId + ", " : "") +
            (productTemplateRelId != null ? "productTemplateRelId=" + productTemplateRelId + ", " : "") +
            (productOptionRelId != null ? "productOptionRelId=" + productOptionRelId + ", " : "") +
            (productClazzRelId != null ? "productClazzRelId=" + productClazzRelId + ", " : "") +
            (productStoreRelId != null ? "productStoreRelId=" + productStoreRelId + ", " : "") +
            "}";
    }
}
