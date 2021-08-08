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

    private StringFilter type;

    private StringFilter status;

    private StringFilter code;

    private BooleanFilter useClassOption;

    private IntegerFilter price;

    private BooleanFilter useDiscountInstant;

    private BooleanFilter useInstallment;

    private IntegerFilter installmentMonth;

    private BooleanFilter useSellDate;

    private InstantFilter sellDateFrom;

    private InstantFilter sellDateTo;

    private IntegerFilter quantity;

    private BooleanFilter useProductOption;

    private BooleanFilter useProductInputOption;

    private BooleanFilter useProductAddOption;

    private IntegerFilter minPurchaseAmount;

    private IntegerFilter maxPurchaseAmountPerCount;

    private IntegerFilter maxPurchaseAmountPerOne;

    private StringFilter mainImageFileUrl;

    private StringFilter mainVideoFileUrl;

    private BooleanFilter useProductAnnounce;

    private BooleanFilter useProductFaq;

    private StringFilter descriptionFileUrl;

    private StringFilter shippingReleaseType;

    private StringFilter shippingStandardStartTime;

    private StringFilter etcShippingContent;

    private StringFilter separateShippingPriceType;

    private StringFilter bundleShippingType;

    private IntegerFilter defaultShippingPrice;

    private IntegerFilter freeShippingPrice;

    private IntegerFilter jejuShippingPrice;

    private IntegerFilter difficultShippingPrice;

    private IntegerFilter refundShippingPrice;

    private IntegerFilter exchangeShippingPrice;

    private StringFilter exchangeShippingFileType;

    private StringFilter exchangeShippingFileUrl;

    private BooleanFilter useView;

    private BooleanFilter useViewReservation;

    private InstantFilter viewReservationDate;

    private BooleanFilter useProductNotice;

    private BooleanFilter useProductIllegal;

    private BooleanFilter useProductRecommend;

    private BooleanFilter useProductMapping;

    private BooleanFilter activated;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private LongFilter productDiscountId;

    private LongFilter productMappingId;

    private LongFilter productOptionId;

    private LongFilter productAddOptionId;

    private LongFilter productInputOptionId;

    private LongFilter productFaqId;

    private LongFilter productAnnounceId;

    private LongFilter productAddImageId;

    private LongFilter productLabelId;

    private LongFilter productTemplateId;

    private LongFilter productCategoryId;

    private LongFilter productNoticeManageId;

    private LongFilter productClazzAuthorId;

    private LongFilter productStoreId;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.useClassOption = other.useClassOption == null ? null : other.useClassOption.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.useDiscountInstant = other.useDiscountInstant == null ? null : other.useDiscountInstant.copy();
        this.useInstallment = other.useInstallment == null ? null : other.useInstallment.copy();
        this.installmentMonth = other.installmentMonth == null ? null : other.installmentMonth.copy();
        this.useSellDate = other.useSellDate == null ? null : other.useSellDate.copy();
        this.sellDateFrom = other.sellDateFrom == null ? null : other.sellDateFrom.copy();
        this.sellDateTo = other.sellDateTo == null ? null : other.sellDateTo.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.useProductOption = other.useProductOption == null ? null : other.useProductOption.copy();
        this.useProductInputOption = other.useProductInputOption == null ? null : other.useProductInputOption.copy();
        this.useProductAddOption = other.useProductAddOption == null ? null : other.useProductAddOption.copy();
        this.minPurchaseAmount = other.minPurchaseAmount == null ? null : other.minPurchaseAmount.copy();
        this.maxPurchaseAmountPerCount = other.maxPurchaseAmountPerCount == null ? null : other.maxPurchaseAmountPerCount.copy();
        this.maxPurchaseAmountPerOne = other.maxPurchaseAmountPerOne == null ? null : other.maxPurchaseAmountPerOne.copy();
        this.mainImageFileUrl = other.mainImageFileUrl == null ? null : other.mainImageFileUrl.copy();
        this.mainVideoFileUrl = other.mainVideoFileUrl == null ? null : other.mainVideoFileUrl.copy();
        this.useProductAnnounce = other.useProductAnnounce == null ? null : other.useProductAnnounce.copy();
        this.useProductFaq = other.useProductFaq == null ? null : other.useProductFaq.copy();
        this.descriptionFileUrl = other.descriptionFileUrl == null ? null : other.descriptionFileUrl.copy();
        this.shippingReleaseType = other.shippingReleaseType == null ? null : other.shippingReleaseType.copy();
        this.shippingStandardStartTime = other.shippingStandardStartTime == null ? null : other.shippingStandardStartTime.copy();
        this.etcShippingContent = other.etcShippingContent == null ? null : other.etcShippingContent.copy();
        this.separateShippingPriceType = other.separateShippingPriceType == null ? null : other.separateShippingPriceType.copy();
        this.bundleShippingType = other.bundleShippingType == null ? null : other.bundleShippingType.copy();
        this.defaultShippingPrice = other.defaultShippingPrice == null ? null : other.defaultShippingPrice.copy();
        this.freeShippingPrice = other.freeShippingPrice == null ? null : other.freeShippingPrice.copy();
        this.jejuShippingPrice = other.jejuShippingPrice == null ? null : other.jejuShippingPrice.copy();
        this.difficultShippingPrice = other.difficultShippingPrice == null ? null : other.difficultShippingPrice.copy();
        this.refundShippingPrice = other.refundShippingPrice == null ? null : other.refundShippingPrice.copy();
        this.exchangeShippingPrice = other.exchangeShippingPrice == null ? null : other.exchangeShippingPrice.copy();
        this.exchangeShippingFileType = other.exchangeShippingFileType == null ? null : other.exchangeShippingFileType.copy();
        this.exchangeShippingFileUrl = other.exchangeShippingFileUrl == null ? null : other.exchangeShippingFileUrl.copy();
        this.useView = other.useView == null ? null : other.useView.copy();
        this.useViewReservation = other.useViewReservation == null ? null : other.useViewReservation.copy();
        this.viewReservationDate = other.viewReservationDate == null ? null : other.viewReservationDate.copy();
        this.useProductNotice = other.useProductNotice == null ? null : other.useProductNotice.copy();
        this.useProductIllegal = other.useProductIllegal == null ? null : other.useProductIllegal.copy();
        this.useProductRecommend = other.useProductRecommend == null ? null : other.useProductRecommend.copy();
        this.useProductMapping = other.useProductMapping == null ? null : other.useProductMapping.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy();
        this.productDiscountId = other.productDiscountId == null ? null : other.productDiscountId.copy();
        this.productMappingId = other.productMappingId == null ? null : other.productMappingId.copy();
        this.productOptionId = other.productOptionId == null ? null : other.productOptionId.copy();
        this.productAddOptionId = other.productAddOptionId == null ? null : other.productAddOptionId.copy();
        this.productInputOptionId = other.productInputOptionId == null ? null : other.productInputOptionId.copy();
        this.productFaqId = other.productFaqId == null ? null : other.productFaqId.copy();
        this.productAnnounceId = other.productAnnounceId == null ? null : other.productAnnounceId.copy();
        this.productAddImageId = other.productAddImageId == null ? null : other.productAddImageId.copy();
        this.productLabelId = other.productLabelId == null ? null : other.productLabelId.copy();
        this.productTemplateId = other.productTemplateId == null ? null : other.productTemplateId.copy();
        this.productCategoryId = other.productCategoryId == null ? null : other.productCategoryId.copy();
        this.productNoticeManageId = other.productNoticeManageId == null ? null : other.productNoticeManageId.copy();
        this.productClazzAuthorId = other.productClazzAuthorId == null ? null : other.productClazzAuthorId.copy();
        this.productStoreId = other.productStoreId == null ? null : other.productStoreId.copy();
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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

    public BooleanFilter getUseClassOption() {
        return useClassOption;
    }

    public BooleanFilter useClassOption() {
        if (useClassOption == null) {
            useClassOption = new BooleanFilter();
        }
        return useClassOption;
    }

    public void setUseClassOption(BooleanFilter useClassOption) {
        this.useClassOption = useClassOption;
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

    public BooleanFilter getUseDiscountInstant() {
        return useDiscountInstant;
    }

    public BooleanFilter useDiscountInstant() {
        if (useDiscountInstant == null) {
            useDiscountInstant = new BooleanFilter();
        }
        return useDiscountInstant;
    }

    public void setUseDiscountInstant(BooleanFilter useDiscountInstant) {
        this.useDiscountInstant = useDiscountInstant;
    }

    public BooleanFilter getUseInstallment() {
        return useInstallment;
    }

    public BooleanFilter useInstallment() {
        if (useInstallment == null) {
            useInstallment = new BooleanFilter();
        }
        return useInstallment;
    }

    public void setUseInstallment(BooleanFilter useInstallment) {
        this.useInstallment = useInstallment;
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

    public BooleanFilter getUseSellDate() {
        return useSellDate;
    }

    public BooleanFilter useSellDate() {
        if (useSellDate == null) {
            useSellDate = new BooleanFilter();
        }
        return useSellDate;
    }

    public void setUseSellDate(BooleanFilter useSellDate) {
        this.useSellDate = useSellDate;
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

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public IntegerFilter quantity() {
        if (quantity == null) {
            quantity = new IntegerFilter();
        }
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public BooleanFilter getUseProductOption() {
        return useProductOption;
    }

    public BooleanFilter useProductOption() {
        if (useProductOption == null) {
            useProductOption = new BooleanFilter();
        }
        return useProductOption;
    }

    public void setUseProductOption(BooleanFilter useProductOption) {
        this.useProductOption = useProductOption;
    }

    public BooleanFilter getUseProductInputOption() {
        return useProductInputOption;
    }

    public BooleanFilter useProductInputOption() {
        if (useProductInputOption == null) {
            useProductInputOption = new BooleanFilter();
        }
        return useProductInputOption;
    }

    public void setUseProductInputOption(BooleanFilter useProductInputOption) {
        this.useProductInputOption = useProductInputOption;
    }

    public BooleanFilter getUseProductAddOption() {
        return useProductAddOption;
    }

    public BooleanFilter useProductAddOption() {
        if (useProductAddOption == null) {
            useProductAddOption = new BooleanFilter();
        }
        return useProductAddOption;
    }

    public void setUseProductAddOption(BooleanFilter useProductAddOption) {
        this.useProductAddOption = useProductAddOption;
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

    public IntegerFilter getMaxPurchaseAmountPerCount() {
        return maxPurchaseAmountPerCount;
    }

    public IntegerFilter maxPurchaseAmountPerCount() {
        if (maxPurchaseAmountPerCount == null) {
            maxPurchaseAmountPerCount = new IntegerFilter();
        }
        return maxPurchaseAmountPerCount;
    }

    public void setMaxPurchaseAmountPerCount(IntegerFilter maxPurchaseAmountPerCount) {
        this.maxPurchaseAmountPerCount = maxPurchaseAmountPerCount;
    }

    public IntegerFilter getMaxPurchaseAmountPerOne() {
        return maxPurchaseAmountPerOne;
    }

    public IntegerFilter maxPurchaseAmountPerOne() {
        if (maxPurchaseAmountPerOne == null) {
            maxPurchaseAmountPerOne = new IntegerFilter();
        }
        return maxPurchaseAmountPerOne;
    }

    public void setMaxPurchaseAmountPerOne(IntegerFilter maxPurchaseAmountPerOne) {
        this.maxPurchaseAmountPerOne = maxPurchaseAmountPerOne;
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

    public BooleanFilter getUseProductAnnounce() {
        return useProductAnnounce;
    }

    public BooleanFilter useProductAnnounce() {
        if (useProductAnnounce == null) {
            useProductAnnounce = new BooleanFilter();
        }
        return useProductAnnounce;
    }

    public void setUseProductAnnounce(BooleanFilter useProductAnnounce) {
        this.useProductAnnounce = useProductAnnounce;
    }

    public BooleanFilter getUseProductFaq() {
        return useProductFaq;
    }

    public BooleanFilter useProductFaq() {
        if (useProductFaq == null) {
            useProductFaq = new BooleanFilter();
        }
        return useProductFaq;
    }

    public void setUseProductFaq(BooleanFilter useProductFaq) {
        this.useProductFaq = useProductFaq;
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

    public StringFilter getShippingReleaseType() {
        return shippingReleaseType;
    }

    public StringFilter shippingReleaseType() {
        if (shippingReleaseType == null) {
            shippingReleaseType = new StringFilter();
        }
        return shippingReleaseType;
    }

    public void setShippingReleaseType(StringFilter shippingReleaseType) {
        this.shippingReleaseType = shippingReleaseType;
    }

    public StringFilter getShippingStandardStartTime() {
        return shippingStandardStartTime;
    }

    public StringFilter shippingStandardStartTime() {
        if (shippingStandardStartTime == null) {
            shippingStandardStartTime = new StringFilter();
        }
        return shippingStandardStartTime;
    }

    public void setShippingStandardStartTime(StringFilter shippingStandardStartTime) {
        this.shippingStandardStartTime = shippingStandardStartTime;
    }

    public StringFilter getEtcShippingContent() {
        return etcShippingContent;
    }

    public StringFilter etcShippingContent() {
        if (etcShippingContent == null) {
            etcShippingContent = new StringFilter();
        }
        return etcShippingContent;
    }

    public void setEtcShippingContent(StringFilter etcShippingContent) {
        this.etcShippingContent = etcShippingContent;
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

    public StringFilter getBundleShippingType() {
        return bundleShippingType;
    }

    public StringFilter bundleShippingType() {
        if (bundleShippingType == null) {
            bundleShippingType = new StringFilter();
        }
        return bundleShippingType;
    }

    public void setBundleShippingType(StringFilter bundleShippingType) {
        this.bundleShippingType = bundleShippingType;
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

    public StringFilter getExchangeShippingFileType() {
        return exchangeShippingFileType;
    }

    public StringFilter exchangeShippingFileType() {
        if (exchangeShippingFileType == null) {
            exchangeShippingFileType = new StringFilter();
        }
        return exchangeShippingFileType;
    }

    public void setExchangeShippingFileType(StringFilter exchangeShippingFileType) {
        this.exchangeShippingFileType = exchangeShippingFileType;
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

    public BooleanFilter getUseView() {
        return useView;
    }

    public BooleanFilter useView() {
        if (useView == null) {
            useView = new BooleanFilter();
        }
        return useView;
    }

    public void setUseView(BooleanFilter useView) {
        this.useView = useView;
    }

    public BooleanFilter getUseViewReservation() {
        return useViewReservation;
    }

    public BooleanFilter useViewReservation() {
        if (useViewReservation == null) {
            useViewReservation = new BooleanFilter();
        }
        return useViewReservation;
    }

    public void setUseViewReservation(BooleanFilter useViewReservation) {
        this.useViewReservation = useViewReservation;
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

    public BooleanFilter getUseProductNotice() {
        return useProductNotice;
    }

    public BooleanFilter useProductNotice() {
        if (useProductNotice == null) {
            useProductNotice = new BooleanFilter();
        }
        return useProductNotice;
    }

    public void setUseProductNotice(BooleanFilter useProductNotice) {
        this.useProductNotice = useProductNotice;
    }

    public BooleanFilter getUseProductIllegal() {
        return useProductIllegal;
    }

    public BooleanFilter useProductIllegal() {
        if (useProductIllegal == null) {
            useProductIllegal = new BooleanFilter();
        }
        return useProductIllegal;
    }

    public void setUseProductIllegal(BooleanFilter useProductIllegal) {
        this.useProductIllegal = useProductIllegal;
    }

    public BooleanFilter getUseProductRecommend() {
        return useProductRecommend;
    }

    public BooleanFilter useProductRecommend() {
        if (useProductRecommend == null) {
            useProductRecommend = new BooleanFilter();
        }
        return useProductRecommend;
    }

    public void setUseProductRecommend(BooleanFilter useProductRecommend) {
        this.useProductRecommend = useProductRecommend;
    }

    public BooleanFilter getUseProductMapping() {
        return useProductMapping;
    }

    public BooleanFilter useProductMapping() {
        if (useProductMapping == null) {
            useProductMapping = new BooleanFilter();
        }
        return useProductMapping;
    }

    public void setUseProductMapping(BooleanFilter useProductMapping) {
        this.useProductMapping = useProductMapping;
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

    public LongFilter getProductDiscountId() {
        return productDiscountId;
    }

    public LongFilter productDiscountId() {
        if (productDiscountId == null) {
            productDiscountId = new LongFilter();
        }
        return productDiscountId;
    }

    public void setProductDiscountId(LongFilter productDiscountId) {
        this.productDiscountId = productDiscountId;
    }

    public LongFilter getProductMappingId() {
        return productMappingId;
    }

    public LongFilter productMappingId() {
        if (productMappingId == null) {
            productMappingId = new LongFilter();
        }
        return productMappingId;
    }

    public void setProductMappingId(LongFilter productMappingId) {
        this.productMappingId = productMappingId;
    }

    public LongFilter getProductOptionId() {
        return productOptionId;
    }

    public LongFilter productOptionId() {
        if (productOptionId == null) {
            productOptionId = new LongFilter();
        }
        return productOptionId;
    }

    public void setProductOptionId(LongFilter productOptionId) {
        this.productOptionId = productOptionId;
    }

    public LongFilter getProductAddOptionId() {
        return productAddOptionId;
    }

    public LongFilter productAddOptionId() {
        if (productAddOptionId == null) {
            productAddOptionId = new LongFilter();
        }
        return productAddOptionId;
    }

    public void setProductAddOptionId(LongFilter productAddOptionId) {
        this.productAddOptionId = productAddOptionId;
    }

    public LongFilter getProductInputOptionId() {
        return productInputOptionId;
    }

    public LongFilter productInputOptionId() {
        if (productInputOptionId == null) {
            productInputOptionId = new LongFilter();
        }
        return productInputOptionId;
    }

    public void setProductInputOptionId(LongFilter productInputOptionId) {
        this.productInputOptionId = productInputOptionId;
    }

    public LongFilter getProductFaqId() {
        return productFaqId;
    }

    public LongFilter productFaqId() {
        if (productFaqId == null) {
            productFaqId = new LongFilter();
        }
        return productFaqId;
    }

    public void setProductFaqId(LongFilter productFaqId) {
        this.productFaqId = productFaqId;
    }

    public LongFilter getProductAnnounceId() {
        return productAnnounceId;
    }

    public LongFilter productAnnounceId() {
        if (productAnnounceId == null) {
            productAnnounceId = new LongFilter();
        }
        return productAnnounceId;
    }

    public void setProductAnnounceId(LongFilter productAnnounceId) {
        this.productAnnounceId = productAnnounceId;
    }

    public LongFilter getProductAddImageId() {
        return productAddImageId;
    }

    public LongFilter productAddImageId() {
        if (productAddImageId == null) {
            productAddImageId = new LongFilter();
        }
        return productAddImageId;
    }

    public void setProductAddImageId(LongFilter productAddImageId) {
        this.productAddImageId = productAddImageId;
    }

    public LongFilter getProductLabelId() {
        return productLabelId;
    }

    public LongFilter productLabelId() {
        if (productLabelId == null) {
            productLabelId = new LongFilter();
        }
        return productLabelId;
    }

    public void setProductLabelId(LongFilter productLabelId) {
        this.productLabelId = productLabelId;
    }

    public LongFilter getProductTemplateId() {
        return productTemplateId;
    }

    public LongFilter productTemplateId() {
        if (productTemplateId == null) {
            productTemplateId = new LongFilter();
        }
        return productTemplateId;
    }

    public void setProductTemplateId(LongFilter productTemplateId) {
        this.productTemplateId = productTemplateId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public LongFilter productCategoryId() {
        if (productCategoryId == null) {
            productCategoryId = new LongFilter();
        }
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductNoticeManageId() {
        return productNoticeManageId;
    }

    public LongFilter productNoticeManageId() {
        if (productNoticeManageId == null) {
            productNoticeManageId = new LongFilter();
        }
        return productNoticeManageId;
    }

    public void setProductNoticeManageId(LongFilter productNoticeManageId) {
        this.productNoticeManageId = productNoticeManageId;
    }

    public LongFilter getProductClazzAuthorId() {
        return productClazzAuthorId;
    }

    public LongFilter productClazzAuthorId() {
        if (productClazzAuthorId == null) {
            productClazzAuthorId = new LongFilter();
        }
        return productClazzAuthorId;
    }

    public void setProductClazzAuthorId(LongFilter productClazzAuthorId) {
        this.productClazzAuthorId = productClazzAuthorId;
    }

    public LongFilter getProductStoreId() {
        return productStoreId;
    }

    public LongFilter productStoreId() {
        if (productStoreId == null) {
            productStoreId = new LongFilter();
        }
        return productStoreId;
    }

    public void setProductStoreId(LongFilter productStoreId) {
        this.productStoreId = productStoreId;
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
            Objects.equals(type, that.type) &&
            Objects.equals(status, that.status) &&
            Objects.equals(code, that.code) &&
            Objects.equals(useClassOption, that.useClassOption) &&
            Objects.equals(price, that.price) &&
            Objects.equals(useDiscountInstant, that.useDiscountInstant) &&
            Objects.equals(useInstallment, that.useInstallment) &&
            Objects.equals(installmentMonth, that.installmentMonth) &&
            Objects.equals(useSellDate, that.useSellDate) &&
            Objects.equals(sellDateFrom, that.sellDateFrom) &&
            Objects.equals(sellDateTo, that.sellDateTo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(useProductOption, that.useProductOption) &&
            Objects.equals(useProductInputOption, that.useProductInputOption) &&
            Objects.equals(useProductAddOption, that.useProductAddOption) &&
            Objects.equals(minPurchaseAmount, that.minPurchaseAmount) &&
            Objects.equals(maxPurchaseAmountPerCount, that.maxPurchaseAmountPerCount) &&
            Objects.equals(maxPurchaseAmountPerOne, that.maxPurchaseAmountPerOne) &&
            Objects.equals(mainImageFileUrl, that.mainImageFileUrl) &&
            Objects.equals(mainVideoFileUrl, that.mainVideoFileUrl) &&
            Objects.equals(useProductAnnounce, that.useProductAnnounce) &&
            Objects.equals(useProductFaq, that.useProductFaq) &&
            Objects.equals(descriptionFileUrl, that.descriptionFileUrl) &&
            Objects.equals(shippingReleaseType, that.shippingReleaseType) &&
            Objects.equals(shippingStandardStartTime, that.shippingStandardStartTime) &&
            Objects.equals(etcShippingContent, that.etcShippingContent) &&
            Objects.equals(separateShippingPriceType, that.separateShippingPriceType) &&
            Objects.equals(bundleShippingType, that.bundleShippingType) &&
            Objects.equals(defaultShippingPrice, that.defaultShippingPrice) &&
            Objects.equals(freeShippingPrice, that.freeShippingPrice) &&
            Objects.equals(jejuShippingPrice, that.jejuShippingPrice) &&
            Objects.equals(difficultShippingPrice, that.difficultShippingPrice) &&
            Objects.equals(refundShippingPrice, that.refundShippingPrice) &&
            Objects.equals(exchangeShippingPrice, that.exchangeShippingPrice) &&
            Objects.equals(exchangeShippingFileType, that.exchangeShippingFileType) &&
            Objects.equals(exchangeShippingFileUrl, that.exchangeShippingFileUrl) &&
            Objects.equals(useView, that.useView) &&
            Objects.equals(useViewReservation, that.useViewReservation) &&
            Objects.equals(viewReservationDate, that.viewReservationDate) &&
            Objects.equals(useProductNotice, that.useProductNotice) &&
            Objects.equals(useProductIllegal, that.useProductIllegal) &&
            Objects.equals(useProductRecommend, that.useProductRecommend) &&
            Objects.equals(useProductMapping, that.useProductMapping) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(productDiscountId, that.productDiscountId) &&
            Objects.equals(productMappingId, that.productMappingId) &&
            Objects.equals(productOptionId, that.productOptionId) &&
            Objects.equals(productAddOptionId, that.productAddOptionId) &&
            Objects.equals(productInputOptionId, that.productInputOptionId) &&
            Objects.equals(productFaqId, that.productFaqId) &&
            Objects.equals(productAnnounceId, that.productAnnounceId) &&
            Objects.equals(productAddImageId, that.productAddImageId) &&
            Objects.equals(productLabelId, that.productLabelId) &&
            Objects.equals(productTemplateId, that.productTemplateId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productNoticeManageId, that.productNoticeManageId) &&
            Objects.equals(productClazzAuthorId, that.productClazzAuthorId) &&
            Objects.equals(productStoreId, that.productStoreId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            type,
            status,
            code,
            useClassOption,
            price,
            useDiscountInstant,
            useInstallment,
            installmentMonth,
            useSellDate,
            sellDateFrom,
            sellDateTo,
            quantity,
            useProductOption,
            useProductInputOption,
            useProductAddOption,
            minPurchaseAmount,
            maxPurchaseAmountPerCount,
            maxPurchaseAmountPerOne,
            mainImageFileUrl,
            mainVideoFileUrl,
            useProductAnnounce,
            useProductFaq,
            descriptionFileUrl,
            shippingReleaseType,
            shippingStandardStartTime,
            etcShippingContent,
            separateShippingPriceType,
            bundleShippingType,
            defaultShippingPrice,
            freeShippingPrice,
            jejuShippingPrice,
            difficultShippingPrice,
            refundShippingPrice,
            exchangeShippingPrice,
            exchangeShippingFileType,
            exchangeShippingFileUrl,
            useView,
            useViewReservation,
            viewReservationDate,
            useProductNotice,
            useProductIllegal,
            useProductRecommend,
            useProductMapping,
            activated,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            productDiscountId,
            productMappingId,
            productOptionId,
            productAddOptionId,
            productInputOptionId,
            productFaqId,
            productAnnounceId,
            productAddImageId,
            productLabelId,
            productTemplateId,
            productCategoryId,
            productNoticeManageId,
            productClazzAuthorId,
            productStoreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (useClassOption != null ? "useClassOption=" + useClassOption + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (useDiscountInstant != null ? "useDiscountInstant=" + useDiscountInstant + ", " : "") +
            (useInstallment != null ? "useInstallment=" + useInstallment + ", " : "") +
            (installmentMonth != null ? "installmentMonth=" + installmentMonth + ", " : "") +
            (useSellDate != null ? "useSellDate=" + useSellDate + ", " : "") +
            (sellDateFrom != null ? "sellDateFrom=" + sellDateFrom + ", " : "") +
            (sellDateTo != null ? "sellDateTo=" + sellDateTo + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (useProductOption != null ? "useProductOption=" + useProductOption + ", " : "") +
            (useProductInputOption != null ? "useProductInputOption=" + useProductInputOption + ", " : "") +
            (useProductAddOption != null ? "useProductAddOption=" + useProductAddOption + ", " : "") +
            (minPurchaseAmount != null ? "minPurchaseAmount=" + minPurchaseAmount + ", " : "") +
            (maxPurchaseAmountPerCount != null ? "maxPurchaseAmountPerCount=" + maxPurchaseAmountPerCount + ", " : "") +
            (maxPurchaseAmountPerOne != null ? "maxPurchaseAmountPerOne=" + maxPurchaseAmountPerOne + ", " : "") +
            (mainImageFileUrl != null ? "mainImageFileUrl=" + mainImageFileUrl + ", " : "") +
            (mainVideoFileUrl != null ? "mainVideoFileUrl=" + mainVideoFileUrl + ", " : "") +
            (useProductAnnounce != null ? "useProductAnnounce=" + useProductAnnounce + ", " : "") +
            (useProductFaq != null ? "useProductFaq=" + useProductFaq + ", " : "") +
            (descriptionFileUrl != null ? "descriptionFileUrl=" + descriptionFileUrl + ", " : "") +
            (shippingReleaseType != null ? "shippingReleaseType=" + shippingReleaseType + ", " : "") +
            (shippingStandardStartTime != null ? "shippingStandardStartTime=" + shippingStandardStartTime + ", " : "") +
            (etcShippingContent != null ? "etcShippingContent=" + etcShippingContent + ", " : "") +
            (separateShippingPriceType != null ? "separateShippingPriceType=" + separateShippingPriceType + ", " : "") +
            (bundleShippingType != null ? "bundleShippingType=" + bundleShippingType + ", " : "") +
            (defaultShippingPrice != null ? "defaultShippingPrice=" + defaultShippingPrice + ", " : "") +
            (freeShippingPrice != null ? "freeShippingPrice=" + freeShippingPrice + ", " : "") +
            (jejuShippingPrice != null ? "jejuShippingPrice=" + jejuShippingPrice + ", " : "") +
            (difficultShippingPrice != null ? "difficultShippingPrice=" + difficultShippingPrice + ", " : "") +
            (refundShippingPrice != null ? "refundShippingPrice=" + refundShippingPrice + ", " : "") +
            (exchangeShippingPrice != null ? "exchangeShippingPrice=" + exchangeShippingPrice + ", " : "") +
            (exchangeShippingFileType != null ? "exchangeShippingFileType=" + exchangeShippingFileType + ", " : "") +
            (exchangeShippingFileUrl != null ? "exchangeShippingFileUrl=" + exchangeShippingFileUrl + ", " : "") +
            (useView != null ? "useView=" + useView + ", " : "") +
            (useViewReservation != null ? "useViewReservation=" + useViewReservation + ", " : "") +
            (viewReservationDate != null ? "viewReservationDate=" + viewReservationDate + ", " : "") +
            (useProductNotice != null ? "useProductNotice=" + useProductNotice + ", " : "") +
            (useProductIllegal != null ? "useProductIllegal=" + useProductIllegal + ", " : "") +
            (useProductRecommend != null ? "useProductRecommend=" + useProductRecommend + ", " : "") +
            (useProductMapping != null ? "useProductMapping=" + useProductMapping + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
            (productDiscountId != null ? "productDiscountId=" + productDiscountId + ", " : "") +
            (productMappingId != null ? "productMappingId=" + productMappingId + ", " : "") +
            (productOptionId != null ? "productOptionId=" + productOptionId + ", " : "") +
            (productAddOptionId != null ? "productAddOptionId=" + productAddOptionId + ", " : "") +
            (productInputOptionId != null ? "productInputOptionId=" + productInputOptionId + ", " : "") +
            (productFaqId != null ? "productFaqId=" + productFaqId + ", " : "") +
            (productAnnounceId != null ? "productAnnounceId=" + productAnnounceId + ", " : "") +
            (productAddImageId != null ? "productAddImageId=" + productAddImageId + ", " : "") +
            (productLabelId != null ? "productLabelId=" + productLabelId + ", " : "") +
            (productTemplateId != null ? "productTemplateId=" + productTemplateId + ", " : "") +
            (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
            (productNoticeManageId != null ? "productNoticeManageId=" + productNoticeManageId + ", " : "") +
            (productClazzAuthorId != null ? "productClazzAuthorId=" + productClazzAuthorId + ", " : "") +
            (productStoreId != null ? "productStoreId=" + productStoreId + ", " : "") +
            "}";
    }
}
