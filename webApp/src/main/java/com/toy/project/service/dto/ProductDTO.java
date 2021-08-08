package com.toy.project.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toy.project.domain.Product} entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String name;

    @Size(max = 50)
    private String type;

    @Size(max = 50)
    private String status;

    private String code;

    private Boolean useClassOption;

    private Integer price;

    private Boolean useDiscountInstant;

    private Boolean useInstallment;

    private Integer installmentMonth;

    private Boolean useSellDate;

    private Instant sellDateFrom;

    private Instant sellDateTo;

    private Integer quantity;

    private Boolean useProductOption;

    private Boolean useProductInputOption;

    private Boolean useProductAddOption;

    private Integer minPurchaseAmount;

    private Integer maxPurchaseAmountPerCount;

    private Integer maxPurchaseAmountPerOne;

    private String mainImageFileUrl;

    private String mainVideoFileUrl;

    private Boolean useProductAnnounce;

    private Boolean useProductFaq;

    private String descriptionFileUrl;

    private String shippingReleaseType;

    private String shippingStandardStartTime;

    @Size(max = 300)
    private String etcShippingContent;

    private String separateShippingPriceType;

    private String bundleShippingType;

    private Integer defaultShippingPrice;

    private Integer freeShippingPrice;

    private Integer jejuShippingPrice;

    private Integer difficultShippingPrice;

    private Integer refundShippingPrice;

    private Integer exchangeShippingPrice;

    private String exchangeShippingFileType;

    private String exchangeShippingFileUrl;

    private Boolean useView;

    private Boolean useViewReservation;

    private Instant viewReservationDate;

    private Boolean useProductNotice;

    private Boolean useProductIllegal;

    private Boolean useProductRecommend;

    private Boolean useProductMapping;

    private Boolean activated;

    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Long productNoticeManageId;

    private ProductClazzAuthorDTO productClazzAuthor;

    private ProductStoreDTO productStore;

    @ApiModelProperty(value = "상품 카테고리 리스트")
    private List<ProductCategoryDTO> productCategories;

    @ApiModelProperty(value = "상품 추가 이미지 리스트")
    private List<ProductAddImageDTO> productAddImages;

    @ApiModelProperty(value = "상품 즉시 할인")
    private List<ProductDiscountDTO> productDiscounts;

    @ApiModelProperty(value = "상품 선택 옵션")
    private List<ProductOptionDTO> productOptions;

    @ApiModelProperty(value = "상품 직접 입력 옵션")
    private List<ProductInputOptionDTO> productInputOptions;

    @ApiModelProperty(value = "상품 추가 옵션")
    private List<ProductAddOptionDTO> productAddOptions;

    @ApiModelProperty(value = "상품 고시")
    private List<ProductAnnounceDTO> productAnnounces;

    @ApiModelProperty(value = "상품 자주 묻는 질문")
    private List<ProductFaqDTO> productFaqs;

    @ApiModelProperty(value = "상품 템플릿")
    private List<ProductTemplateDTO> productTemplates;

    @ApiModelProperty(value = "추천, 연관 상품 관리")
    private List<ProductMappingDTO> productMappings;

    @ApiModelProperty(value = "상품 라벨")
    private List<ProductLabelDTO> productLabels;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getInstallmentMonth() {
        return installmentMonth;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinPurchaseAmount() {
        return minPurchaseAmount;
    }

    public void setMinPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Integer getMaxPurchaseAmountPerCount() {
        return maxPurchaseAmountPerCount;
    }

    public void setMaxPurchaseAmountPerCount(Integer maxPurchaseAmountPerCount) {
        this.maxPurchaseAmountPerCount = maxPurchaseAmountPerCount;
    }

    public Integer getMaxPurchaseAmountPerOne() {
        return maxPurchaseAmountPerOne;
    }

    public void setMaxPurchaseAmountPerOne(Integer maxPurchaseAmountPerOne) {
        this.maxPurchaseAmountPerOne = maxPurchaseAmountPerOne;
    }

    public String getMainImageFileUrl() {
        return mainImageFileUrl;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
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

    public String getShippingReleaseType() {
        return shippingReleaseType;
    }

    public void setShippingReleaseType(String shippingReleaseType) {
        this.shippingReleaseType = shippingReleaseType;
    }

    public String getShippingStandardStartTime() {
        return shippingStandardStartTime;
    }

    public void setShippingStandardStartTime(String shippingStandardStartTime) {
        this.shippingStandardStartTime = shippingStandardStartTime;
    }

    public String getEtcShippingContent() {
        return etcShippingContent;
    }

    public void setEtcShippingContent(String etcShippingContent) {
        this.etcShippingContent = etcShippingContent;
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

    public String getExchangeShippingFileType() {
        return exchangeShippingFileType;
    }

    public void setExchangeShippingFileType(String exchangeShippingFileType) {
        this.exchangeShippingFileType = exchangeShippingFileType;
    }

    public String getExchangeShippingFileUrl() {
        return exchangeShippingFileUrl;
    }

    public void setExchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
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

    public Long getProductNoticeManageId() {
        return productNoticeManageId;
    }

    public void setProductNoticeManageId(Long productNoticeManageId) {
        this.productNoticeManageId = productNoticeManageId;
    }

    public ProductClazzAuthorDTO getProductClazzAuthor() {
        return productClazzAuthor;
    }

    public void setProductClazzAuthor(ProductClazzAuthorDTO productClazzAuthor) {
        this.productClazzAuthor = productClazzAuthor;
    }

    public ProductStoreDTO getProductStore() {
        return productStore;
    }

    public void setProductStore(ProductStoreDTO productStore) {
        this.productStore = productStore;
    }

    public List<ProductDiscountDTO> getProductDiscounts() {
        return productDiscounts;
    }

    public void setProductDiscounts(List<ProductDiscountDTO> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    public List<ProductOptionDTO> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptionDTO> productOptions) {
        this.productOptions = productOptions;
    }

    public List<ProductInputOptionDTO> getProductInputOptions() {
        return productInputOptions;
    }

    public void setProductInputOptions(List<ProductInputOptionDTO> productInputOptions) {
        this.productInputOptions = productInputOptions;
    }

    public List<ProductAddOptionDTO> getProductAddOptions() {
        return productAddOptions;
    }

    public void setProductAddOptions(List<ProductAddOptionDTO> productAddOptions) {
        this.productAddOptions = productAddOptions;
    }

    public List<ProductAnnounceDTO> getProductAnnounces() {
        return productAnnounces;
    }

    public void setProductAnnounces(List<ProductAnnounceDTO> productAnnounces) {
        this.productAnnounces = productAnnounces;
    }

    public List<ProductFaqDTO> getProductFaqs() {
        return productFaqs;
    }

    public void setProductFaqs(List<ProductFaqDTO> productFaqs) {
        this.productFaqs = productFaqs;
    }

    public List<ProductTemplateDTO> getProductTemplates() {
        return productTemplates;
    }

    public void setProductTemplates(List<ProductTemplateDTO> productTemplates) {
        this.productTemplates = productTemplates;
    }

    public List<ProductMappingDTO> getProductMappings() {
        return productMappings;
    }

    public void setProductMappings(List<ProductMappingDTO> productMappings) {
        this.productMappings = productMappings;
    }

    public List<ProductLabelDTO> getProductLabels() {
        return productLabels;
    }

    public void setProductLabels(List<ProductLabelDTO> productLabels) {
        this.productLabels = productLabels;
    }

    public List<ProductAddImageDTO> getProductAddImages() {
        return productAddImages;
    }

    public void setProductAddImages(List<ProductAddImageDTO> productAddImages) {
        this.productAddImages = productAddImages;
    }

    public List<ProductCategoryDTO> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategoryDTO> productCategories) {
        this.productCategories = productCategories;
    }

    public Boolean getUseClassOption() {
        return useClassOption;
    }

    public void setUseClassOption(Boolean useClassOption) {
        this.useClassOption = useClassOption;
    }

    public Boolean getUseDiscountInstant() {
        return useDiscountInstant;
    }

    public void setUseDiscountInstant(Boolean useDiscountInstant) {
        this.useDiscountInstant = useDiscountInstant;
    }

    public Boolean getUseInstallment() {
        return useInstallment;
    }

    public void setUseInstallment(Boolean useInstallment) {
        this.useInstallment = useInstallment;
    }

    public Boolean getUseSellDate() {
        return useSellDate;
    }

    public void setUseSellDate(Boolean useSellDate) {
        this.useSellDate = useSellDate;
    }

    public Boolean getUseProductOption() {
        return useProductOption;
    }

    public void setUseProductOption(Boolean useProductOption) {
        this.useProductOption = useProductOption;
    }

    public Boolean getUseProductInputOption() {
        return useProductInputOption;
    }

    public void setUseProductInputOption(Boolean useProductInputOption) {
        this.useProductInputOption = useProductInputOption;
    }

    public Boolean getUseProductAddOption() {
        return useProductAddOption;
    }

    public void setUseProductAddOption(Boolean useProductAddOption) {
        this.useProductAddOption = useProductAddOption;
    }

    public Boolean getUseProductAnnounce() {
        return useProductAnnounce;
    }

    public void setUseProductAnnounce(Boolean useProductAnnounce) {
        this.useProductAnnounce = useProductAnnounce;
    }

    public Boolean getUseProductFaq() {
        return useProductFaq;
    }

    public void setUseProductFaq(Boolean useProductFaq) {
        this.useProductFaq = useProductFaq;
    }

    public Boolean getUseView() {
        return useView;
    }

    public void setUseView(Boolean useView) {
        this.useView = useView;
    }

    public Boolean getUseViewReservation() {
        return useViewReservation;
    }

    public void setUseViewReservation(Boolean useViewReservation) {
        this.useViewReservation = useViewReservation;
    }

    public Boolean getUseProductNotice() {
        return useProductNotice;
    }

    public void setUseProductNotice(Boolean useProductNotice) {
        this.useProductNotice = useProductNotice;
    }

    public Boolean getUseProductIllegal() {
        return useProductIllegal;
    }

    public void setUseProductIllegal(Boolean useProductIllegal) {
        this.useProductIllegal = useProductIllegal;
    }

    public Boolean getUseProductRecommend() {
        return useProductRecommend;
    }

    public void setUseProductRecommend(Boolean useProductRecommend) {
        this.useProductRecommend = useProductRecommend;
    }

    public Boolean getUseProductMapping() {
        return useProductMapping;
    }

    public void setUseProductMapping(Boolean useProductMapping) {
        this.useProductMapping = useProductMapping;
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

    @Override
    public String toString() {
        return (
            "ProductDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", status='" +
            status +
            '\'' +
            ", code='" +
            code +
            '\'' +
            ", useClassOption=" +
            useClassOption +
            ", price=" +
            price +
            ", useDiscountInstant=" +
            useDiscountInstant +
            ", useInstallment=" +
            useInstallment +
            ", installmentMonth=" +
            installmentMonth +
            ", useSellDate=" +
            useSellDate +
            ", sellDateFrom=" +
            sellDateFrom +
            ", sellDateTo=" +
            sellDateTo +
            ", quantity=" +
            quantity +
            ", useProductOption=" +
            useProductOption +
            ", useProductInputOption=" +
            useProductInputOption +
            ", useProductAddOption=" +
            useProductAddOption +
            ", minPurchaseAmount=" +
            minPurchaseAmount +
            ", maxPurchaseAmountPerCount=" +
            maxPurchaseAmountPerCount +
            ", maxPurchaseAmountPerOne=" +
            maxPurchaseAmountPerOne +
            ", mainImageFileUrl='" +
            mainImageFileUrl +
            '\'' +
            ", mainVideoFileUrl='" +
            mainVideoFileUrl +
            '\'' +
            ", useProductAnnounce=" +
            useProductAnnounce +
            ", useProductFaq=" +
            useProductFaq +
            ", descriptionFileUrl='" +
            descriptionFileUrl +
            '\'' +
            ", shippingReleaseType='" +
            shippingReleaseType +
            '\'' +
            ", shippingStandardStartTime='" +
            shippingStandardStartTime +
            '\'' +
            ", etcShippingContent='" +
            etcShippingContent +
            '\'' +
            ", separateShippingPriceType='" +
            separateShippingPriceType +
            '\'' +
            ", bundleShippingType='" +
            bundleShippingType +
            '\'' +
            ", defaultShippingPrice=" +
            defaultShippingPrice +
            ", freeShippingPrice=" +
            freeShippingPrice +
            ", jejuShippingPrice=" +
            jejuShippingPrice +
            ", difficultShippingPrice=" +
            difficultShippingPrice +
            ", refundShippingPrice=" +
            refundShippingPrice +
            ", exchangeShippingPrice=" +
            exchangeShippingPrice +
            ", exchangeShippingFileType='" +
            exchangeShippingFileType +
            '\'' +
            ", exchangeShippingFileUrl='" +
            exchangeShippingFileUrl +
            '\'' +
            ", useView=" +
            useView +
            ", useViewReservation=" +
            useViewReservation +
            ", viewReservationDate=" +
            viewReservationDate +
            ", useProductNotice=" +
            useProductNotice +
            ", useProductIllegal=" +
            useProductIllegal +
            ", useProductRecommend=" +
            useProductRecommend +
            ", useProductMapping=" +
            useProductMapping +
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
            ", productNoticeManageId=" +
            productNoticeManageId +
            ", productClazzAuthor=" +
            productClazzAuthor +
            ", productStore=" +
            productStore +
            ", productCategories=" +
            productCategories +
            ", productAddImages=" +
            productAddImages +
            ", productDiscounts=" +
            productDiscounts +
            ", productOptions=" +
            productOptions +
            ", productInputOptions=" +
            productInputOptions +
            ", productAddOptions=" +
            productAddOptions +
            ", productAnnounces=" +
            productAnnounces +
            ", productFaqs=" +
            productFaqs +
            ", productTemplates=" +
            productTemplates +
            ", productMappings=" +
            productMappings +
            ", productLabels=" +
            productLabels +
            '}'
        );
    }
}
