package com.toy.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Product.
 * Fetch 전략은
 */
@Entity
@Table(name = "product")
public class Product extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "calculation")
    private String calculation;

    @Column(name = "calculation_date_from")
    private Instant calculationDateFrom;

    @Column(name = "calculation_date_to")
    private Instant calculationDateTo;

    @Column(name = "price")
    private Integer price;

    @Column(name = "all_price_unit")
    private String allPriceUnit;

    @Column(name = "discount")
    private String discount;

    @Column(name = "discount_price")
    private String discountPrice;

    @Column(name = "discount_unit")
    private String discountUnit;

    @Column(name = "discount_date_from")
    private Instant discountDateFrom;

    @Column(name = "discount_date_to")
    private Instant discountDateTo;

    @Column(name = "is_installment")
    private Boolean isInstallment;

    @Column(name = "installment_month")
    private Integer installmentMonth;

    @Column(name = "is_sell")
    private Boolean isSell;

    @Column(name = "sell_date_from")
    private Instant sellDateFrom;

    @Column(name = "sell_date_to")
    private Instant sellDateTo;

    @Column(name = "min_purchase_amount")
    private Integer minPurchaseAmount;

    @Column(name = "man_purchase_amount")
    private Integer manPurchaseAmount;

    @Column(name = "main_image_file_url")
    private String mainImageFileUrl;

    @Lob
    @Column(name = "add_image_file_url")
    private String addImageFileUrl;

    @Column(name = "main_video_file_url")
    private String mainVideoFileUrl;

    @Column(name = "description_file_url")
    private String descriptionFileUrl;

    @Column(name = "shipping_type")
    private String shippingType;

    @Column(name = "separate_shipping_price_type")
    private String separateShippingPriceType;

    @Column(name = "default_shipping_price")
    private Integer defaultShippingPrice;

    @Column(name = "free_shipping_price")
    private Integer freeShippingPrice;

    @Column(name = "jeju_shipping_price")
    private Integer jejuShippingPrice;

    @Column(name = "difficult_shipping_price")
    private Integer difficultShippingPrice;

    @Column(name = "refund_shipping_price")
    private Integer refundShippingPrice;

    @Column(name = "exchange_shipping_price")
    private Integer exchangeShippingPrice;

    @Column(name = "exchange_shipping_file_url")
    private String exchangeShippingFileUrl;

    @Column(name = "is_view")
    private Boolean isView;

    @Column(name = "view_reservation_date")
    private Instant viewReservationDate;

    @Column(name = "activated")
    private Boolean activated;

    @OneToMany
    @JoinTable(
        name = "product_category_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_category_id")
    )
    @JsonIgnoreProperties(value = { "productCategoryRel" }, allowSetters = true)
    private Set<ProductCategory> productCategories = new HashSet<>();

    @OneToMany
    @JoinTable(
        name = "product_mapping_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_mapping_id")
    )
    @JsonIgnoreProperties(value = { "productMappingRel" }, allowSetters = true)
    private Set<ProductMapping> productMappings = new HashSet<>();

    @OneToMany
    @JoinTable(
        name = "product_view_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_view_id")
    )
    @JsonIgnoreProperties(value = { "productViewRel" }, allowSetters = true)
    private Set<ProductView> productViews = new HashSet<>();

    @OneToMany
    @JoinTable(
        name = "product_notice_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_notice_id")
    )
    @JsonIgnoreProperties(value = { "productNoticeRel" }, allowSetters = true)
    private Set<ProductNotice> productNotices = new HashSet<>();

    @OneToMany
    @JoinTable(
        name = "product_shipping_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_shipping_id")
    )
    @JsonIgnoreProperties(value = { "productShippingRel" }, allowSetters = true)
    private Set<ProductShipping> productShippings = new HashSet<>();

    @OneToMany
    @JoinTable(
        name = "product_template_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_template_id")
    )
    @JsonIgnoreProperties(value = { "productTemplateRel" }, allowSetters = true)
    private Set<ProductTemplate> productTemplates = new HashSet<>();

    @OneToMany
    @JoinTable(
        name = "product_option_rel",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "product_option_id")
    )
    @JsonIgnoreProperties(value = { "productOptionRel" }, allowSetters = true)
    private Set<ProductOption> productOptions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "productLabel" }, allowSetters = true)
    private Set<ProductLabelRel> productLabelRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "clazz" }, allowSetters = true)
    private Set<ProductClazzRel> productClazzRels = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "store" }, allowSetters = true)
    private Set<ProductStoreRel> productStoreRels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Product code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCalculation() {
        return this.calculation;
    }

    public Product calculation(String calculation) {
        this.calculation = calculation;
        return this;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Instant getCalculationDateFrom() {
        return this.calculationDateFrom;
    }

    public Product calculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
        return this;
    }

    public void setCalculationDateFrom(Instant calculationDateFrom) {
        this.calculationDateFrom = calculationDateFrom;
    }

    public Instant getCalculationDateTo() {
        return this.calculationDateTo;
    }

    public Product calculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
        return this;
    }

    public void setCalculationDateTo(Instant calculationDateTo) {
        this.calculationDateTo = calculationDateTo;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Product price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getAllPriceUnit() {
        return this.allPriceUnit;
    }

    public Product allPriceUnit(String allPriceUnit) {
        this.allPriceUnit = allPriceUnit;
        return this;
    }

    public void setAllPriceUnit(String allPriceUnit) {
        this.allPriceUnit = allPriceUnit;
    }

    public String getDiscount() {
        return this.discount;
    }

    public Product discount(String discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPrice() {
        return this.discountPrice;
    }

    public Product discountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
        return this;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountUnit() {
        return this.discountUnit;
    }

    public Product discountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
        return this;
    }

    public void setDiscountUnit(String discountUnit) {
        this.discountUnit = discountUnit;
    }

    public Instant getDiscountDateFrom() {
        return this.discountDateFrom;
    }

    public Product discountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
        return this;
    }

    public void setDiscountDateFrom(Instant discountDateFrom) {
        this.discountDateFrom = discountDateFrom;
    }

    public Instant getDiscountDateTo() {
        return this.discountDateTo;
    }

    public Product discountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
        return this;
    }

    public void setDiscountDateTo(Instant discountDateTo) {
        this.discountDateTo = discountDateTo;
    }

    public Boolean getIsInstallment() {
        return this.isInstallment;
    }

    public Product isInstallment(Boolean isInstallment) {
        this.isInstallment = isInstallment;
        return this;
    }

    public void setIsInstallment(Boolean isInstallment) {
        this.isInstallment = isInstallment;
    }

    public Integer getInstallmentMonth() {
        return this.installmentMonth;
    }

    public Product installmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
        return this;
    }

    public void setInstallmentMonth(Integer installmentMonth) {
        this.installmentMonth = installmentMonth;
    }

    public Boolean getIsSell() {
        return this.isSell;
    }

    public Product isSell(Boolean isSell) {
        this.isSell = isSell;
        return this;
    }

    public void setIsSell(Boolean isSell) {
        this.isSell = isSell;
    }

    public Instant getSellDateFrom() {
        return this.sellDateFrom;
    }

    public Product sellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
        return this;
    }

    public void setSellDateFrom(Instant sellDateFrom) {
        this.sellDateFrom = sellDateFrom;
    }

    public Instant getSellDateTo() {
        return this.sellDateTo;
    }

    public Product sellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
        return this;
    }

    public void setSellDateTo(Instant sellDateTo) {
        this.sellDateTo = sellDateTo;
    }

    public Integer getMinPurchaseAmount() {
        return this.minPurchaseAmount;
    }

    public Product minPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
        return this;
    }

    public void setMinPurchaseAmount(Integer minPurchaseAmount) {
        this.minPurchaseAmount = minPurchaseAmount;
    }

    public Integer getManPurchaseAmount() {
        return this.manPurchaseAmount;
    }

    public Product manPurchaseAmount(Integer manPurchaseAmount) {
        this.manPurchaseAmount = manPurchaseAmount;
        return this;
    }

    public void setManPurchaseAmount(Integer manPurchaseAmount) {
        this.manPurchaseAmount = manPurchaseAmount;
    }

    public String getMainImageFileUrl() {
        return this.mainImageFileUrl;
    }

    public Product mainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
        return this;
    }

    public void setMainImageFileUrl(String mainImageFileUrl) {
        this.mainImageFileUrl = mainImageFileUrl;
    }

    public String getAddImageFileUrl() {
        return this.addImageFileUrl;
    }

    public Product addImageFileUrl(String addImageFileUrl) {
        this.addImageFileUrl = addImageFileUrl;
        return this;
    }

    public void setAddImageFileUrl(String addImageFileUrl) {
        this.addImageFileUrl = addImageFileUrl;
    }

    public String getMainVideoFileUrl() {
        return this.mainVideoFileUrl;
    }

    public Product mainVideoFileUrl(String mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
        return this;
    }

    public void setMainVideoFileUrl(String mainVideoFileUrl) {
        this.mainVideoFileUrl = mainVideoFileUrl;
    }

    public String getDescriptionFileUrl() {
        return this.descriptionFileUrl;
    }

    public Product descriptionFileUrl(String descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
        return this;
    }

    public void setDescriptionFileUrl(String descriptionFileUrl) {
        this.descriptionFileUrl = descriptionFileUrl;
    }

    public String getShippingType() {
        return this.shippingType;
    }

    public Product shippingType(String shippingType) {
        this.shippingType = shippingType;
        return this;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getSeparateShippingPriceType() {
        return this.separateShippingPriceType;
    }

    public Product separateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
        return this;
    }

    public void setSeparateShippingPriceType(String separateShippingPriceType) {
        this.separateShippingPriceType = separateShippingPriceType;
    }

    public Integer getDefaultShippingPrice() {
        return this.defaultShippingPrice;
    }

    public Product defaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
        return this;
    }

    public void setDefaultShippingPrice(Integer defaultShippingPrice) {
        this.defaultShippingPrice = defaultShippingPrice;
    }

    public Integer getFreeShippingPrice() {
        return this.freeShippingPrice;
    }

    public Product freeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
        return this;
    }

    public void setFreeShippingPrice(Integer freeShippingPrice) {
        this.freeShippingPrice = freeShippingPrice;
    }

    public Integer getJejuShippingPrice() {
        return this.jejuShippingPrice;
    }

    public Product jejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
        return this;
    }

    public void setJejuShippingPrice(Integer jejuShippingPrice) {
        this.jejuShippingPrice = jejuShippingPrice;
    }

    public Integer getDifficultShippingPrice() {
        return this.difficultShippingPrice;
    }

    public Product difficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
        return this;
    }

    public void setDifficultShippingPrice(Integer difficultShippingPrice) {
        this.difficultShippingPrice = difficultShippingPrice;
    }

    public Integer getRefundShippingPrice() {
        return this.refundShippingPrice;
    }

    public Product refundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
        return this;
    }

    public void setRefundShippingPrice(Integer refundShippingPrice) {
        this.refundShippingPrice = refundShippingPrice;
    }

    public Integer getExchangeShippingPrice() {
        return this.exchangeShippingPrice;
    }

    public Product exchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
        return this;
    }

    public void setExchangeShippingPrice(Integer exchangeShippingPrice) {
        this.exchangeShippingPrice = exchangeShippingPrice;
    }

    public String getExchangeShippingFileUrl() {
        return this.exchangeShippingFileUrl;
    }

    public Product exchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
        return this;
    }

    public void setExchangeShippingFileUrl(String exchangeShippingFileUrl) {
        this.exchangeShippingFileUrl = exchangeShippingFileUrl;
    }

    public Boolean getIsView() {
        return this.isView;
    }

    public Product isView(Boolean isView) {
        this.isView = isView;
        return this;
    }

    public void setIsView(Boolean isView) {
        this.isView = isView;
    }

    public Instant getViewReservationDate() {
        return this.viewReservationDate;
    }

    public Product viewReservationDate(Instant viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
        return this;
    }

    public void setViewReservationDate(Instant viewReservationDate) {
        this.viewReservationDate = viewReservationDate;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public Product activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Product createdBy(String createdBy) {
        setCreatedBy(createdBy);
        return this;
    }

    public Product createdDate(Instant createdDate) {
        setCreatedDate(createdDate);
        return this;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public Product lastModifiedDate(Instant lastModifiedDate) {
        setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public Set<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(Set<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public Set<ProductMapping> getProductMappings() {
        return productMappings;
    }

    public void setProductMappings(Set<ProductMapping> productMappings) {
        this.productMappings = productMappings;
    }

    public Set<ProductView> getProductViews() {
        return productViews;
    }

    public void setProductViews(Set<ProductView> productViews) {
        this.productViews = productViews;
    }

    public Set<ProductNotice> getProductNotices() {
        return productNotices;
    }

    public void setProductNotices(Set<ProductNotice> productNotices) {
        this.productNotices = productNotices;
    }

    public Set<ProductShipping> getProductShippings() {
        return productShippings;
    }

    public void setProductShippings(Set<ProductShipping> productShippings) {
        this.productShippings = productShippings;
    }

    public Set<ProductTemplate> getProductTemplates() {
        return productTemplates;
    }

    public void setProductTemplates(Set<ProductTemplate> productTemplates) {
        this.productTemplates = productTemplates;
    }

    public Set<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(Set<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }

    public Set<ProductLabelRel> getProductLabelRels() {
        return this.productLabelRels;
    }

    public Product productLabelRels(Set<ProductLabelRel> productLabelRels) {
        this.setProductLabelRels(productLabelRels);
        return this;
    }

    public void setProductLabelRels(Set<ProductLabelRel> productLabelRels) {
        if (this.productLabelRels != null) {
            this.productLabelRels.forEach(i -> i.setProduct(null));
        }
        if (productLabelRels != null) {
            productLabelRels.forEach(i -> i.setProduct(this));
        }
        this.productLabelRels = productLabelRels;
    }

    public Set<ProductClazzRel> getProductClazzRels() {
        return this.productClazzRels;
    }

    public Product productClazzRels(Set<ProductClazzRel> productClazzRels) {
        this.setProductClazzRels(productClazzRels);
        return this;
    }

    public void setProductClazzRels(Set<ProductClazzRel> productClazzRels) {
        if (this.productClazzRels != null) {
            this.productClazzRels.forEach(i -> i.setProduct(null));
        }
        if (productClazzRels != null) {
            productClazzRels.forEach(i -> i.setProduct(this));
        }
        this.productClazzRels = productClazzRels;
    }

    public Set<ProductStoreRel> getProductStoreRels() {
        return this.productStoreRels;
    }

    public Product productStoreRels(Set<ProductStoreRel> productStoreRels) {
        this.setProductStoreRels(productStoreRels);
        return this;
    }

    public void setProductStoreRels(Set<ProductStoreRel> productStoreRels) {
        if (this.productStoreRels != null) {
            this.productStoreRels.forEach(i -> i.setProduct(null));
        }
        if (productStoreRels != null) {
            productStoreRels.forEach(i -> i.setProduct(this));
        }
        this.productStoreRels = productStoreRels;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
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
